package it.arcidiacono.weatherforecast;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import it.arcidiacono.weatherforecast.exception.ServiceExceptionMapper;

@ApplicationPath("/")
public class WeatherApplication extends ResourceConfig {

	private static final String RESOURCE_PACKAGE = "it.arcidiacono.weatherforecast.resource";

	public WeatherApplication() {
		packages(RESOURCE_PACKAGE);

		register(new ApplicationBinder());

		register(ServiceExceptionMapper.class);

		register(JacksonFeature.class);
	}

}
