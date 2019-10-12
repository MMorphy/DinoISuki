package hr.go2.play.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.go2.play.entities.SubscriptionType;

public interface SubscriptionTypeRepository extends JpaRepository<SubscriptionType, Long>{

	Collection<SubscriptionType> findByName(String name);
}
