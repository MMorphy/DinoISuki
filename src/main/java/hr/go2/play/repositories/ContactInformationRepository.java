package hr.go2.play.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.go2.play.entities.ContactInformation;

public interface ContactInformationRepository extends JpaRepository<ContactInformation, Long>{

}
