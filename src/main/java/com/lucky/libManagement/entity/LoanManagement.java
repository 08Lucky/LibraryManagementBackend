package com.lucky.libManagement.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "LoanManagement")
public class LoanManagement {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id")
    private Long loanId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "fine")
    private double fine;

    @Column(name = "status")
    private String status;

	public LoanManagement(Long loanId, User user, Book book, LocalDate dueDate, double fine, String status) {
		super();
		this.loanId = loanId;
		this.user = user;
		this.book = book;
		this.dueDate = dueDate;
		this.fine = fine;
		this.status = status;
	}

	public LoanManagement(Long loanId, User user, Book book, LocalDate dueDate, double fine) {
		super();
		this.loanId = loanId;
		this.user = user;
		this.book = book;
		this.dueDate = dueDate;
		this.fine = fine;
	}

	public LoanManagement(Long loanId, User user, Book book, LocalDate dueDate) {
		super();
		this.loanId = loanId;
		this.user = user;
		this.book = book;
		this.dueDate = dueDate;
	}

	public LoanManagement(Long loanId, User user, Book book) {
		super();
		this.loanId = loanId;
		this.user = user;
		this.book = book;
	}

	public LoanManagement(Long loanId, User user) {
		super();
		this.loanId = loanId;
		this.user = user;
	}

	public LoanManagement(Long loanId) {
		super();
		this.loanId = loanId;
	}

	public LoanManagement() {
		super();
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public double getFine() {
		return fine;
	}

	public void setFine(double fine) {
		this.fine = fine;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "LoanManagement [loanId=" + loanId + ", user=" + user + ", book=" + book + ", dueDate=" + dueDate
				+ ", fine=" + fine + ", status=" + status + "]";
	}

}
