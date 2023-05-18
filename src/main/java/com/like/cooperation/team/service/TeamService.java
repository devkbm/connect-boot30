package com.like.cooperation.team.service;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.like.cooperation.team.boundary.TeamDTO;
import com.like.cooperation.team.domain.Team;
import com.like.cooperation.team.domain.TeamRepository;
import com.like.system.user.domain.SystemUser;
import com.like.system.user.service.SystemUserSearchService;

@Service
@Transactional
public class TeamService {
	
	private TeamRepository teamRepository;	
	private SystemUserSearchService userService;	
	
	public TeamService(TeamRepository teamRepository					  
					  ,SystemUserSearchService userService) {
		this.teamRepository = teamRepository;		
		this.userService = userService;		
	}
	
	public Team getTeam(Long teamId) {
		return teamRepository.findById(teamId).orElse(null);
	}
		
	public void saveTeam(TeamDTO.Form dto) {		
		Team entity = dto.teamId() == null ? null : teamRepository.findById(dto.teamId()).orElse(null);
		
		if (entity == null) {
			entity = dto.newEntity(userService);
		} else {
			dto.modify(entity, userService);
		}
		
		teamRepository.save(entity);
	}
	
	public void deleteTeam(Long teamId) {
		teamRepository.deleteById(teamId);
	}
	
	/**
	 * 팀에 가입한다.
	 * @param teamId 팀 엔티티 Id
	 * @param userId 유저 엔티티 Id
	 * @return 
	 */
	public void joinTeam(Long teamId, String userId) {
		Team team = teamRepository.findById(teamId).orElse(null);
		SystemUser member = userService.findUser(userId);			
		
		team.addMember(member);			
	}	
}
