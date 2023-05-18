package com.like.cooperation.team.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Embeddable
public class TeamMemberId implements Serializable {		
	
	private static final long serialVersionUID = 438709457020029955L;

	//@Column(name="TEAM_ID")	
	Long team;
		
	@Column(name="USER_ID")
	String userId;	
	
	public TeamMemberId(Long team, String userId) {
		this.team = team;
		this.userId = userId;
	}
	
}
