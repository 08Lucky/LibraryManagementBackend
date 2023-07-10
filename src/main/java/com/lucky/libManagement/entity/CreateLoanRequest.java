package com.lucky.libManagement.entity;

public class CreateLoanRequest {

	private Long userId;
    private Long bookId;

	public CreateLoanRequest(Long userId, Long bookId) {
		super();
		this.userId = userId;
		this.bookId = bookId;
	}

	public CreateLoanRequest(Long userId) {
		super();
		this.userId = userId;
	}

	public CreateLoanRequest() {
		super();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	@Override
	public String toString() {
		return "CreateLoanRequest [userId=" + userId + ", bookId=" + bookId + "]";
	}


}
