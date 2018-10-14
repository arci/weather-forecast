package it.arcidiacono.weatherforecast.request;

import javax.validation.constraints.Pattern;
import javax.ws.rs.QueryParam;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ForecastRequest {

	@QueryParam("city")
	@Pattern(regexp = "\\w+", message = "city name must be a string")
	private String city;

	@QueryParam("country")
	@Pattern(regexp = "\\w\\w", message = "contry code should have two characters")
	private String country;

}
