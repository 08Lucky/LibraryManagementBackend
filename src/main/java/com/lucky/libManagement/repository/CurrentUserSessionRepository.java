package com.lucky.libManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucky.libManagement.entity.CurrentUserSession;

public interface CurrentUserSessionRepository extends JpaRepository<CurrentUserSession ,Integer>{

	CurrentUserSession findByEmailAndRole(String email, String role);

	CurrentUserSession findByEmail(String email);

	CurrentUserSession findByRoleAndCurrSessionid(String role, Integer sessionid);
	CurrentUserSession findByRoleAndPrivateKey(String role, String privateKey);

	CurrentUserSession findByPrivateKey(String privateKey);
}
