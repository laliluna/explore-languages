package sample.bikes;

import com.google.common.eventbus.AsyncEventBus;
import com.google.inject.AbstractModule;
import org.sql2o.Sql2o;
import org.sql2o.converters.UUIDConverter;
import org.sql2o.quirks.PostgresQuirks;

import java.util.UUID;
import java.util.concurrent.Executors;

public class BikeModule extends AbstractModule {

	@Override
	protected void configure() {

		Sql2o sql2o = new Sql2o("jdbc:postgresql:sample",
			"postgres", "p", new PostgresQuirks() {

			{
				// make sure we use default UUID converter.
				converters.put(UUID.class, new UUIDConverter());
			}
		});
		bind(Sql2o.class).toInstance(sql2o);
		bind(BikeOrmService.class);
		bind(BikeNoOrmService.class);
		bind(BikeWebSocket.class);
		AsyncEventBus eventBus = new AsyncEventBus(Executors.newFixedThreadPool(5));
		bind(AsyncEventBus.class).toInstance(eventBus);
	}
}
