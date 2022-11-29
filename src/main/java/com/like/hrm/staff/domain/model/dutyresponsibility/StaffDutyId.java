package com.like.hrm.staff.domain.model.dutyresponsibility;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import org.hibernate.annotations.Comment;

import com.like.hrm.staff.domain.model.Staff;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class StaffDutyId implements Serializable {
	
	private static final long serialVersionUID = -1176225352272330423L;
	
	@Comment("직원ID")
	@Column(name="STAFF_ID")
	String staff;
		
	@Comment("등록순번")
	@Column(name="SEQ")
	Long seq;
	
	public StaffDutyId(Staff staff, Long seq) {
		this.staff = staff.getId();
		this.seq = seq;
	}
}
