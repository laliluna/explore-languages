package sample.bikes;

import com.google.inject.Inject;
import sample.Json;
import spark.RouteGroup;

import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.post;

public class BikeRoutes {

	@Inject
	private BikeOrmService bikeOrmService;

	public RouteGroup routes() {
		return () -> {
			after(Json.addJsonHeader());
			get("", (request, response) -> bikeOrmService.findAll());
			post("",
				(request, response) -> bikeOrmService.create(Json.parse(request, BikeView.class)));
		};
	}
}
