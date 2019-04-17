package sample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Filter;
import spark.Request;
import spark.ResponseTransformer;

import java.io.IOException;

public class Json {

	static ObjectMapper objectMapper;

	static {
		objectMapper = new ObjectMapper();
	}

	public static Filter addJsonHeader() {
		return (request, response) -> response.header("Content-Type", "application/json");
	}

	public static <T> T parse(Request request, Class<T> resultClass) {
		try {
			return objectMapper.readValue(request.body(), resultClass);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static class JsonTransformer implements ResponseTransformer {

		@Override
		public String render(Object model) throws Exception {
			return objectMapper.writeValueAsString(model);
		}
	}

	public static String encode(Object model) {
		try {
			return objectMapper.writeValueAsString(model);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

}
