package it.arcidiacono.weatherforecast.bean;

import lombok.Data;
import lombok.NonNull;

@Data(staticConstructor="of")
public class City {

	@NonNull
	private String name;

	@NonNull
	private String country;

}
