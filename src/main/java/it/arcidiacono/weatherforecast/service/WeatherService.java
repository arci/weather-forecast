package it.arcidiacono.weatherforecast.service;

import java.util.List;

import javax.annotation.PostConstruct;

import it.arcidiacono.weatherforecast.bean.City;
import it.arcidiacono.weatherforecast.owm.OWMClient;
import it.arcidiacono.weatherforecast.owm.exception.OWMException;
import it.arcidiacono.weatherforecast.own.Measure;

public class WeatherService {

	private OWMClient client;

	@PostConstruct
	public void initialize() {
		// TODO read apiKey from configuration
		this.client = new OWMClient("5935b02a6ede8356a952143cdde6696b");
	}

	public List<Measure>  getForecast (City city) throws OWMException {
		return client.getForecast(city.getCode());
	}

}
