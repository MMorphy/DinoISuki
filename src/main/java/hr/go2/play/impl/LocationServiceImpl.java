package hr.go2.play.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.go2.play.entities.Location;
import hr.go2.play.entities.User;
import hr.go2.play.entities.WorkingHours;
import hr.go2.play.repositories.LocationRepository;
import hr.go2.play.services.LocationService;

@Service
public class LocationServiceImpl implements LocationService {
	
	@Autowired
	private LocationRepository locationRepo;
	
	public LocationServiceImpl (LocationRepository locationRepo) {
		this.locationRepo = locationRepo;
	}

	@Override
	public List<Location> findAllLocations() {
		return this.locationRepo.findAll();
	}

	@Override
	public Location findLocationById(Long id) {
		try {
			return this.locationRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	@Override
	public Location findLocationByAddress(String address) {
		try {
			return this.locationRepo.findByAddress(address).get();
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	@Override
	public Location findLocationByAddressLike(String address) {
		try {
			return this.locationRepo.findByAddressLike(address).get();
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	@Override
	public Location findLocationByContactUser(User contactUser) {
		try {
			return this.locationRepo.findByContactUser(contactUser).get();
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	@Override
	public List<Location> findLocationByWorkingHours(WorkingHours wh) {
		return (List<Location>) this.locationRepo.findByHours(wh);
	}

	@Override
	public Location findLocationByFieldsSportName(String name) {
		try {
			return this.locationRepo.findByFields_Sport_Name(name).get();
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	@Override
	public void deleteLocationById(Long id) {
		this.locationRepo.deleteById(id);
	}

	@Override
	public Location saveLocation(Location location) {
		return this.locationRepo.save(location);
	}

	@Override
	public Location updateLocation(Long id, Location location) {
		Optional<Location> optLocation= this.locationRepo.findById(id);
		if (optLocation.isPresent()) {
			Location loc = optLocation.get();
			loc.setAddress(location.getAddress());
			loc.setContactUser(location.getContactUser());
			loc.setFields(location.getFields());
			loc.setHours(location.getHours());
			loc.setName(location.getName());
			return this.locationRepo.save(loc);
		} else {
			return this.locationRepo.save(location);
		}
	}

}
