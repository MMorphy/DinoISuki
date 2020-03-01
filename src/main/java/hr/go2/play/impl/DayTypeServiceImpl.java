package hr.go2.play.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.go2.play.entities.DayType;
import hr.go2.play.repositories.DayTypeRepository;
import hr.go2.play.services.DayTypeService;

@Service
public class DayTypeServiceImpl implements DayTypeService {

	@Autowired
	private DayTypeRepository DTRepo;

	@Override
	public List<DayType> findAllDayTypes() {
		return this.DTRepo.findAll();
	}

	@Override
	public DayType findDayTypeById(Long id) {
		try {
			return this.DTRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	@Override
	public DayType findDayTypeByType(String type) {
		try {
			return this.DTRepo.findByType(type).get();
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	@Override
	public void deleteDayTypeById(Long id) {
		DTRepo.deleteById(id);
	}

	@Override
	public DayType updateDayType(Long id, DayType newDT) {
		Optional<DayType> optDT = this.DTRepo.findById(id);
		if (optDT.isPresent()) {
			DayType dt = optDT.get();
			dt.setType(newDT.getType());
			return this.DTRepo.save(dt);
		} else {
			return this.DTRepo.save(newDT);
		}
	}

	@Override
	public DayType saveDayType(DayType newDT) {
		return this.DTRepo.save(newDT);
	}

	@Override
	public void deleteAllDayTypes() {
		this.DTRepo.deleteAll();
	}

}
