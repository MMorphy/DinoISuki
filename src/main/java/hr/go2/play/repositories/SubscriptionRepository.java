package hr.go2.play.repositories;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hr.go2.play.entities.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long>{

	Collection<Subscription> findByValidFrom (Date validFrom);

	Collection<Subscription> findByValidTo (Date validTo);

	Collection<Subscription> findBySubscriptionType_Name (String name);
}
