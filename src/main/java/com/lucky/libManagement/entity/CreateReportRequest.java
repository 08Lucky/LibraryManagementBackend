package com.lucky.libManagement.entity;

public class CreateReportRequest {

	private String userActivity;
    private String bookStatus;
    private double finesCollected;
	public CreateReportRequest(String userActivity, String bookStatus, double finesCollected) {
		super();
		this.userActivity = userActivity;
		this.bookStatus = bookStatus;
		this.finesCollected = finesCollected;
	}
	public CreateReportRequest(String userActivity, String bookStatus) {
		super();
		this.userActivity = userActivity;
		this.bookStatus = bookStatus;
	}
	public CreateReportRequest(String userActivity) {
		super();
		this.userActivity = userActivity;
	}

	public CreateReportRequest() {
		super();
	}

	public String getUserActivity() {
		return userActivity;
	}
	public void setUserActivity(String userActivity) {
		this.userActivity = userActivity;
	}
	public String getBookStatus() {
		return bookStatus;
	}
	public void setBookStatus(String bookStatus) {
		this.bookStatus = bookStatus;
	}
	public double getFinesCollected() {
		return finesCollected;
	}
	public void setFinesCollected(double finesCollected) {
		this.finesCollected = finesCollected;
	}
	@Override
	public String toString() {
		return "CreateReportRequest [userActivity=" + userActivity + ", bookStatus=" + bookStatus + ", finesCollected="
				+ finesCollected + "]";
	}


}
