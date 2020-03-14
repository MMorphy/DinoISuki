package hr.go2.play.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.go2.play.entities.ApplicationProperties;
import hr.go2.play.entities.User;
import hr.go2.play.repositories.ApplicationPropertiesRepository;
import hr.go2.play.services.ApplicationPropertiesService;

@Service
@Transactional
public class ApplicationPropertiesServiceImpl implements ApplicationPropertiesService {

	@Autowired
	private ApplicationPropertiesRepository applicationPropertiesRepo;

	@Override
	public void saveApplicationProperty(ApplicationProperties applicationProperties) {
		applicationPropertiesRepo.save(applicationProperties);
	}

	@Override
	public Optional<ApplicationProperties> findByKey(String key) {
		return applicationPropertiesRepo.findByKey(key);
	}

	@Override
	public Optional<ApplicationProperties> findByValue(String value) {
		return applicationPropertiesRepo.findByValue(value);
	}

	@Override
	public Optional<ApplicationProperties> findByUser(User user) {
		return applicationPropertiesRepo.findByUserId(user);
	}


}
