package it.arcidiacono.weatherforecast.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import it.arcidiacono.weatherforecast.bean.City;
import it.arcidiacono.weatherforecast.exception.ServiceException;
import it.arcidiacono.weatherforecast.operation.WeatherOperation;
import it.arcidiacono.weatherforecast.owm.bean.Measure;
import it.arcidiacono.weatherforecast.response.AverageForecast;
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
		Mockito.when(service.getForecast(any(City.class))).thenReturn(Collections.emptyMap());

		WeatherForecast data = operation.getData("", "");
		assertTrue(data.getForecast().isEmpty());
	}

	@Test
	public void singleDaySingleMeasureForecast() throws ServiceException {
		// prepare measures
		Long now = Instant.now().getEpochSecond();
		Double temperature = Double.valueOf(10.0);
		Double pressure = Double.valueOf(1000.0);
		Measure measure = Measure.of(now, temperature, pressure);

		// prepare forecasts
		List<Measure> measures = toList(measure);
		Map<LocalDate, List<Measure>> forecasts = new HashMap<>();
		LocalDate localDate = toLocalDate(now);
		forecasts.put(localDate, measures);

		// mock
		Mockito.when(service.getForecast(any(City.class))).thenReturn(forecasts);

		// assert
		WeatherForecast data = operation.getData("", "");
		assertEquals(1, data.getForecast().size());
		AverageForecast forecast = data.getForecast().get(localDate);
		if (forecast.getDaily() != null) {
			assertNull(forecast.getNightly());
			assertEquals(temperature, forecast.getDaily());
		} else {
			assertNull(forecast.getDaily());
			assertEquals(temperature, forecast.getNightly());
		}
		assertEquals(pressure, forecast.getPressure());
	}

	@Test
	public void singelDayMultiMeasureForecast() throws ServiceException {
		// prepare measures
		Long eightFifteenTime = getTodayTimeAt("08", "15");
		Double eightFifteenTemperature = Double.valueOf(12.0);
		Double eightFifteenPressure = Double.valueOf(1003.1);
		Measure eightFifteen = Measure.of(eightFifteenTime, eightFifteenTemperature, eightFifteenPressure);

		Long noonTime = getTodayTimeAt("12");
		Double noonTemperature = Double.valueOf(10.0);
		Double noonPressure = Double.valueOf(1000.0);
		Measure noon = Measure.of(noonTime, noonTemperature, noonPressure);

		Long elevenFourtyTime = getTodayTimeAt("23", "40");
		Double elevenFourtyTemperature = Double.valueOf(12.8);
		Double elevenFourtyPressure = Double.valueOf(1040.6);
		Measure elevenFourty = Measure.of(elevenFourtyTime, elevenFourtyTemperature, elevenFourtyPressure);

		Long midnightTime = getTodayTimeAt("00");
		Double midnightTemperature = Double.valueOf(13.0);
		Double midnightPressure = Double.valueOf(1050.0);
		Measure midnight = Measure.of(midnightTime, midnightTemperature, midnightPressure);

		// prepare forecasts
		List<Measure> measures = toList(eightFifteen, noon, elevenFourty, midnight);
		Map<LocalDate, List<Measure>> forecasts = new HashMap<>();
		LocalDate localDate = toLocalDate(eightFifteenTime);
		forecasts.put(localDate, measures);

		// mock
		Mockito.when(service.getForecast(any(City.class))).thenReturn(forecasts);

		// assert
		Double dailyTemperature = (eightFifteenTemperature + noonTemperature) / 2;
		Double nightlyTemperature = (elevenFourtyTemperature + midnightTemperature) / 2;
		Double pressure = (eightFifteenPressure + noonPressure + elevenFourtyPressure + midnightPressure) / 4;

		WeatherForecast data = operation.getData("", "");
		assertEquals(1, data.getForecast().size());
		AverageForecast forecast = data.getForecast().get(localDate);
		assertEquals(dailyTemperature, forecast.getDaily());
		assertEquals(nightlyTemperature, forecast.getNightly());
		assertEquals(pressure, forecast.getPressure());
	}

	private List<Measure> toList(Measure... measures) {
		return Arrays.asList(measures);
	}

	private Long getTodayTimeAt(String hour) {
		return getTodayTimeAt(hour, "00", "00");
	}

	private Long getTodayTimeAt(String hour, String minutes) {
		return getTodayTimeAt(hour, minutes, "00");
	}

	private Long getTodayTimeAt(String hour, String minutes, String seconds) {
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.parse(hour + ":" + minutes + ":" + seconds);
		LocalDateTime dateTime = LocalDateTime.of(date, time);
		Instant instant = dateTime.atZone(ZoneId.systemDefault()).toInstant();
		return instant.toEpochMilli();
	}

	private LocalDate toLocalDate(Long timestamp) {
		return Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();
	}
}
