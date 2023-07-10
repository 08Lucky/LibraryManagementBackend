package com.lucky.libManagement.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "CurrentUserSession")
public class CurrentUserSession {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer currSessionid;
	
	private String email;	
	
	@NotNull(message = "Date time is require")
	private LocalDateTime loginDateTime;
	
	@NotNull(message = "Role is require")
	private String role;

	private String privateKey;
	

	public CurrentUserSession(Integer currSessionid, String email,
			@NotNull(message = "Date time is require") LocalDateTime loginDateTime,
			@NotNull(message = "Role is require") String role) {
		super();
		this.currSessionid = currSessionid;
		this.email = email;
		this.loginDateTime = loginDateTime;
		this.role = role;
	}

	public CurrentUserSession(Integer currSessionid, String email,
			@NotNull(message = "Date time is require") LocalDateTime loginDateTime,
			@NotNull(message = "Role is require") String role, String privateKey) {
		super();
		this.currSessionid = currSessionid;
		this.email = email;
		this.loginDateTime = loginDateTime;
		this.role = role;
		this.privateKey = privateKey;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public Integer getCurrSessionid() {
		return currSessionid;
	}

	public void setCurrSessionid(Integer currSessionid) {
		this.currSessionid = currSessionid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDateTime getLoginDateTime() {
		return loginDateTime;
	}

	public void setLoginDateTime(LocalDateTime loginDateTime) {
		this.loginDateTime = loginDateTime;
	}

	public CurrentUserSession(Integer currSessionid,  String email,
			@NotNull(message = "Date time is require") LocalDateTime loginDateTime) {
		super();
		this.currSessionid = currSessionid;
		this.email = email;
		this.loginDateTime = loginDateTime;
	}

	public CurrentUserSession(Integer currSessionid, String email) {
		super();
		this.currSessionid = currSessionid;
		this.email = email;
	}

	public CurrentUserSession(Integer currSessionid) {
		super();
		this.currSessionid = currSessionid;
	}

	public CurrentUserSession() {
		super();
	}

	@Override
	public String toString() {
		return "CurrentUserSession [currSessionid=" + currSessionid + ", email=" + email + ", loginDateTime="
				+ loginDateTime + "]";
	}
	
	
}
