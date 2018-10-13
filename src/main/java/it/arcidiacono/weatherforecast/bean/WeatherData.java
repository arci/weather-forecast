package it.arcidiacono.weatherforecast.bean;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName="of")
@NoArgsConstructor
public class WeatherData {

	@NotNull
	private Double daily;

	@NotNull
	private Double nightly;

	@NotNull
	private Double pressure;

}
