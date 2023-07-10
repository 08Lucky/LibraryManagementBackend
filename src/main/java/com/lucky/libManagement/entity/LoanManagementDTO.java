package com.lucky.libManagement.entity;

import java.time.LocalDate;

public class LoanManagementDTO {

	private Long loanId;
    private LocalDate dueDate;
	public Long getLoanId() {
		return loanId;
	}
	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}
	public LocalDate getDueDate() {
		return dueDate;
	}
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	public LoanManagementDTO(Long loanId, LocalDate dueDate) {
		super();
		this.loanId = loanId;
		this.dueDate = dueDate;
	}
	public LoanManagementDTO(Long loanId) {
		super();
		this.loanId = loanId;
	}
	public LoanManagementDTO() {
		super();
	}
	@Override
	public String toString() {
		return "LoanManagementDTO [loanId=" + loanId + ", dueDate=" + dueDate + "]";
	}


}
