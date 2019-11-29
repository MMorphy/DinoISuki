package hr.go2.play.repositories;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.go2.play.entities.ContactInformation;

public interface ContactInformationRepository extends JpaRepository<ContactInformation, Long>{

	public Optional<ContactInformation> findByEmail(String email);

	public Collection<ContactInformation> findByEmailLike (String email);

	public Optional<ContactInformation> findByTelephoneNumber(String telephoneNumber);

	public Collection<ContactInformation> findByTelephoneNumberLike(String telephoneNumber);
	
	public Boolean existsByEmail(String email);
}
