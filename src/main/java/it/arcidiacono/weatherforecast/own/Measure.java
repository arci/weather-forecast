package it.arcidiacono.weatherforecast.own;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class Measure {

	private Long timestamp;

	private Double temperature;

	private Double pressure;

}
