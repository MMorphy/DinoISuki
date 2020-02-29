package hr.go2.play.services;

import java.util.List;

import hr.go2.play.entities.Field;

public interface FieldService {
	
	public abstract List<Field> findAllFields();
	
	public abstract Field findFieldById(Long id);
	
	public abstract Field findFieldByCamerasName(String name);
	
	public abstract void deleteFieldById(Long id);
	
	public abstract Field saveField(Field field);
	
	public abstract Field updateField(Long id, Field field);

	public abstract void deleteAllFields();

	public abstract boolean existsFieldById(Long id);

}
