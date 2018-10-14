package it.arcidiacono.weatherforecast.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.QueryParam;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ForecastRequest {

	@Parameter(description = "The city name", required = true)
	@QueryParam("city")
	@NotNull(message = "city may not be null")
	@Pattern(regexp = "\\w+", message = "city name must be a string")
	private String city;

	@Parameter(description = "The country code", required = true)
	@QueryParam("country")
	@NotNull(message = "country may not be null")
	@Pattern(regexp = "\\w\\w", message = "country code should have two characters")
	private String country;

}
