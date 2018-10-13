package it.arcidiacono.weatherforecast.bean;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WeatherData {

	@NotNull
	private Double daily;

	@NotNull
	private Double nightly;

	@NotNull
	private Double pressure;

}
