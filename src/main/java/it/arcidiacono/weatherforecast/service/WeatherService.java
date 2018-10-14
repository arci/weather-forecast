package it.arcidiacono.weatherforecast.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import it.arcidiacono.weatherforecast.bean.City;
import it.arcidiacono.weatherforecast.exception.ServiceException;
import it.arcidiacono.weatherforecast.owm.bean.Measure;

public interface WeatherService {

	/**
	 * Get list of forecast measures for the given city.
	 *
	 * @param  city             the city to get forecast of
	 * @return                  a map where keys are {@linkplain LocalDate} of the forecasted day and values are lists of
	 *                          {@linkplain Measure}
	 * @throws ServiceException if any error occur
	 */
	Map<LocalDate, List<Measure>> getForecast(City city) throws ServiceException;

}
