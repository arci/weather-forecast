package it.arcidiacono.weatherforecast.owm;

import java.io.IOException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.arcidiacono.weatherforecast.owm.exception.CityNotFoundException;
import it.arcidiacono.weatherforecast.owm.exception.OWMException;

public class OWMClient {

	private static Logger logger = LoggerFactory.getLogger(OWMClient.class);

	private static final String ENDPOINT = "https://api.openweathermap.org/data/2.5";

	private static final String FORECAST = "forecast";

	private static final String API_KEY = "5935b02a6ede8356a952143cdde6696b";

	public void getForecast (Integer cityCode) throws OWMException {
		WebTarget webTarget = buildForecastWebTarget(cityCode);
		Response response = webTarget.request(MediaType.APPLICATION_JSON).get();
		if (response.getStatus() < Status.BAD_REQUEST.getStatusCode()) {
			// TODO
		} else {
			buildError(response);
		}
	}

	private WebTarget buildForecastWebTarget (Integer cityCode) {
		Client client = ClientBuilder.newClient();
		return client.target(ENDPOINT).path(FORECAST).queryParam("appid", API_KEY).queryParam("id", cityCode);
	}

	private void buildError (Response response) throws OWMException {
		String error = getErrorMessage(response);
		if (response.getStatus() == Status.NOT_FOUND.getStatusCode()) {
			throw new CityNotFoundException(error);
		}
		throw new OWMException(error, response.getStatus());
	}

	private String getErrorMessage (Response response) {
		try {
			JsonNode json = asJson(response);
			return json.get("message").asText();
		} catch (IOException e) {
			logger.error("cannot extract error message", e);
			return "generic error";
		}
	}

	private JsonNode asJson (Response response) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readTree(response.readEntity(String.class));
	}
}
