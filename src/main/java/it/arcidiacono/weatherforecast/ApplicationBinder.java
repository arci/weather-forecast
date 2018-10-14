package it.arcidiacono.weatherforecast;

import org.glassfish.jersey.internal.inject.AbstractBinder;

import it.arcidiacono.weatherforecast.operation.WeatherOperation;
import it.arcidiacono.weatherforecast.service.WeatherService;
import it.arcidiacono.weatherforecast.service.owm.OWMWeatherService;

public class ApplicationBinder extends AbstractBinder {

	@Override
	protected void configure() {
		bind(WeatherOperation.class).to(WeatherOperation.class);
		bind(OWMWeatherService.class).to(WeatherService.class);
	}

}
