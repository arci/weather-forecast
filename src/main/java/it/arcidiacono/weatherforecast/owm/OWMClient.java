package it.arcidiacono.weatherforecast.owm;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.arcidiacono.weatherforecast.owm.bean.Measure;
import it.arcidiacono.weatherforecast.owm.exception.CityNotFoundException;
import it.arcidiacono.weatherforecast.owm.exception.OWMException;
import it.arcidiacono.weatherforecast.owm.exception.ResponseFormatException;

public class OWMClient {

	private static Logger logger = LoggerFactory.getLogger(OWMClient.class);

	private static final String ENDPOINT = "https://api.openweathermap.org/data/2.5";

	private static final String FORECAST = "forecast";

	private final String apiKey;

	public OWMClient(String apiKey) {
		if (StringUtils.isBlank(apiKey)) {
			throw new NullPointerException("api key cannot be null");
		}
		this.apiKey = apiKey;
	}

	/**
	 * Queries OWM for forecast data of the given city.
	 *
	 * @param  name         the city name
	 * @param  country      the country code in two characters format
	 * @return              a map where keys are {@linkplain LocalDate} of the forecasted day and values are lists of
	 *                      {@linkplain Measure} parsed from OWM response
	 * @throws OWMException if any error occur
	 */
	public Map<LocalDate, List<Measure>> getForecast(String name, String country) throws OWMException {
		Response response = getForecast(name + "," + country);
		if (response.getStatus() < Status.BAD_REQUEST.getStatusCode()) {
			return parseResponse(response);
		} else {
			String error = getErrorMessage(response);
			if (response.getStatus() == Status.NOT_FOUND.getStatusCode()) {
				throw new CityNotFoundException(error);
			}
			throw new OWMException(error, response.getStatus());
		}
	}

	private Response getForecast(String query) {
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(ENDPOINT)
				.path(FORECAST)
				.queryParam("appid", apiKey)
				.queryParam("q", query)
				.queryParam("units", "metric");
		return webTarget.request(MediaType.APPLICATION_JSON).get();
	}

	private Map<LocalDate, List<Measure>> parseResponse(Response response) throws ResponseFormatException {
		Map<LocalDate, List<Measure>> forecasts = new HashMap<>();
		JsonNode json = asJson(response);
		JsonNode list = json.get("list");
		if (list.isArray()) {
			for (final JsonNode element : list) {
				Measure measure = buildMeasure(element);
				LocalDate date = toLocalDate(measure.getTimestamp());
				List<Measure> measures;
				if (forecasts.containsKey(date)) {
					measures = forecasts.get(date);
				} else {
					measures = new ArrayList<>();
				}
				measures.add(measure);
				forecasts.put(date, measures);
			}
		}
		return forecasts;
	}

	/*
	 * TODO better error handling:
	 * - list can miss
	 * - main can miss
	 * - dt, temp and
	 * pressure can miss or data type conversion can fail.
	 * Either throw a new "MalformedResponseException" or log and skip the element.
	 */
	private Measure buildMeasure(final JsonNode element) {
		Long timestamp = element.get("dt").asLong();
		JsonNode main = element.get("main");
		Double temperature = main.get("temp").asDouble();
		Double pressure = main.get("pressure").asDouble();
		return Measure.of(timestamp, temperature, pressure);
	}

	private LocalDate toLocalDate(Long timestamp) {
		return Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	/*
	 * TODO better error handling:
	 * - message can miss
	 */
	private String getErrorMessage(Response response) throws ResponseFormatException {
		JsonNode json = asJson(response);
		return json.get("message").asText();
	}

	private JsonNode asJson(Response response) throws ResponseFormatException {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readTree(response.readEntity(String.class));
		} catch (IOException e) {
			logger.error("cannot read response body", e);
			throw new ResponseFormatException(e);
		}
	}

}
