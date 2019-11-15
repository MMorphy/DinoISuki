package hr.go2.play.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import hr.go2.play.entities.Notification;
import hr.go2.play.entities.Team;
import hr.go2.play.repositories.TeamRepository;
import hr.go2.play.services.TeamService;

public class TeamServiceImpl implements TeamService {

	@Autowired
	private TeamRepository teamRepo;

	@Override
	public List<Team> findAllTeams() {
		return this.teamRepo.findAll();
	}

	@Override
	public Team findTeamById(Long id) {
		return this.teamRepo.findById(id).get();
	}

	@Override
	public Team findTeamByName(String name) {
		return this.teamRepo.findByName(name).get();
	}

	@Override
	public List<Team> findTeamsByUserUsername(String username) {
		return (List<Team>) this.teamRepo.findByUsers_Username(username);
	}

	@Override
	public void deleteTeamById(Long id) {
		this.teamRepo.deleteById(id);
		
	}

	@Override
	public Team saveTeam(Team team) {
		return this.teamRepo.save(team);
	}

	@Override
	public Team updateTeam(Long id, Team team) {
		Optional<Team> optTeam = this.teamRepo.findById(id);
		if (optTeam.isPresent()) {
			Team t = optTeam.get();
			t.setName(team.getName());
			t.setUsers(team.getUsers());
			return this.teamRepo.save(t);
		} else {
			return this.teamRepo.save(team);
		}
	}

	@Override
	public void deleteAllTeams() {
		this.teamRepo.deleteAll();
		
	}

}
