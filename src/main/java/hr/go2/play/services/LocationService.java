package hr.go2.play.services;

import java.util.List;

import hr.go2.play.entities.Location;
import hr.go2.play.entities.User;
import hr.go2.play.entities.WorkingHours;

public interface LocationService {
	
	public abstract List<Location> findAllLocations();
	
	public abstract Location findLocationById(Long id);
	
	public abstract Location findLocationByAddress(String address);
	
	public abstract Location findLocationByAddressLike(String address);
	
	public abstract Location findLocationByContactUser(User contactUser);
	
	public abstract List<Location> findLocationByWorkingHours(WorkingHours wh);
	
	public abstract Location findLocationByFieldsSportName(String name);
	
	public abstract void deleteLocationById(Long id);
	
	public abstract Location saveLocation(Location location);
	
	public abstract Location updateLocation(Long id, Location location);

}
