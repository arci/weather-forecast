package it.arcidiacono.weatherforecast.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class AverageForecast {

	@Schema(description = "The daily average temperature in Celsius")
	private Double daily;

	@Schema(description = "The nightly average temperature in Celsius")
	private Double nightly;

	@Schema(description = "The average pressure")
	private Double pressure;

}
