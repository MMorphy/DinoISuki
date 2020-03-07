package hr.go2.play.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import hr.go2.play.DTO.VideoDetailsDTO;
import hr.go2.play.entities.ContactInformation;
import hr.go2.play.entities.Location;
import hr.go2.play.entities.WorkingHours;

public interface LocationService {

	public abstract List<Location> findAllLocations();

	public abstract Location findLocationById(Long id);

	public abstract Location findLocationByAddress(String address);

	public abstract Location findLocationByAddressLike(String address);

	public abstract Location findLocationByContactInfo(ContactInformation info);

	public abstract Location findLocationByEmail (String email);

	public abstract Location findLocationByTelNumber (String number);

	public abstract Location findLocationByName(String name);

	public abstract List<Location> findLocationByWorkingHours(WorkingHours wh);

	public abstract void deleteLocationById(Long id);

	public abstract Location saveLocation(Location location);

	public abstract Location updateLocation(Long id, Location location);

	public abstract void deleteAllLocations();

	public abstract List<VideoDetailsDTO> findAllVideoDetails();

	public abstract Optional<Location> findLocationByCameraName(String cameraName);

	public abstract Collection<WorkingHours> findWorkingHoursByLocationId(Long locationId);

}
