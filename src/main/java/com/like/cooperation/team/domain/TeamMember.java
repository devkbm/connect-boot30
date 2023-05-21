package com.like.cooperation.team.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.like.system.core.jpa.domain.AbstractAuditEntity;
import com.like.system.user.domain.SystemUser;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

//@ToString(exclude = {"team"})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "GRWTEAMUSER")
public class TeamMember extends AbstractAuditEntity {	

	@EmbeddedId
	TeamMemberId id;
			
	@JsonBackReference
	@MapsId("team")
	@ManyToOne
	@JoinColumn(name="TEAM_ID", referencedColumnName = "TEAM_ID")
	private Team team;	
	
	/*
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="USER_ID", insertable = false, updatable = false)
	private SystemUser user;	
	*/
	
	//@Comment("권한")
	String authority;	
	
	public TeamMember(Team team, SystemUser user) {
		this.id = new TeamMemberId(team.getTeamId(), user.getId());
		this.team = team;
		//this.user = user;
	}
		
	public Team getTeam() {
		return this.getTeam();
	}
	
	public String getUserId() {
		return this.id.getUserId();
	}
	
	/*
	public SystemUser getUser() {
		return this.user;
	}
	*/
	
}
