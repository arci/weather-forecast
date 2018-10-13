package it.arcidiacono.weatherforecast.bean;

import lombok.Data;
import lombok.NonNull;

@Data(staticConstructor = "of")
public class Error {

	@NonNull
	private String message;

}
