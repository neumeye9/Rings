package com.krista.rings.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.krista.rings.models.Team;
import com.krista.rings.repositories.TeamRepository;

@Service
public class TeamService {
	
	private TeamRepository teamRepository;
	
	public TeamService(TeamRepository teamRepository) {
		this.teamRepository = teamRepository;
	}
	
	public void createTeam(Team team) {
		teamRepository.save(team);
	}
	
	public List<Team> allTeams(){
		return teamRepository.findAll();
	}
	
	public Team findByName(String name) {
		return teamRepository.findByGuild(name);
	}
	
	public Team findTeamById(Long id) {
		return teamRepository.findOne(id);
	}
	
	public void update(Team team) {
		teamRepository.save(team);
	}
	
	public void destroy(Long id) {
		teamRepository.delete(id);
	}
	
	

}
