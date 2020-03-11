package hr.go2.play.impl;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hr.go2.play.entities.Subscription;
import hr.go2.play.entities.SubscriptionStatistics;
import hr.go2.play.repositories.SubscriptionRepository;
import hr.go2.play.services.SubscriptionService;

@Service
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
	public void deleteAllSubscriptions() {
		this.subRepo.deleteAll();
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

	@Override
	@Transactional
	public void updateValidityByTime(Date date) {
		subRepo.updateValidityByTime(date);
	}

	@Override
	public int numberOfInvalidatingSubscriptions(Date date) {
		return subRepo.numberOfInvalidatingSubscriptions(date);
	}

	@Override
	public List<SubscriptionStatistics> getSubscriptionStatistics() {
		return (List<SubscriptionStatistics>) subRepo.getSubscriptionStatistics();
	}

}
