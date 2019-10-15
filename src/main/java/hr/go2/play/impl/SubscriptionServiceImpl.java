package hr.go2.play.impl;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import hr.go2.play.entities.Subscription;
import hr.go2.play.repositories.SubscriptionRepository;
import hr.go2.play.services.SubscriptionService;

public class SubscriptionServiceImpl implements SubscriptionService {
	
	@Autowired
	private SubscriptionRepository subRepo;
	
	public SubscriptionServiceImpl(SubscriptionRepository subRepo) {
		this.subRepo = subRepo;
	}

	@Override
	public List<Subscription> findAllSubcscriptions() {
		return this.subRepo.findAll();
	}

	@Override
	public Subscription findSubscriptionById(Long id) {
		try {
			return this.subRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	@Override
	public List<Subscription> findSubscriptionByValidFrom(Date validFrom) {
		return (List<Subscription>) this.subRepo.findByValidFrom(validFrom);
	}

	@Override
	public List<Subscription> findSubscriptionByValidTo(Date validTo) {
		return (List<Subscription>) this.subRepo.findByValidTo(validTo);
	}

	@Override
	public List<Subscription> findSubscriptionBySubTypeName(String name) {
		return (List<Subscription>) this.subRepo.findBySubscriptionType_Name(name);
	}

	@Override
	public void deleteSubscriptionById(Long id) {
		this.subRepo.deleteById(id);
	}

	@Override
	public Subscription saveSubscription(Subscription subscription) {
		return this.subRepo.save(subscription);
	}

	@Override
	public Subscription updateSubscription(Long id, Subscription subscription) {
		Optional<Subscription> optSub = this.subRepo.findById(id);
		if (optSub.isPresent()) {
			Subscription sub = optSub.get();
			sub.setSubscriptionType(subscription.getSubscriptionType());
			sub.setValid(subscription.isValid());
			sub.setValidFrom(subscription.getValidFrom());
			sub.setValidTo(subscription.getValidTo());
			return this.subRepo.save(sub);
		} else {
			return this.subRepo.save(subscription);
		}
	}

}
