package hr.go2.play.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hr.go2.play.entities.ApplicationProperties;
import hr.go2.play.entities.User;

@Repository
public interface ApplicationPropertiesRepository extends JpaRepository<ApplicationProperties, Long> {

	public Optional<ApplicationProperties> findByKey(String key);

	public Optional<ApplicationProperties> findByValue(String value);

	public Optional<ApplicationProperties> findByUserId(User user);

	public void deleteByKey(String key);

}
