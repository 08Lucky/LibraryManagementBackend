package com.lucky.libManagement.entity;

public class UserRequest {

	private String email;
    private String password;

    // Getters and Setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public UserRequest(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public UserRequest(String email) {
		super();
		this.email = email;
	}

	public UserRequest() {
		super();
	}
}
