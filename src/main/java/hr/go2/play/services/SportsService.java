package hr.go2.play.services;

import java.util.List;

import hr.go2.play.entities.Sports;

public interface SportsService {
	
	public abstract List<Sports> findAllSports();
	
	public abstract Sports findSportsById(Long id);
	
	public abstract Sports findSportsByName(String name);
	
	public abstract void deleteSportsById(Long id);
	
	public abstract Sports saveSports(Sports sports);
	
	public abstract Sports updateSports(Long id, Sports sports);

	public abstract void deleteAllSports();

}
