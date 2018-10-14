package it.arcidiacono.weatherforecast.resource;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.arcidiacono.weatherforecast.bean.WeatherData;
import it.arcidiacono.weatherforecast.exception.ServiceException;
import it.arcidiacono.weatherforecast.operation.WeatherOperation;

@Path("data/{city}/{country}")
public class WeatherForecastResource {

	@Inject
	private WeatherOperation operation;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCityData(
			@PathParam("city") String city,
			@PathParam("country") String country) throws ServiceException {
		WeatherData data = operation.getData(city, country);
		return Response.ok().entity(data).build();
	}

}
