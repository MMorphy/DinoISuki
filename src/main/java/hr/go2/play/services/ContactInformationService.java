package hr.go2.play.services;

import java.util.List;

import hr.go2.play.entities.ContactInformation;

public interface ContactInformationService {
	
	public abstract List<ContactInformation> findAllContactInformations();
	
	public abstract ContactInformation findContactInformationById(Long id);
	
	public abstract ContactInformation findContactInformationByEmail(String email);
	
	public abstract List<ContactInformation> findContactInformationByEmailLike(String email);
	
	public abstract ContactInformation findContactInformationByTelNumber(String telNumber);
	
	public abstract List<ContactInformation> findContactInformationByTelNumberLike(String telNumber);
	
	public abstract void deleteContactInformationById(Long id);
	
	public abstract ContactInformation saveContactInformation(ContactInformation contactInformation);
	
	public abstract ContactInformation updateContactInformation(Long id, ContactInformation contactInformation);

	public abstract void deleteAllContactInformations();

}
