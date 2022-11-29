package com.like.system.term.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.like.system.core.jpa.domain.AbstractAuditEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@ToString(callSuper=true, includeFieldNames=true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "COMTERMDOMAIN")
@EntityListeners(AuditingEntityListener.class)
public class DataDomainDictionary extends AbstractAuditEntity {	
	
	@Id	
	@Column(name="DOMAIN_ID")
	String id;
	
	@Column(name="DB")
	@Enumerated(EnumType.STRING)
	Database database; 
	
	@Column(name="DOMAIN_NAME")
	String domainName;
		
	@Column(name="DATA_TYPE")
	String dataType;	
	
	@Column(name="CMT")
	String comment;
	
	@Builder
	public DataDomainDictionary(Database database, String domainName, String dataType, String comment) {
		this.id = database + "_" + domainName;
		this.database = database;
		this.domainName = domainName;
		this.dataType = dataType;
		this.comment = comment;
	}
	
	public void modify(String dataType, String comment) {		
		this.dataType = dataType;
		this.comment = comment;
	}
}
