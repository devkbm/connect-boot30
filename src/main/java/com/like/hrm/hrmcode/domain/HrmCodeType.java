package com.like.hrm.hrmcode.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.like.system.core.jpa.domain.AbstractAuditEntity;

//import com.like.system.core.jpa.domain.AbstractAuditEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "HRMCODETYPE")
public class HrmCodeType extends AbstractAuditEntity {
		
	@Id
	@Column(name="TYPE_ID")
	String id;
	
	@Column(name="TYPE_NM")
	String name;	
	
	@Column(name="PRT_SEQ")
	Integer sequence;
					
	@Column(name="CMT")
	String comment;		
	
	public HrmCodeType(String id
					  ,String name				
					  ,Integer sequence				  				 
					  ,String comment) {				
		this.id = id;
		this.name = name;		
		this.sequence = sequence;		
		this.comment = comment;
	}
	
	public void modify(String name					  
					  ,Integer sequence					  
					  ,String comment ) {		
		this.name = name;		
		this.sequence = sequence;		
		this.comment = comment;
	}	
	
}
