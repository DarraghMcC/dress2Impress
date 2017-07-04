package com.dress2Impress.common.exception;

public class WeatherServiceException extends RuntimeException {

	public WeatherServiceException(final Exception rootException){
		super(rootException);
	}

	public WeatherServiceException(final String message){
		super(message);
	}
}
