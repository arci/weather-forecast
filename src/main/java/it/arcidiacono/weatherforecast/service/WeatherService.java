package it.arcidiacono.weatherforecast.service;

import java.util.List;

import javax.inject.Inject;

import it.arcidiacono.weatherforecast.bean.City;
import it.arcidiacono.weatherforecast.owm.OWMClient;
import it.arcidiacono.weatherforecast.owm.exception.OWMException;
import it.arcidiacono.weatherforecast.own.Measure;

public class WeatherService {

	@Inject
	private OWMClient client;

	public List<Measure>  getForecast (City city) throws OWMException {
		return client.getForecast(city.getCode());
	}

}
