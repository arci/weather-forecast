package it.arcidiacono.weatherforecast.response;

import java.time.LocalDate;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class WeatherForecast {

	private Map<LocalDate, AverageForecast> forecast;

}
