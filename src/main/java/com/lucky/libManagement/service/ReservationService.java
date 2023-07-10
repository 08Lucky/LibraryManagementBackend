package com.lucky.libManagement.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lucky.libManagement.entity.Book;
import com.lucky.libManagement.entity.Reservation;
import com.lucky.libManagement.entity.User;
import com.lucky.libManagement.repository.ReservationRepository;

@Service
public class ReservationService {

	private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getReservationsByUser(User user) {
        return reservationRepository.findByUser(user);
    }

    public List<Reservation> getReservationsByBook(Book book) {
        return reservationRepository.findByBook(book);
    }

    public Optional<Reservation> getReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId);
    }


    public Reservation reserveBook(User user, Book book, LocalDate reservationDate) {
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setBook(book);
        reservation.setReservationDate(reservationDate);

        return reservationRepository.save(reservation);
    }

    public void cancelReservation(Reservation reservation) {
        reservationRepository.delete(reservation);
    }
}
