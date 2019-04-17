package sample.bikes;

import com.google.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

public class BikeNoOrmService {

	private Sql2o sql2o;

	@Inject
	public BikeNoOrmService(Sql2o sql2o) {
		this.sql2o = sql2o;
	}

	public List<Bike> findAll() {
		try(Connection connection = sql2o.open()){
			return connection.createQuery("select * from bikes").executeAndFetch(Bike.class);
		}
	}

//	public BikeView create(BikeView view) {
//		try(Connection connection = sql2o.open()){
//			connection.createQuery("select ")
//			return connection.createQuery("insert into bikes (name) values (:id, :name)")
//				.addParameter().executeAndFetch(Bike.class);
//		}
//		Bike bike = view.toBike();
//		em.get().persist(bike);
//		BikeView result = BikeView.of(bike);
//		EventBus.getDefault().post(result);
//		return result;
//	}
}
