package hr.go2.play.impl;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import hr.go2.play.entities.WorkingHours;
import hr.go2.play.repositories.WorkingHoursRepository;
import hr.go2.play.services.WorkingHoursService;

public class WorkingHoursServiceImpl implements WorkingHoursService {
	
	@Autowired
	private WorkingHoursRepository whRepo;
	
	public WorkingHoursServiceImpl(WorkingHoursRepository whRepo) {
		this.whRepo = whRepo;
	}

	@Override
	public List<WorkingHours> findAllWorkingHours() {
		return this.whRepo.findAll();
	}

	@Override
	public WorkingHours findWorkingHoursById(Long id) {
		try {
			return this.whRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	@Override
	public List<WorkingHours> findWorkingHoursByFromTime(Date fromTime) {
		return (List<WorkingHours>) this.whRepo.findByFromTime(fromTime);
	}

	@Override
	public List<WorkingHours> findWorkingHoursByToTime(Date toTime) {
		return (List<WorkingHours>) this.whRepo.findByToTime(toTime);
	}

	@Override
	public List<WorkingHours> findWorkingHoursByFromTimeAndToTime(Date fromTime, Date toTime) {
		return (List<WorkingHours>) this.whRepo.findByFromTimeAndToTime(fromTime, toTime);
	}

	@Override
	public void deleteWorkingHoursById(Long id) {
		this.whRepo.deleteById(id);
	}

	@Override
	public WorkingHours saveWorkingHours(WorkingHours wh) {
		return this.whRepo.save(wh);
	}

	@Override
	public WorkingHours updateWorkingHours(Long id, WorkingHours wh) {
		Optional<WorkingHours> optWh = this.whRepo.findById(id);
		if (optWh.isPresent()) {
			WorkingHours workingHours = optWh.get();
			workingHours.setFromTime(wh.getFromTime());
			workingHours.setToTime(wh.getToTime());
			return this.whRepo.save(workingHours);
		} else {
			return this.whRepo.save(wh);
		}
	}

}