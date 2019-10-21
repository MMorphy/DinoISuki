package hr.go2.play.services;

import java.util.List;

import hr.go2.play.entities.SubscriptionType;

public interface SubscriptionTypeService {
	
	public abstract List<SubscriptionType> findAllSubscriptionTypes();
	
	public abstract SubscriptionType findSubscriptionTypeById(Long id);
	
	public abstract List<SubscriptionType> findSubscriptionTypeByName(String name);
	
	public abstract void deleteSubscriptionTypeById(Long id);
	
	public abstract SubscriptionType saveSubscriptionType(SubscriptionType subType);
	
	public abstract SubscriptionType updateSubscriptionType(Long id, SubscriptionType subType);

	public abstract void deleteAllSubscriptionTypes();

}
