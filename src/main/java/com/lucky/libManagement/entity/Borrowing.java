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
@Table(name = "Borrowing")
public class Borrowing {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "borrowing_id")
    private Long borrowingId;

    @ManyToOne  // many instances of borrowing can be created for one user
    @JoinColumn(name = "user_id") //indicates that the foreign key column in the table Borrowing
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "borrow_date")
    private LocalDate borrowDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(name = "status")
    private String status;

	public Borrowing(Long borrowingId, User user, Book book, LocalDate borrowDate, LocalDate dueDate,
			LocalDate returnDate, String status) {
		super();
		this.borrowingId = borrowingId;
		this.user = user;
		this.book = book;
		this.borrowDate = borrowDate;
		this.dueDate = dueDate;
		this.returnDate = returnDate;
		this.status = status;
	}

	public Borrowing(Long borrowingId, User user, Book book, LocalDate borrowDate, LocalDate dueDate,
			LocalDate returnDate) {
		super();
		this.borrowingId = borrowingId;
		this.user = user;
		this.book = book;
		this.borrowDate = borrowDate;
		this.dueDate = dueDate;
		this.returnDate = returnDate;
	}

	public Borrowing(Long borrowingId, User user, Book book, LocalDate borrowDate, LocalDate dueDate) {
		super();
		this.borrowingId = borrowingId;
		this.user = user;
		this.book = book;
		this.borrowDate = borrowDate;
		this.dueDate = dueDate;
	}

	public Borrowing(Long borrowingId, User user, Book book, LocalDate borrowDate) {
		super();
		this.borrowingId = borrowingId;
		this.user = user;
		this.book = book;
		this.borrowDate = borrowDate;
	}

	public Borrowing(Long borrowingId, User user, Book book) {
		super();
		this.borrowingId = borrowingId;
		this.user = user;
		this.book = book;
	}

	public Borrowing(Long borrowingId, User user) {
		super();
		this.borrowingId = borrowingId;
		this.user = user;
	}

	public Borrowing(Long borrowingId) {
		super();
		this.borrowingId = borrowingId;
	}

	public Borrowing() {
		super();
	}

	public Long getBorrowingId() {
		return borrowingId;
	}

	public void setBorrowingId(Long borrowingId) {
		this.borrowingId = borrowingId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user2) {
		this.user = user2;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book2) {
		this.book = book2;
	}

	public LocalDate getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(LocalDate borrowDate) {
		this.borrowDate = borrowDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Borrowing [borrowingId=" + borrowingId + ", user=" + user + ", book=" + book + ", borrowDate="
				+ borrowDate + ", dueDate=" + dueDate + ", returnDate=" + returnDate + ", status=" + status + "]";
	}
}
