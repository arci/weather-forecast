package it.arcidiacono.weatherforecast.response;

import lombok.Data;
import lombok.NonNull;

@Data(staticConstructor = "of")
public class Error {

	@NonNull
	private String message;

}
