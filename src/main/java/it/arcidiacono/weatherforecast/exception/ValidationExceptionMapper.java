package it.arcidiacono.weatherforecast.exception;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import it.arcidiacono.weatherforecast.bean.Error;

public final class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

	@Override
	public Response toResponse(ValidationException exception) {
		if (exception instanceof ConstraintViolationException) {
			final ConstraintViolationException cve = (ConstraintViolationException) exception;

			List<Error> errors = new ArrayList<>();
			for (ConstraintViolation<?> violation : cve.getConstraintViolations()) {
				errors.add(Error.of(violation.getMessage()));
			}

			return Response
					.status(Status.BAD_REQUEST)
					.entity(errors)
					.type(MediaType.APPLICATION_JSON)
					.build();
		} else {
			return Response.serverError().entity(exception.getMessage()).build();
		}
	}

}
