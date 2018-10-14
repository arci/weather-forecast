package it.arcidiacono.weatherforecast.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import it.arcidiacono.weatherforecast.bean.City;
import it.arcidiacono.weatherforecast.exception.ServiceException;
import it.arcidiacono.weatherforecast.operation.WeatherOperation;
import it.arcidiacono.weatherforecast.own.bean.Measure;
import it.arcidiacono.weatherforecast.response.WeatherForecast;
import it.arcidiacono.weatherforecast.service.WeatherService;

@RunWith(MockitoJUnitRunner.class)
public class WeatherOperationTest {

	@Mock
	private WeatherService service;

	@InjectMocks
	private WeatherOperation operation;

	@Test
	public void emptyForecast() throws ServiceException {
		Mockito.when(service.getForecast(any(City.class))).thenReturn(Collections.emptyList());

		WeatherForecast data = operation.getData("", "");
		assertNull(data.getDaily());
		assertNull(data.getNightly());
		assertNull(data.getPressure());
	}

	@Test
	public void singleMeasureForecast() throws ServiceException {
		Long now = Instant.now().getEpochSecond();
		Double temperature = Double.valueOf(10.0);
		Double pressure = Double.valueOf(1000.0);
		List<Measure> measures = prepareSingleMeasure(now, temperature, pressure);
		Mockito.when(service.getForecast(any(City.class))).thenReturn(measures);

		WeatherForecast data = operation.getData("", "");
		if (data.getDaily() != null) {
			assertNull(data.getNightly());
			assertEquals(temperature, data.getDaily());
		} else {
			assertNull(data.getDaily());
			assertEquals(temperature, data.getNightly());
		}
		assertEquals(pressure, data.getPressure());
	}

	@Test
	public void multiMeasureForecast() throws ServiceException {
		Long eightFifteenTime = getTimeAt("08", "15");
		Double eightFifteenTemperature = Double.valueOf(12.0);
		Double eightFifteenPressure = Double.valueOf(1003.1);
		Measure eightFifteen = Measure.of(eightFifteenTime, eightFifteenTemperature, eightFifteenPressure);

		Long noonTime = getTimeAt("12");
		Double noonTemperature = Double.valueOf(10.0);
		Double noonPressure = Double.valueOf(1000.0);
		Measure noon = Measure.of(noonTime, noonTemperature, noonPressure);

		Long elevenFourtyTime = getTimeAt("23", "40");
		Double elevenFourtyTemperature = Double.valueOf(12.8);
		Double elevenFourtyPressure = Double.valueOf(1040.6);
		Measure elevenFourty = Measure.of(elevenFourtyTime, elevenFourtyTemperature, elevenFourtyPressure);

		Long midnightTime = getTimeAt("00");
		Double midnightTemperature = Double.valueOf(13.0);
		Double midnightPressure = Double.valueOf(1050.0);
		Measure midnight = Measure.of(midnightTime, midnightTemperature, midnightPressure);

		List<Measure> measures = toList(eightFifteen, noon, elevenFourty, midnight);
		Mockito.when(service.getForecast(any(City.class))).thenReturn(measures);

		Double dailyTemperature = (eightFifteenTemperature + noonTemperature) / 2;
		Double nightlyTemperature = (elevenFourtyTemperature + midnightTemperature) / 2;
		Double pressure = (eightFifteenPressure + noonPressure + elevenFourtyPressure + midnightPressure) / 4;

		WeatherForecast data = operation.getData("", "");
		assertEquals(dailyTemperature, data.getDaily());
		assertEquals(nightlyTemperature, data.getNightly());
		assertEquals(pressure, data.getPressure());
	}

	private List<Measure> toList(Measure... measures) {
		return Arrays.asList(measures);
	}

	private List<Measure> prepareSingleMeasure(Long timestamp, Double temperature, Double pressure) {
		List<Measure> measures = new ArrayList<>();
		Measure measure = Measure.of(timestamp, temperature, pressure);
		measures.add(measure);
		return measures;
	}

	private Long getTimeAt(String hour) {
		return getTimeAt(hour, "00", "00");
	}

	private Long getTimeAt(String hour, String minutes) {
		return getTimeAt(hour, minutes, "00");
	}

	private Long getTimeAt(String hour, String minutes, String seconds) {
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.parse(hour + ":" + minutes + ":" + seconds);
		LocalDateTime dateTime = LocalDateTime.of(date, time);
		ZoneId timeZone = TimeZone.getDefault().toZoneId();
		Instant instant = dateTime.atZone(timeZone).toInstant();
		return instant.toEpochMilli();
	}
}
