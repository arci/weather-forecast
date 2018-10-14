package it.arcidiacono.weatherforecast.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName="of")
@NoArgsConstructor
public class WeatherData {

	private Double daily;

	private Double nightly;

	private Double pressure;

}
