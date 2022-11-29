package com.like.system.file.domain;

import java.io.File;
import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.like.system.core.jpa.domain.AbstractAuditEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@JsonAutoDetect
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "comfileinfo")
@EntityListeners(AuditingEntityListener.class)
public class FileInfo extends AbstractAuditEntity implements Serializable {
			
	private static final long serialVersionUID = 4108977246168878308L;

	@Id	
	@Column(name="FILE_ID", columnDefinition = "BINARY(16)")
	UUID id;	

	@Column(name="APP_URL")
	String appUrl;
		
	@Column(name="USER_ID")
	String userId;
	
	@Column(name="CONTENT_TYPE")
	String contentType;
		
	@Column(name="UUID")
	String uuid;
	
	@Column(name="FILE_PATH")
	String path;
	
	@Column(name="FILE_NM")
	String fileName;
	
	@Column(name="FILE_SIZE")
	long size;
	
	@Column(name="DOWNLOAD_CNT")
	long downloadCount;			
		
	@Builder
	public FileInfo(String appUrl, String userId, String contentType, String uuid, String path, String fileName, long size) {		
		this.id = FileIdGenerator.generateSequencialUUID();
		this.appUrl = appUrl;
		this.userId = userId;
		this.contentType = contentType;
		this.uuid = uuid;
		this.path = path;
		this.fileName = fileName;
		this.size = size;
	}
		
	public void plusDownloadCount() {
		this.downloadCount = this.downloadCount + 1;
	}
	
	public File getFile() {
		return new File(this.path, this.uuid);
	}

}
