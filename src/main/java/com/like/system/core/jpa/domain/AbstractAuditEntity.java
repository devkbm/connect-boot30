package com.like.system.core.jpa.domain;

import org.hibernate.annotations.CompositeType;
import org.hibernate.annotations.CompositeTypeRegistration;
//import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * @see https://stackoverflow.com/questions/61813715/spring-boot-auditing-hostname-and-hostip
 *
 */
@Getter(value = AccessLevel.PUBLIC)
/*
@TypeDef(name = "AuditorDetails",
    typeClass = AuditorDetailsType.class,
    defaultForType = AuditorDetails.class)
*/
@CompositeTypeRegistration( embeddableClass = AuditorDetails.class, userType = AuditorDetailsType.class )
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditEntity {

    @CreatedDate    
    @Column(name = "CREATED_DT", updatable = false)
    LocalDateTime createdDt;

    @CreatedBy
    @AttributeOverrides({
        @AttributeOverride(name = "loggedUser", column = @Column(name = "CREATED_USER_ID")),
        @AttributeOverride(name = "hostIp", column = @Column(name = "CREATED_HOST_IP"))
    })    
    @CompositeType(AuditorDetailsType.class)
    AuditorDetails createdBy;

    @Column(name = "CREATED_APP_URL", updatable = false)
    protected String createdAppUrl;
    
    @LastModifiedDate
    @Column(name = "MODIFIED_DT")    
    LocalDateTime modifiedDt;

    @LastModifiedBy    
    @AttributeOverrides({
        @AttributeOverride(name = "loggedUser", column = @Column(name = "MODIFIED_USER_ID")),
        @AttributeOverride(name = "hostIp", column = @Column(name = "MODIFIED_HOST_IP"))
    })       
    @CompositeType(AuditorDetailsType.class)
    AuditorDetails modifiedBy;   

    @Column(name = "MODIFIED_APP_URL")
    protected String modifiedAppUrl;       
    
    protected AbstractAuditEntity() {}
        
    @PrePersist
    protected void prePersist() {    	
    	this.modifiedAppUrl = this.createdAppUrl;    	
    }
 
    @PreUpdate
    protected void preUpdate() {
    	this.modifiedAppUrl = this.createdAppUrl;    	
    }
    
    public void setAppUrl(String appUrl) {
    	this.createdAppUrl = appUrl;
    }

	@Override
	public String toString() {
		
		String createdByLoggedUser = this.createdBy != null ? createdBy.getLoggedUser() : null;
		String createdByHospIp = this.createdBy != null ? createdBy.getHostIp() : null;
		String modifiedByLoggedUser = this.modifiedBy != null ? modifiedBy.getLoggedUser() : null;
		String modifiedByHospIp = this.modifiedBy != null ? modifiedBy.getHostIp() : null;
		
		return "AbstractAuditEntity [createdDt=" + createdDt + ", createdByLoggedUser=" + createdByLoggedUser 
				+ ", createdByHospIp=" + createdByHospIp 	+ ", createdAppUrl=" + createdAppUrl 
				+ ", modifiedDt=" + modifiedDt + ", modifiedByLoggedUser=" + modifiedByLoggedUser 
				+ ", modifiedByHospIp=" + modifiedByHospIp + ", modifiedAppUrl=" + modifiedAppUrl + "]";
	}
    
}
