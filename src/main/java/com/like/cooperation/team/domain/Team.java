package com.like.cooperation.team.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import org.hibernate.annotations.Comment;

import com.like.system.core.jpa.domain.AbstractAuditEntity;
import com.like.system.user.domain.SystemUser;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Entity
@Table(name = "GRWTEAM")
public class Team extends AbstractAuditEntity {
	
	@Id	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="TEAM_ID")
	Long teamId;
	
	@Comment("팀명")
	@Column(name="TEAM_NAME")
	String teamName;
	
	/*@OneToOne
	@JoinColumn(name="USER_ID")
	private User manager;*/ 
		
	@OneToMany(mappedBy="team", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	List<TeamMember> members = new ArrayList<TeamMember>();			
	
	public Team(String teamName) {
		this.teamName = teamName;		
	}	
	
	public Team(String teamName, List<SystemUser> userList) {
		this.teamName = teamName;
		this.addMembers(userList);
	}
	
	public void modify(String teamName) {
		this.teamName = teamName;
	}					
	
	public void addMember(SystemUser user) {
		this.members.add(new TeamMember(this, user));
	}
	
	public void addMemberList(List<TeamMember> memberList) {
		this.members.addAll(memberList);
	}
	
	public void addMembers(List<SystemUser> userList) {		
		for (SystemUser user : userList) {
			if (!isMember(user)) {				
				this.addMember(user);
			}
		}
	}
	
	public void updateMembers(List<SystemUser> userList) {
		if (userList == null || userList.isEmpty()) {
			this.members.clear();
		} else {
			// userList에 없는 맴버는 삭제
			this.members.removeIf(e -> userList.stream()
											   .map(r -> r.getId())
											   .anyMatch(r -> !r.equals(e.getUserId())));
					
			this.addMembers(userList);
		}				
	}
	
	private boolean isMember(SystemUser user) {					
		return this.members.stream()
						   .map(r -> r.getId().getUserId())					
						   .anyMatch(e -> e.equals(user.getId()));
	}
		
}
