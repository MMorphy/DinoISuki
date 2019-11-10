package hr.go2.play.services;

import java.util.List;

import hr.go2.play.entities.Team;

public interface TeamService {
	
	public abstract List<Team> findAllTeams();
	
	public abstract Team findTeamById(Long id);
	
	public abstract Team findTeamByName(String name);

	public abstract List<Team> findTeamsByUserUsername(String username);
	
	public abstract void deleteTeamById(Long id);
	
	public abstract Team saveTeam(Team team);
	
	public abstract Team updateTeam(Long id, Team team);

	public abstract void deleteAllTeams();
}
