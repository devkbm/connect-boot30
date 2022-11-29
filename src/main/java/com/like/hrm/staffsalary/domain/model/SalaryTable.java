package com.like.hrm.staffsalary.domain.model;

import java.time.LocalDate;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.Comment;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "HRMSALARYTABLE")
public class SalaryTable {
	
	@Id		
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Comment("식별자")
	@Column(name="ID", nullable = false)
	Long id;
	
	@Comment("기준일")
	@Column(name="BASE_DT")
	LocalDate baseDate;
		
	@Embedded
	SalaryAppointmentInformation appointmentInfo;
			
	@Comment("금액")
	@Column(name="AMOUNT")
	BigDecimal amount;
		
	public SalaryTable(LocalDate baseDate
					  ,SalaryAppointmentInformation appointmentInfo
					  ,BigDecimal amount) {
		this.baseDate = baseDate;
		this.appointmentInfo = appointmentInfo;		
		this.amount = amount;		
	}
		
}
