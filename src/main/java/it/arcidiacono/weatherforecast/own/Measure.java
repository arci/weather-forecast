package it.arcidiacono.weatherforecast.own;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class Measure {

	private Instant timestamp;

	private Double temperature;

	private Double pressure;

}
