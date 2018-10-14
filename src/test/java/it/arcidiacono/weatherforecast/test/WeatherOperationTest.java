package it.arcidiacono.weatherforecast.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import it.arcidiacono.weatherforecast.bean.City;
import it.arcidiacono.weatherforecast.bean.WeatherData;
import it.arcidiacono.weatherforecast.exception.ServiceException;
import it.arcidiacono.weatherforecast.operation.WeatherOperation;
import it.arcidiacono.weatherforecast.own.Measure;
import it.arcidiacono.weatherforecast.service.WeatherService;

@RunWith(MockitoJUnitRunner.class)
public class WeatherOperationTest {

	@Mock
	private WeatherService service;

	@InjectMocks
	private WeatherOperation operation;

	@Test
	public void emptyForecast () throws ServiceException {
		Mockito.when(service.getForecast(any(City.class))).thenReturn(Collections.emptyList());

		WeatherData data = operation.getData("", "");
		assertNull(data.getDaily());
		assertNull(data.getNightly());
		assertNull(data.getPressure());
	}

	@Test
	public void singleMeasureForecast () throws ServiceException {
		List<Measure> measures = new ArrayList<>();
		Measure measure = Measure.of(Instant.now(), 10.0, 1000.0);
		measures.add(measure);
		Mockito.when(service.getForecast(any(City.class))).thenReturn(measures);

		WeatherData data = operation.getData("", "");
		if (data.getDaily() != null) {
			assertNull(data.getNightly());
			assertEquals(Double.valueOf(10.0), data.getDaily());
		} else {
			assertNull(data.getDaily());
			assertEquals(Double.valueOf(10.0), data.getNightly());
		}
		assertEquals(Double.valueOf(1000.0), data.getPressure());
	}

	@Test
	public void multiMeasureForecast () throws ServiceException {
		List<Measure> measures = new ArrayList<>();
		Instant now = Instant.now();
		double temp1 = 10.0;
		double pressure1 = 1000.0;
		measures.add(Measure.of(now, temp1, pressure1));
		double temp2 = 12.0;
		double pressure2 = 1003.1;
		measures.add(Measure.of(now, temp2, pressure2));
		double temp3 = 13.0;
		double pressure3 = 1050.0;
		measures.add(Measure.of(now, temp3, pressure3));
		Mockito.when(service.getForecast(any(City.class))).thenReturn(measures);

		WeatherData data = operation.getData("", "");
		double averageTemp = (temp1 + temp2 + temp3) / 3;
		if (data.getDaily() != null) {
			assertNull(data.getNightly());
			assertEquals(Double.valueOf(averageTemp), data.getDaily());
		} else {
			assertNull(data.getDaily());
			assertEquals(Double.valueOf(averageTemp), data.getNightly());
		}
		double averagePressure = (pressure1 + pressure2 + pressure3) / 3;
		assertEquals(Double.valueOf(averagePressure), data.getPressure());
	}
}
