package com.lucky.libManagement.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lucky.libManagement.entity.Book;
import com.lucky.libManagement.entity.Borrowing;
import com.lucky.libManagement.entity.CurrentUserSession;
import com.lucky.libManagement.entity.User;
import com.lucky.libManagement.exception.UserNotFoundException;
import com.lucky.libManagement.service.BookService;
import com.lucky.libManagement.service.BorrowingService;
import com.lucky.libManagement.service.CurrentUserSessionService;
import com.lucky.libManagement.service.UserService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/borrowings")
public class BorrowingController {
	private final BorrowingService borrowingService;
    private final UserService userService;
    private final BookService bookService;
    private final CurrentUserSessionService currentUserSessionService;

    public BorrowingController(BorrowingService borrowingService, UserService userService, BookService bookService, CurrentUserSessionService currentUserSessionService) {
        this.borrowingService = borrowingService;
        this.userService = userService;
        this.bookService = bookService;
        this.currentUserSessionService = currentUserSessionService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Borrowing>> getBorrowingsByUser(@PathVariable Long userId, @RequestParam(required = false) String privateKey) {
        if (validatePrivateKey(privateKey, "user", "librarian")) {
            Optional<User> user = userService.getUserById(userId);
            List<Borrowing> borrowings = borrowingService.getBorrowingsByUser(user);
            return ResponseEntity.ok(borrowings);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<Borrowing>> getBorrowingsByBook(@PathVariable Long bookId, @RequestParam(required = false) String privateKey) {
        if (validatePrivateKey(privateKey, "user", "librarian")) {
            Optional<Book> book = bookService.getBookById(bookId);
            List<Borrowing> borrowings = borrowingService.getBorrowingsByBook(book);
            return ResponseEntity.ok(borrowings);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Borrowing>> getBorrowingsByStatus(@PathVariable String status, @RequestParam(required = false) String privateKey) {
        if (validatePrivateKey(privateKey, "user", "librarian")) {
            List<Borrowing> borrowings = borrowingService.getBorrowingsByStatus(status);
            return ResponseEntity.ok(borrowings);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/borrow")
    public ResponseEntity<String> borrowBook(
            @RequestParam Long userId,
            @RequestParam Long bookId,
            @RequestParam(required = false) String privateKey) {

        if (validatePrivateKey(privateKey, "user")) {
            Optional<User> userOptional = userService.getUserById(userId);
            Optional<Book> bookOptional = bookService.getBookById(bookId);

            // Calculate borrowDate as the current date
            LocalDate borrowDate = LocalDate.now();

            // Calculate dueDate as 7 days from the borrowDate
            LocalDate dueDate = borrowDate.plusDays(7);

            // Check if the book exists
            if (userOptional.isPresent() && bookOptional.isPresent()) {
                User user = userOptional.get();
                Book book = bookOptional.get();

                // Check if the book is available for borrowing
                if (book.getAvailableQuantity() > 0) {
                    Borrowing borrowing = borrowingService.borrowBook(user, book, borrowDate, dueDate);
                    // Update the book quantity in the database
                    bookService.updateBookAvailability(book, book.getAvailableQuantity() - 1);

                    return ResponseEntity.ok("Book borrowed successfully. Borrowing ID: " + borrowing.getBorrowingId());
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book not available for borrowing.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book not found.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @PostMapping("/return/{borrowingId}")
    public ResponseEntity<String> returnBook(
            @PathVariable Long borrowingId,
            @RequestParam(required = false) String privateKey) {

        if (validatePrivateKey(privateKey, "user")) {
            LocalDate returnDate = LocalDate.now();

            Optional<Borrowing> borrowingOptional = borrowingService.getBorrowingsById(borrowingId);

            // Check if the borrowing exists
            if (borrowingOptional.isPresent()) {
                Borrowing borrowing = borrowingOptional.orElse(null);

                // Check if the book is already returned
                if (borrowing.getStatus().equals("Returned")) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book already returned.");
                } else {
                    borrowingService.returnBook(borrowing, returnDate);
                    // Update the book quantity in the database
                    Book book = borrowing.getBook();
                    bookService.updateBookAvailability(book, book.getAvailableQuantity() + 1);

                    return ResponseEntity.ok("Book returned successfully.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Borrowing not found.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
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
