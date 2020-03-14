package hr.go2.play.services;

import java.util.Optional;

import hr.go2.play.entities.ApplicationProperties;
import hr.go2.play.entities.User;

public interface ApplicationPropertiesService {

	public abstract void saveApplicationProperty(ApplicationProperties applicationProperties);

	public abstract Optional<ApplicationProperties> findByKey(String key);

	public abstract Optional<ApplicationProperties> findByValue(String value);

	public abstract Optional<ApplicationProperties> findByUser(User user);
}
