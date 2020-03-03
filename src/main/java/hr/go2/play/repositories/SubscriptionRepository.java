package hr.go2.play.repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hr.go2.play.entities.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long>{

	Collection<Subscription> findByValidFrom (Date validFrom);

	Collection<Subscription> findByValidTo (Date validTo);

	Collection<Subscription> findBySubscriptionType_Name (String name);

	@Modifying
	@Query("UPDATE Subscription s SET s.valid = FALSE WHERE s.validTo < DATE(?1) AND s.valid = TRUE")
	void updateValidityByTime(Date date);

	@Query("SELECT COUNT(s.id) FROM Subscription s WHERE s.validTo < DATE(?1) AND s.valid = TRUE")
	int numberOfInvalidatingSubscriptions(Date date);
}
