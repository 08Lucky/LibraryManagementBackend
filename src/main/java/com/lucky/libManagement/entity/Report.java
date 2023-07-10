package com.lucky.libManagement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Reports")
public class Report {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long reportId;

    @Column(name = "user_activity")
    private String userActivity;

    @Column(name = "book_status")
    private String bookStatus;

    @Column(name = "fines_collected")
    private double finesCollected;

	public Report(Long reportId, String userActivity, String bookStatus, double finesCollected) {
		super();
		this.reportId = reportId;
		this.userActivity = userActivity;
		this.bookStatus = bookStatus;
		this.finesCollected = finesCollected;
	}

	public Report(Long reportId, String userActivity, String bookStatus) {
		super();
		this.reportId = reportId;
		this.userActivity = userActivity;
		this.bookStatus = bookStatus;
	}

	public Report(Long reportId, String userActivity) {
		super();
		this.reportId = reportId;
		this.userActivity = userActivity;
	}

	public Report(Long reportId) {
		super();
		this.reportId = reportId;
	}

	public Report() {
		super();
	}

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
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
		return "Report [reportId=" + reportId + ", userActivity=" + userActivity + ", bookStatus=" + bookStatus
				+ ", finesCollected=" + finesCollected + "]";
	}
}
