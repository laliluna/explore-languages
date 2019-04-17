package sample.bikes;

import com.google.inject.Provider;
import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

public class BikeOrmService {

	@Inject
	Provider<EntityManager> em;

	public List<Bike> findAll() {
		return em.get().createQuery("select b from Bike b").getResultList();
	}

	@com.google.inject.persist.Transactional
	public BikeView create(BikeView view) {
		Bike bike = view.toBike();
		em.get().persist(bike);
		BikeView result = BikeView.of(bike);
		EventBus.getDefault().post(result);
		return result;
	}
}
