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
@Table(name = "Reservation")
public class Reservation {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long reservationId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "reservation_date")
    private LocalDate reservationDate;

	public Reservation(Long reservationId, User user, Book book, LocalDate reservationDate) {
		super();
		this.reservationId = reservationId;
		this.user = user;
		this.book = book;
		this.reservationDate = reservationDate;
	}

	public Reservation(Long reservationId, User user, Book book) {
		super();
		this.reservationId = reservationId;
		this.user = user;
		this.book = book;
	}

	public Reservation(Long reservationId, User user) {
		super();
		this.reservationId = reservationId;
		this.user = user;
	}

	public Reservation(Long reservationId) {
		super();
		this.reservationId = reservationId;
	}

	public Reservation() {
		super();
	}

	public Long getReservationId() {
		return reservationId;
	}

	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
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

	public void setBook(Book book) {
		this.book = book;
	}

	public LocalDate getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(LocalDate reservationDate) {
		this.reservationDate = reservationDate;
	}

	@Override
	public String toString() {
		return "Reservation [reservationId=" + reservationId + ", user=" + user + ", book=" + book
				+ ", reservationDate=" + reservationDate + "]";
	}


}
