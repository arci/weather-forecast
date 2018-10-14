package it.arcidiacono.weatherforecast;

import javax.ws.rs.ext.ContextResolver;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {

	private final ObjectMapper mapper;

	public ObjectMapperContextResolver() {
		mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
	}

	@Override
	public ObjectMapper getContext(Class<?> type) {
		return mapper;
	}
}
