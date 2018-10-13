package it.arcidiacono.weatherforecast.owm.exception;

import javax.ws.rs.core.Response.Status;

public class CityNotFoundException extends OWMException {

	private static final long serialVersionUID = 6681020281016963298L;

	private static final int STATUS_CODE = Status.NOT_FOUND.getStatusCode();

	public CityNotFoundException() {
		super(STATUS_CODE);
	}

	public CityNotFoundException(String message, Throwable cause) {
		super(message, STATUS_CODE, cause);
	}

	public CityNotFoundException(String message) {
		super(message, STATUS_CODE);
	}

	public CityNotFoundException(Throwable cause) {
		super(STATUS_CODE, cause);
	}

}
