package sample.bikes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Random;

@Entity
@Table(name = "bikes")
public class Bike {

	@Id
	@GeneratedValue
	Integer id;

	String name;

	public Bike() {
	}

	public Bike(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Integer getId() {
		return id;
	}
}
