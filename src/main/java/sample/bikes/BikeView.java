package sample.bikes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BikeView {

	private String name;

	@JsonCreator
	public BikeView(
		@JsonProperty("name") String name) {
		this.name = name;
	}

	public static BikeView of(Bike bike) {
		return new BikeView(bike.name);
	}

	public String getName() {
		return name;
	}

	public Bike toBike() {
		return new Bike(name);
	}
}
