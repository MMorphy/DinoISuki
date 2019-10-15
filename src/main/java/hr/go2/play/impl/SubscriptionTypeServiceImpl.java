package hr.go2.play.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import hr.go2.play.entities.SubscriptionType;
import hr.go2.play.repositories.SubscriptionTypeRepository;
import hr.go2.play.services.SubscriptionTypeService;

public class SubscriptionTypeServiceImpl implements SubscriptionTypeService {
	
	@Autowired
	private SubscriptionTypeRepository subTypeRepo;
	
	public SubscriptionTypeServiceImpl(SubscriptionTypeRepository subTypeRepo) {
		this.subTypeRepo = subTypeRepo;
	}

	@Override
	public List<SubscriptionType> findAllSubscriptionTypes() {
		return this.subTypeRepo.findAll();
	}

	@Override
	public SubscriptionType findSubscriptionTypeById(Long id) {
		try { 
			return this.subTypeRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	@Override
	public List<SubscriptionType> findSubscriptionTypeByName(String name) {
		return (List<SubscriptionType>) this.subTypeRepo.findByName(name);
	}

	@Override
	public void deleteSubscriptionTypeById(Long id) {
		this.subTypeRepo.deleteById(id);
	}

	@Override
	public SubscriptionType saveSubscriptionType(SubscriptionType subType) {
		return this.subTypeRepo.save(subType);
	}

	@Override
	public SubscriptionType updateSubscriptionType(Long id, SubscriptionType subType) {
		Optional<SubscriptionType> optSubType = this.subTypeRepo.findById(id);
		if (optSubType.isPresent()) {
			SubscriptionType sub = optSubType.get();
			sub.setName(subType.getName());
			return this.subTypeRepo.save(sub);
		} else {
			return this.subTypeRepo.save(subType);
		}
	}

}
