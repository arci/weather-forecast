package it.arcidiacono.weatherforecast.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.QueryParam;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ForecastRequest {

	@QueryParam("city")
	@NotNull(message = "city may not be null")
	@Pattern(regexp = "\\w+", message = "city name must be a string")
	private String city;

	@QueryParam("country")
	@NotNull(message = "country may not be null")
	@Pattern(regexp = "\\w\\w", message = "country code should have two characters")
	private String country;

}
