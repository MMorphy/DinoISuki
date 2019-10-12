package hr.go2.play.repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.go2.play.entities.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long>{

	Collection<Subscription> findByValidFrom (Date validFrom);

	Collection<Subscription> findByValidTo (Date validTo);

	Collection<Subscription> findBySubscriptionType_Name (String name);
}
