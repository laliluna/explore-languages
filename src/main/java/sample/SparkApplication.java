package sample;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.bikes.BikeModule;
import sample.bikes.BikeRoutes;
import sample.bikes.BikeWebSocket;
import spark.Spark;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SparkApplication implements spark.servlet.SparkApplication {

	private static final Logger LOG = LoggerFactory.getLogger(SparkApplication.class);

	static ExecutorService executor;

	static {
		executor = Executors.newFixedThreadPool(5);
	}

	public static void main(String[] args) {
		new SparkApplication().init();
	}

	@Override
	public void init() {
		Injector injector = startGuice();
		configureRoutes(injector);
	}

	private void configureRoutes(Injector injector) {
		Spark.defaultResponseTransformer(new LogResultTransformer(new Json.JsonTransformer()));
		Spark.webSocket("/ws", injector.getInstance(BikeWebSocket.class));
		Spark.before("/*", (q, a) -> LOG.info("{} {}", q.requestMethod(), q.pathInfo()));
		Spark.path("/bikes", injector.getInstance(BikeRoutes.class).routes());

	}

	private Injector startGuice() {
		Injector injector = Guice.createInjector(new JpaPersistModule("myFirstJpaUnit"), new BikeModule());
		executor.submit(() -> {
			injector.getInstance(PersistService.class).start();
		});
		return injector;
	}

}
