package hr.go2.play.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.go2.play.entities.Field;
import hr.go2.play.repositories.FieldRepository;
import hr.go2.play.services.FieldService;

@Service
public class FieldServiceImpl implements FieldService {

	@Autowired
	private FieldRepository fieldRepo;

	public FieldServiceImpl(FieldRepository fieldRepo) {
		this.fieldRepo = fieldRepo;
	}

	@Override
	public boolean existsFieldById(Long id) {
		return fieldRepo.existsById(id);
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
	public Field findFieldByCamerasName(String name) {
		try {
			return this.fieldRepo.findByCameras_Name(name).get();
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	@Override
	public void deleteFieldById(Long id) {
		this.fieldRepo.deleteById(id);
	}

	@Override
	public void deleteAllFields() {
		this.fieldRepo.deleteAll();
		;
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
			return this.fieldRepo.save(fieldNew);
		} else {
			return this.fieldRepo.save(field);
		}
	}
}
