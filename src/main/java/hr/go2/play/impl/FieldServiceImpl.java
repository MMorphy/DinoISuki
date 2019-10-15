package hr.go2.play.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import hr.go2.play.entities.Field;
import hr.go2.play.repositories.FieldRepository;
import hr.go2.play.services.FieldService;

public class FieldServiceImpl implements FieldService{
	
	@Autowired
	private FieldRepository fieldRepo;
	
	public FieldServiceImpl(FieldRepository fieldRepo) {
		this.fieldRepo = fieldRepo;
	}

	@Override
	public List<Field> findAllFields() {
		return this.fieldRepo.findAll();
	}

	@Override
	public Field findFieldById(Long id) {
		try {
			return this.fieldRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	@Override
	public List<Field> findFieldBySportName(String name) {
		return (List<Field>) this.fieldRepo.findBySport_Name(name);
	}

	@Override
	public Field findFieldByCamerasName(String name) {
		try {
			return this.fieldRepo.findByCameras_Name(name).get();
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	@Override
	public List<Field> findFieldByTermsAvail(boolean availability) {
		return (List<Field>) this.fieldRepo.findByTerms_Available(availability);
	}

	@Override
	public void deleteFieldById(Long id) {
		this.fieldRepo.deleteById(id);
	}

	@Override
	public Field saveField(Field field) {
		return this.fieldRepo.save(field);
	}

	@Override
	public Field updateField(Long id, Field field) {
		Optional<Field> optField = this.fieldRepo.findById(id);
		if (optField.isPresent()) {
			Field fieldNew = optField.get();
			fieldNew.setCameras(field.getCameras());
			fieldNew.setSport(field.getSport());
			fieldNew.setTerms(field.getTerms());
			return this.fieldRepo.save(fieldNew);
		} else {
			return this.fieldRepo.save(field);
		}
	}

}
