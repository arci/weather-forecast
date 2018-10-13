package it.arcidiacono.weatherforecast.operation;

import javax.inject.Inject;

import it.arcidiacono.weatherforecast.bean.WeatherData;
import it.arcidiacono.weatherforecast.service.WeatherService;

public class WeatherOperation {

	@Inject
	private WeatherService service;

	public WeatherData getData (String name, String country) {
		// TODO Auto-generated method stub
		return null;
	}

}
