package com.lucky.libManagement.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lucky.libManagement.entity.Book;
import com.lucky.libManagement.entity.CurrentUserSession;
import com.lucky.libManagement.entity.Reservation;
import com.lucky.libManagement.entity.User;
import com.lucky.libManagement.service.BookService;
import com.lucky.libManagement.service.CurrentUserSessionService;
import com.lucky.libManagement.service.ReservationService;
import com.lucky.libManagement.service.UserService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/reservations")
public class ReservationController {

	private final ReservationService reservationService;
    private final UserService userService;
    private final BookService bookService;

    private final CurrentUserSessionService currentUserSessionService;

    public ReservationController(ReservationService reservationService, UserService userService, BookService bookService, CurrentUserSessionService currentUserSessionService) {
        this.reservationService = reservationService;
        this.userService = userService;
        this.bookService = bookService;
        this.currentUserSessionService = currentUserSessionService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reservation>> getReservationsByUser(@PathVariable Long userId, @RequestParam(required = false) String privateKey) {
        if (validatePrivateKey(privateKey, "librarian", "user")) {
            Optional<User> userOptional = userService.getUserById(userId);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                List<Reservation> reservations = reservationService.getReservationsByUser(user);
                return ResponseEntity.ok(reservations);
            }
        }
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<Reservation>> getReservationsByBook(@PathVariable Long bookId, @RequestParam(required = false) String privateKey) {
        if (validatePrivateKey(privateKey, "librarian", "user")) {
            Optional<Book> bookOptional = bookService.getBookById(bookId);

            if (bookOptional.isPresent()) {
                Book book = bookOptional.get();
                List<Reservation> reservations = reservationService.getReservationsByBook(book);
                return ResponseEntity.ok(reservations);
            }
        }
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/reserve")
    public ResponseEntity<String> reserveBook(
            @RequestParam Long userId,
            @RequestParam Long bookId,
            @RequestParam(required = false) String privateKey){

        if (validatePrivateKey(privateKey, "user")) {
            LocalDate reservationDate = LocalDate.now();

            Optional<User> userOptional = userService.getUserById(userId);
            Optional<Book> bookOptional = bookService.getBookById(bookId);

            if (userOptional.isPresent() && bookOptional.isPresent()) {
                Book book = bookOptional.get();
                User user = userOptional.get();

                if (book.getAvailableQuantity() < 1) {
                    Reservation reservation = reservationService.reserveBook(user, book, reservationDate);
                    return ResponseEntity.ok("Book reserved successfully. Reservation ID: " + reservation.getReservationId());
                } else {
                    return ResponseEntity.badRequest().body("Book is currently available for borrowing.");
                }
            }
        }
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<String> cancelReservation(@PathVariable Long reservationId, @RequestParam(required = false) String privateKey) {
        if (validatePrivateKey(privateKey, "librarian", "user")) {
            Optional<Reservation> reservationOptional = reservationService.getReservationById(reservationId);

            if (reservationOptional.isPresent()) {
                Reservation reservation = reservationOptional.get();
                reservationService.cancelReservation(reservation);
                return ResponseEntity.ok("Reservation canceled successfully.");
            }
        }
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    
    public boolean validatePrivateKey(String privateKey, String... allowedRoles) {
        if (privateKey == null) {
            return false;
        }
        CurrentUserSession currentUserSession = currentUserSessionService.findByPrivateKey(privateKey);
        if (currentUserSession == null) {
            return false;
        }
        String userRole = currentUserSession.getRole();
        for (String allowedRole : allowedRoles) {
            if (userRole.equals(allowedRole)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean validatePrivateKeyNoRole(String PrivateKey) {
    	if (PrivateKey == null) {
    		return false; // Or handle the null case according to your requirements
        }
        CurrentUserSession currentUserSession = currentUserSessionService.findByPrivateKey(PrivateKey);
        boolean isOk = false;
        if(currentUserSession != null && currentUserSession.getPrivateKey().equals(PrivateKey)) {
        	isOk = true;
        }
        return isOk;
   }

}
