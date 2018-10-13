package it.arcidiacono.weatherforecast.operation;

import java.util.List;

import javax.inject.Inject;

import it.arcidiacono.weatherforecast.bean.City;
import it.arcidiacono.weatherforecast.bean.WeatherData;
import it.arcidiacono.weatherforecast.exception.ServiceException;
import it.arcidiacono.weatherforecast.own.Measure;
import it.arcidiacono.weatherforecast.service.WeatherService;

public class WeatherOperation {

	@Inject
	private WeatherService service;

	public WeatherData getData (String name, String country) throws ServiceException {
		City city = City.of(6542283, "Milan", "IT");
		List<Measure> forecast = service.getForecast(city);
		// TODO business logic
		return new WeatherData();
	}

}
