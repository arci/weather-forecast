package it.arcidiacono.weatherforecast.operation;

import java.time.Instant;
import java.time.LocalTime;
import java.util.List;
import java.util.TimeZone;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.arcidiacono.weatherforecast.bean.City;
import it.arcidiacono.weatherforecast.bean.WeatherData;
import it.arcidiacono.weatherforecast.exception.ServiceException;
import it.arcidiacono.weatherforecast.own.Measure;
import it.arcidiacono.weatherforecast.service.WeatherService;

public class WeatherOperation {

	private static Logger logger = LoggerFactory.getLogger(WeatherOperation.class);

	private LocalTime eight = LocalTime.parse("08:00:00");

	private LocalTime eighteen = LocalTime.parse("18:00:00");

	@Inject
	private WeatherService service;

	public WeatherData getData (String name, String country) throws ServiceException {
		City city = City.of(name, country);
		List<Measure> forecast = service.getForecast(city);
		logger.info("retrieved {} forecasted measueres", forecast.size());
		return aggregateData(forecast);
	}

	private WeatherData aggregateData (List<Measure> forecast) {
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
		return WeatherData.of(daily, nightly, pressure);
	}

	private boolean isDaytime (Instant timestamp) {
		LocalTime target =  LocalTime.from(timestamp.atZone(TimeZone.getDefault().toZoneId()));
		return target.isAfter(eight) && target.isBefore(eighteen);
	}

}
