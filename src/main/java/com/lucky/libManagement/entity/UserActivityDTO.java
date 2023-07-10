package com.lucky.libManagement.entity;

public class UserActivityDTO {

	private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String activityType;

	public UserActivityDTO(String userId, String firstName, String lastName, String email, String activityType
			) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.activityType = activityType;
	}

	public UserActivityDTO(String userId, String firstName, String lastName, String email) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	public UserActivityDTO(String userId, String firstName, String lastName) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	public UserActivityDTO(String userId, String firstName) {
		super();
		this.userId = userId;
		this.firstName = firstName;
	}
	public UserActivityDTO(String userId) {
		super();
		this.userId = userId;
	}
	public UserActivityDTO() {
		super();
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	@Override
	public String toString() {
		return "UserActivityDTO [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", activityType=" + activityType + "]";
	}
    
    
}
