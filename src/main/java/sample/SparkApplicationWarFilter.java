package sample;

import spark.servlet.SparkFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

/** Used if you want to deploy to Tomcat */
@WebFilter(urlPatterns = "/*", initParams = { @WebInitParam(name = "applicationClass", value = "sample.SparkApplication") })
public class SparkApplicationWarFilter implements Filter {

	private SparkFilter sparkFilter;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		sparkFilter = new SparkFilter();

		try {
			sparkFilter.init(filterConfig);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		sparkFilter.doFilter(request, response, chain);
	}

	@Override
	public void destroy() {
		sparkFilter.destroy();
	}
}
