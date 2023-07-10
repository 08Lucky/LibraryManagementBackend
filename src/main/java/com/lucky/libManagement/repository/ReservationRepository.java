package com.lucky.libManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucky.libManagement.entity.Book;
import com.lucky.libManagement.entity.Reservation;
import com.lucky.libManagement.entity.User;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUser(User user);
    List<Reservation> findByBook(Book book);
}