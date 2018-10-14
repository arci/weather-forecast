package it.arcidiacono.weatherforecast.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import it.arcidiacono.weatherforecast.response.Error;

public class ServiceExceptionMapper implements ExceptionMapper<ServiceException> {

	@Override
	public Response toResponse(ServiceException exception) {
		Error bean = Error.of(exception.getMessage());
		return Response
				.status(exception.getStatusCode())
				.entity(bean)
				.type(MediaType.APPLICATION_JSON)
				.build();
	}

}
