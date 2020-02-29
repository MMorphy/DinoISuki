package hr.go2.play.services;

import java.util.List;

import hr.go2.play.entities.DayType;

public interface DayTypeService {

	public abstract List<DayType> findAllDayTypes();

	public abstract DayType findDayTypeById(Long id);

	public abstract DayType findDayTypeByType(String type);

	public abstract void deleteDayTypeById(Long id);

	public abstract DayType updateDayType(Long id, DayType newDT);

	public abstract DayType saveDayType(DayType newDT);

	public void deleteAllDayTypes();
}
