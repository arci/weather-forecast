package it.arcidiacono.weatherforecast.operation;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.arcidiacono.weatherforecast.bean.City;
import it.arcidiacono.weatherforecast.exception.ServiceException;
import it.arcidiacono.weatherforecast.own.bean.Measure;
import it.arcidiacono.weatherforecast.response.AverageForecast;
import it.arcidiacono.weatherforecast.response.WeatherForecast;
import it.arcidiacono.weatherforecast.service.WeatherService;

public class WeatherOperation {

	private static Logger logger = LoggerFactory.getLogger(WeatherOperation.class);

	private LocalTime eight = LocalTime.parse("08:00:00");

	private LocalTime eighteen = LocalTime.parse("18:00:00");

	@Inject
	private WeatherService service;

	public WeatherForecast getData(String name, String country) throws ServiceException {
		City city = City.of(name, country);
		Map<LocalDate, List<Measure>> forecasts = service.getForecast(city);
		logger.info("forecasted {} days", forecasts.size());

		Map<LocalDate, AverageForecast> averages = new HashMap<>();
		for (Entry<LocalDate, List<Measure>> entry : forecasts.entrySet()) {
			AverageForecast average = aggregateMeasures(entry.getValue());
			averages.put(entry.getKey(), average);
		}
		return WeatherForecast.of(averages);
	}

	private AverageForecast aggregateMeasures(List<Measure> forecast) {
		double dailySum = 0;
		int dailyMeasures = 0;
		double nightlySum = 0;
		int nightlyMeasures = 0;
		double pressureSum = 0;
		int pressureMeasures = 0;
		for (Measure measure : forecast) {
			if (measure.getPressure() != null) {
				pressureSum += measure.getPressure();
				pressureMeasures++;
			}
			if (measure.getTemperature() != null) {
				if (isDaytime(measure.getTimestamp())) {
					dailySum += measure.getTemperature();
					dailyMeasures++;
				} else {
					nightlySum += measure.getTemperature();
					nightlyMeasures++;
				}
			}
		}

		Double daily = dailyMeasures > 0 ? dailySum / dailyMeasures : null;
		Double nightly = nightlyMeasures > 0 ? nightlySum / nightlyMeasures : null;
		Double pressure = pressureMeasures > 0 ? pressureSum / pressureMeasures : null;
		return AverageForecast.of(daily, nightly, pressure);
	}

	private boolean isDaytime(Long timestamp) {
		LocalTime target = toLocalTime(timestamp);
		return target.isAfter(eight) && target.isBefore(eighteen);
	}

	private LocalTime toLocalTime(Long timestamp) {
		Instant instant = Instant.ofEpochSecond(timestamp);
		return LocalTime.from(instant.atZone(ZoneId.systemDefault()));
	}

}
