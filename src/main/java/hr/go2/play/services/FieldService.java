package hr.go2.play.services;

import java.util.List;

import hr.go2.play.entities.Field;

public interface FieldService {
	
	public abstract List<Field> findAllFields();
	
	public abstract Field findFieldById(Long id);
	
	public abstract List<Field> findFieldBySportName(String name);
	
	public abstract Field findFieldByCamerasName(String name);
	
	public abstract List<Field> findFieldByTermsAvail(boolean availability);
	
	public abstract void deleteFieldById(Long id);
	
	public abstract Field saveField(Field field);
	
	public abstract Field updateField(Long id, Field field);

}
