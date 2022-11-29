package com.like.hrm.staff.domain.model;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.like.hrm.staff.domain.model.appointment.AppointmentRecord;
import com.like.hrm.staff.domain.model.appointment.AppointmentRecordList;
import com.like.hrm.staff.domain.model.dutyresponsibility.StaffDutyList;
import com.like.hrm.staff.domain.model.family.StaffFamilyList;
import com.like.hrm.staff.domain.model.license.StaffLicenseList;
import com.like.hrm.staff.domain.model.schoolcareer.StaffSchoolCareerList;
import com.like.system.core.jpa.domain.AbstractAuditEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Aggregate Root 
 * <p> 직원정보 </p>
 *  
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "HRMSTAFF")
@EntityListeners(AuditingEntityListener.class)
public class Staff extends AbstractAuditEntity implements Serializable {

	private static final long serialVersionUID = -3164713918774455925L;
	
	@Id
	@Comment("직원ID")
	@Column(name="STAFF_ID")
	String id;
	
	@Column(name="ORG_CD")
	String organizationCode;
	
	@Column(name="STAFF_NO")
	String staffNo;
		
	@Embedded
	StaffName name;
				
	@Embedded
	ResidentRegistrationNumber residentRegistrationNumber;
	
	@Embedded
	StaffContact contact;
	
	@Comment("성별")
	@Column(name="GENDER")
	String gender;
				
	@Comment("생일")
	@Column(name="BIRTHDAY")
	LocalDate birthday;	
		
	@Comment("근무상태")
	@Column(name="WORK_STATE_CODE")
	String workStateCode;
		
	@Comment("이미지경로")
	@Column(name="IMG_PATH")
	String imagePath;
		
	@Embedded
	CurrentAppointmentInformation currentAppointment = new CurrentAppointmentInformation();
		
	@Embedded
	AppointmentRecordList appointmentRecordList = new AppointmentRecordList();
		
	@Embedded
	StaffDutyList staffDutyResponsibilityList = new StaffDutyList();
	
	@Embedded
	StaffFamilyList familyList = new StaffFamilyList();
		
	@Embedded
	StaffSchoolCareerList schoolCareerList = new StaffSchoolCareerList();
	
	/**
	 * 자격면허 명단
	 */
	@Embedded
	StaffLicenseList licenseList;			
			
	public Staff(String organizationCode, StaffNoCreateStrategy strategy, StaffName name, String residentRegistrationNumber) {
		this.id 						= organizationCode + "_" + strategy.create();
		this.organizationCode 			= organizationCode;
		this.staffNo					= strategy.create();
		this.name 						= name; 
		this.residentRegistrationNumber = ResidentRegistrationNumber.of(residentRegistrationNumber);
		this.gender 					= this.residentRegistrationNumber.getGender();
		this.birthday 					= this.residentRegistrationNumber.getBirthDay();		
	}	
					
	public void modifyEntity(StaffName name
						    ,LocalDate birthday) {
		this.name 		= name;				
		this.birthday 	= birthday;
	}
					
	public void changeImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public void changeContact(StaffContact contact) {
		this.contact = contact;
	}
	
	public void applyAppointmentRecord(AppointmentRecord record) {		
		if (this.currentAppointment == null) this.currentAppointment = new CurrentAppointmentInformation(record.getInfo()); 		
								
		LocalDate today = LocalDate.now();		
		if (today.isAfter(record.getAppointmentDate())) {		
			this.currentAppointment.apply(record.getInfo());
					
			record.complete();
		}
	}	
		
}