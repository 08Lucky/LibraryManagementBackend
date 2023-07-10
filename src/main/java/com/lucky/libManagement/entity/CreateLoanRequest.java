package com.lucky.libManagement.entity;

public class CreateLoanRequest {

    private Long bookId;

	public CreateLoanRequest( Long bookId) {
		super();
		this.bookId = bookId;
	}

	public CreateLoanRequest() {
		super();
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	@Override
	public String toString() {
		return "CreateLoanRequest [bookId=" + bookId + "]";
	}
}
