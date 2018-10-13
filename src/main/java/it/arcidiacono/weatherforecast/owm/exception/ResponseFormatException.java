package it.arcidiacono.weatherforecast.owm.exception;

import javax.ws.rs.core.Response.Status;

public class ResponseFormatException extends OWMException {

	private static final long serialVersionUID = 6681020281016963298L;

	private static final int STATUS_CODE = Status.INTERNAL_SERVER_ERROR.getStatusCode();

	public ResponseFormatException() {
		super(STATUS_CODE);
	}

	public ResponseFormatException(String message, Throwable cause) {
		super(message, STATUS_CODE, cause);
	}

	public ResponseFormatException(String message) {
		super(message, STATUS_CODE);
	}

	public ResponseFormatException(Throwable cause) {
		super(STATUS_CODE, cause);
	}

}
