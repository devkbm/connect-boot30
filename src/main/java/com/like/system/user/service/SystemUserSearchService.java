package com.like.system.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.like.system.user.domain.SystemUser;
import com.like.system.user.domain.SystemUserRepository;

@Service
@Transactional(readOnly = true)
public class SystemUserSearchService {

	private SystemUserRepository repository;
	
	public SystemUserSearchService(SystemUserRepository repository) {
		this.repository = repository;
	}
	
	public SystemUser findUser(String userId) {
		return this.repository.findById(userId).orElse(null);
	}
	
	public List<SystemUser> findUsers(List<String> userList) {		
		return this.repository.findAllById(userList);		
	}
}
