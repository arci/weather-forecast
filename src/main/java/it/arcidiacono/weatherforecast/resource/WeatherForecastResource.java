package it.arcidiacono.weatherforecast.resource;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.arcidiacono.weatherforecast.exception.ServiceException;
import it.arcidiacono.weatherforecast.operation.WeatherOperation;
import it.arcidiacono.weatherforecast.request.ForecastRequest;
import it.arcidiacono.weatherforecast.response.Error;
import it.arcidiacono.weatherforecast.response.WeatherForecast;

@Path("data")
@OpenAPIDefinition(
	info = @Info(
		title = "Weather forecast",
		version = "1.0",
		description = "Weather forecast API"))
public class WeatherForecastResource {

	@Inject
	private WeatherOperation operation;

	@GET
	@Operation(
		summary = "Get weather forecast",
		description = "Get weather forecast for the given city",
		responses = {
				@ApiResponse(
					responseCode = "200",
					description = "The forecast",
					content = @Content(
						mediaType = MediaType.APPLICATION_JSON,
						schema = @Schema(
							implementation = WeatherForecast.class))),
				@ApiResponse(
					responseCode = "400",
					content = @Content(
						mediaType = MediaType.APPLICATION_JSON,
						schema = @Schema(
							implementation = Error.class))),
				@ApiResponse(
					responseCode = "401",
					content = @Content(
						mediaType = MediaType.APPLICATION_JSON,
						schema = @Schema(
							implementation = Error.class))),
				@ApiResponse(
					responseCode = "404",
					content = @Content(
						mediaType = MediaType.APPLICATION_JSON,
						schema = @Schema(
							implementation = Error.class)))
		})
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCityData(@Valid @BeanParam ForecastRequest request) throws ServiceException {
		WeatherForecast data = operation.getData(request.getCity(), request.getCountry());
		return Response.ok().entity(data).build();
	}

}
