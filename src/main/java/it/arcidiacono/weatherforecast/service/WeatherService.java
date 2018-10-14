package it.arcidiacono.weatherforecast.service;

import java.util.List;

import it.arcidiacono.weatherforecast.bean.City;
import it.arcidiacono.weatherforecast.exception.ServiceException;
import it.arcidiacono.weatherforecast.own.bean.Measure;

public interface WeatherService {

	/**
	 * Get list of forecast measures for the given city,
	 *
	 * @param  city             the city to get forecast of
	 * @return                  the list of retrieved measures
	 * @throws ServiceException if any error occur
	 */
	List<Measure> getForecast(City city) throws ServiceException;

}
