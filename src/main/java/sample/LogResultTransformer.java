package sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ResponseTransformer;

import java.util.Collection;

public class LogResultTransformer implements ResponseTransformer {

	private static final Logger LOG = LoggerFactory.getLogger(SparkApplication.class);

	private final ResponseTransformer transformer;

	public LogResultTransformer(ResponseTransformer transformer) {
		this.transformer = transformer;
	}

	@Override
	public String render(Object model) throws Exception {
		if (model instanceof Collection) {
			LOG.debug("Result: {} items", ((Collection) model).size());
		} else {
			LOG.debug("Result: {} ", model == null ? null : model.getClass());
		}
		return transformer.render(model);
	}
}
