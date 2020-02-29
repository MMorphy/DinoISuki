package hr.go2.play.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.go2.play.entities.ContactInformation;
import hr.go2.play.repositories.ContactInformationRepository;
import hr.go2.play.services.ContactInformationService;

@Service
public class ContactInformationServiceImpl implements ContactInformationService{
	
	@Autowired
	private ContactInformationRepository contactInformationRepo;
	
	public ContactInformationServiceImpl(ContactInformationRepository contactInformationRepo) {
		this.contactInformationRepo = contactInformationRepo;
	}

	@Override
	public List<ContactInformation> findAllContactInformations() {
		return this.contactInformationRepo.findAll();
	}

	@Override
	public ContactInformation findContactInformationById(Long id) {
		try {
			return this.contactInformationRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	@Override
	public ContactInformation findContactInformationByEmail(String email) {
		try {
			return this.contactInformationRepo.findByEmail(email).get();
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	@Override
	public List<ContactInformation> findContactInformationByEmailLike(String email) {
		return (List<ContactInformation>) this.contactInformationRepo.findByEmailLike(email);
	}

	@Override
	public ContactInformation findContactInformationByTelNumber(String telNumber) {
		try {
			return this.contactInformationRepo.findByTelephoneNumber(telNumber).get();
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	@Override
	public List<ContactInformation> findContactInformationByTelNumberLike(String telNumber) {
		return (List<ContactInformation>) this.contactInformationRepo.findByTelephoneNumberLike(telNumber);
	}

	@Override
	public void deleteContactInformationById(Long id) {
		this.contactInformationRepo.deleteById(id);
	}

	@Override
	public void deleteAllContactInformations() {
		this.contactInformationRepo.deleteAll();
	}

	@Override
	public ContactInformation saveContactInformation(ContactInformation contactInformation) {
		return this.contactInformationRepo.save(contactInformation);
	}

	@Override
	public ContactInformation updateContactInformation(Long id, ContactInformation contactInformation) {
		Optional<ContactInformation> optContInfo = this.contactInformationRepo.findById(id);
		if(optContInfo.isPresent()) {
			ContactInformation contInfo = optContInfo.get();
			contInfo.setEmail(contactInformation.getEmail());
			contInfo.setTelephoneNumber(contactInformation.getTelephoneNumber());
			return this.contactInformationRepo.save(contInfo);
		} else {
			return this.contactInformationRepo.save(contactInformation);
		}
	}

	@Override
	public Boolean existsByEmail(String email) {
		return this.contactInformationRepo.existsByEmail(email);
	}

}
