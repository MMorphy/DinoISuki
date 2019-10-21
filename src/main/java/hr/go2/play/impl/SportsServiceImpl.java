package hr.go2.play.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.go2.play.entities.Sports;
import hr.go2.play.repositories.SportsRepository;
import hr.go2.play.services.SportsService;

@Service
public class SportsServiceImpl implements SportsService {
	
	@Autowired
	private SportsRepository sportsRepo;
	
	public SportsServiceImpl(SportsRepository sportsRepo) {
		this.sportsRepo = sportsRepo;
	}

	@Override
	public List<Sports> findAllSports() {
		return this.sportsRepo.findAll();
	}

	@Override
	public Sports findSportsById(Long id) {
		try {
			return this.sportsRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	@Override
	public Sports findSportsByName(String name) {
		try {
			return this.sportsRepo.findByName(name).get();
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	@Override
	public void deleteSportsById(Long id) {
		this.sportsRepo.deleteById(id);
	}

	@Override
	public void deleteAllSports() {
		this.sportsRepo.deleteAll();
	}

	@Override
	public Sports saveSports(Sports sports) {
		return this.sportsRepo.save(sports);
	}

	@Override
	public Sports updateSports(Long id, Sports sports) {
		Optional<Sports> optSports = this.sportsRepo.findById(id);
		if (optSports.isPresent()) {
			Sports sport = optSports.get();
			sport.setName(sports.getName());
			return this.sportsRepo.save(sport);
		} else {
			return this.sportsRepo.save(sports);
		}
	}

}
