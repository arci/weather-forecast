package it.arcidiacono.weatherforecast.owm.exception;

import lombok.Getter;

@Getter
public class OWMException extends Exception {

	private static final long serialVersionUID = 2966171146717646840L;

	private final Integer statusCode;

	public OWMException(Integer statusCode) {
		super();
		this.statusCode = statusCode;
	}

	public OWMException(Integer statusCode, Throwable cause) {
		super(cause);
		this.statusCode = statusCode;
	}

	public OWMException(String message, Integer statusCode) {
		super(message);
		this.statusCode = statusCode;
	}

	public OWMException(String message, Integer statusCode, Throwable cause) {
		super(message, cause);
		this.statusCode = statusCode;
	}

}
