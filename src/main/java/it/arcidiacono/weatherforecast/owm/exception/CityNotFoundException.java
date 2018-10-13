package it.arcidiacono.weatherforecast.owm.exception;

import javax.ws.rs.core.Response.Status;

public class CityNotFoundException extends OWMException {

	private static final long serialVersionUID = 6681020281016963298L;

	public CityNotFoundException() {
		super(Status.NOT_FOUND.getStatusCode());
	}

	public CityNotFoundException(String message, Throwable cause) {
		super(message, Status.NOT_FOUND.getStatusCode(), cause);
	}

	public CityNotFoundException(String message) {
		super(message, Status.NOT_FOUND.getStatusCode());
	}

	public CityNotFoundException(Throwable cause) {
		super(Status.NOT_FOUND.getStatusCode(), cause);
	}

}
