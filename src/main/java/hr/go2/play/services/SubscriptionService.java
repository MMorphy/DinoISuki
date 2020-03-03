package hr.go2.play.services;

import java.util.Date;
import java.util.List;

import hr.go2.play.entities.Subscription;

public interface SubscriptionService {

	public abstract List<Subscription> findAllSubcscriptions();

	public abstract Subscription findSubscriptionById(Long id);

	public abstract List<Subscription> findSubscriptionByValidFrom(Date validFrom);

	public abstract List<Subscription> findSubscriptionByValidTo(Date validTo);

	public abstract List<Subscription> findSubscriptionBySubTypeName(String name);

	public abstract void deleteSubscriptionById(Long id);

	public abstract Subscription saveSubscription(Subscription subscription);

	public abstract Subscription updateSubscription(Long id, Subscription subscription);

	public abstract void deleteAllSubscriptions();

	public abstract void updateValidityByTime(Date date);

	public abstract int numberOfInvalidatingSubscriptions(Date date);

}
