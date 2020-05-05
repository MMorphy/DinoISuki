package hr.go2.play.services;

import java.util.Date;
import java.util.List;

import hr.go2.play.entities.DayType;
import hr.go2.play.entities.WorkingHours;

public interface WorkingHoursService {

	public abstract List<WorkingHours> findAllWorkingHours();

	public abstract WorkingHours findWorkingHoursById(Long id);

	public abstract List<WorkingHours> findWorkingHoursByFromTime(Date fromTime);

	public abstract List<WorkingHours> findWorkingHoursByToTime(Date toTime);

	public abstract List<WorkingHours> findWorkingHoursByFromTimeAndToTime(Date fromTime, Date toTime);

	public abstract List<WorkingHours> findWorkingHoursByDayType(DayType type);

	public abstract List<WorkingHours> findWorkingHoursByDayTypeName(String type);

	public abstract void deleteWorkingHoursById(Long id);

	public abstract WorkingHours saveWorkingHours(WorkingHours wh);

	public abstract WorkingHours updateWorkingHours(Long id, WorkingHours wh);

	public abstract void deleteAllWorkingHours();

	public abstract List<WorkingHours> findByFromTimeAndToTimeAndDayType(Date fromTime, Date toTime, DayType dayType);

}
