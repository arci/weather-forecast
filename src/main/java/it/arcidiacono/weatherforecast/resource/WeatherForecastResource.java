package it.arcidiacono.weatherforecast.resource;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.arcidiacono.weatherforecast.bean.WeatherData;
import it.arcidiacono.weatherforecast.exception.ServiceException;
import it.arcidiacono.weatherforecast.operation.WeatherOperation;
import it.arcidiacono.weatherforecast.request.ForecastRequest;

@Path("data")
public class WeatherForecastResource {

	@Inject
	private WeatherOperation operation;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCityData(@Valid @BeanParam ForecastRequest request) throws ServiceException {
		WeatherData data = operation.getData(request.getCity(), request.getCountry());
		return Response.ok().entity(data).build();
	}

}
