package it.arcidiacono.weatherforecast.service.owm;

import java.util.List;

import javax.annotation.PostConstruct;

import it.arcidiacono.weatherforecast.bean.City;
import it.arcidiacono.weatherforecast.exception.ServiceException;
import it.arcidiacono.weatherforecast.owm.OWMClient;
import it.arcidiacono.weatherforecast.owm.exception.OWMException;
import it.arcidiacono.weatherforecast.own.Measure;
import it.arcidiacono.weatherforecast.service.WeatherService;

public class OWMWeatherService implements WeatherService {

	private OWMClient client;

	@PostConstruct
	public void initialize() {
		// TODO read apiKey from configuration
		this.client = new OWMClient("5935b02a6ede8356a952143cdde6696b");
	}

	@Override
	public List<Measure>  getForecast (City city) throws ServiceException {
		try {
			return client.getForecast(city.getCode());
		} catch (OWMException e) {
			throw new ServiceException(e.getMessage(), e.getStatusCode());
		}
	}

}
