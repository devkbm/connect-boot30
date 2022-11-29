package com.like.system.holiday.domain;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class HolidayId implements Serializable {
	
	private static final long serialVersionUID = 7009267129126381191L;

	@Column(name="ORG_CD")
	String organizationCode;
	
	@Column(name="HOLIDAY_DT")
	private LocalDate date;
}
