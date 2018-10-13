package it.arcidiacono.weatherforecast.exception;

import lombok.Getter;

@Getter
public class ServiceException extends Exception {

	private static final long serialVersionUID = -3427847316058612342L;

	private final Integer statusCode;

	public ServiceException(Integer statusCode) {
		super();
		this.statusCode = statusCode;
	}

	public ServiceException(String message, Integer statusCode, Throwable cause) {
		super(message, cause);
		this.statusCode = statusCode;
	}

	public ServiceException(String message, Integer statusCode) {
		super(message);
		this.statusCode = statusCode;
	}

	public ServiceException(Integer statusCode, Throwable cause) {
		super(cause);
		this.statusCode = statusCode;
	}

}
