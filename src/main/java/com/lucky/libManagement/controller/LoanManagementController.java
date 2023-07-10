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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lucky.libManagement.entity.Book;
import com.lucky.libManagement.entity.Borrowing;
import com.lucky.libManagement.entity.CreateLoanRequest;
import com.lucky.libManagement.entity.CurrentUserSession;
import com.lucky.libManagement.entity.LoanManagement;
import com.lucky.libManagement.entity.LoanManagementDTO;
import com.lucky.libManagement.entity.User;
import com.lucky.libManagement.repository.BookRepository;
import com.lucky.libManagement.repository.BorrowingRepository;
import com.lucky.libManagement.repository.LoanManagementRepository;
import com.lucky.libManagement.repository.UserRepository;
import com.lucky.libManagement.service.BookService;
import com.lucky.libManagement.service.BorrowingService;
import com.lucky.libManagement.service.CurrentUserSessionService;
import com.lucky.libManagement.service.LoanManagementService;
import com.lucky.libManagement.service.UserService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/loan-management")
public class LoanManagementController {

	private final LoanManagementService loanManagementService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final BorrowingRepository borrowingRepository;
    private final BorrowingService borrowingService;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private final LoanManagementRepository loanManagementRepository;
    private final CurrentUserSessionService currentUserSessionService;

    public LoanManagementController(LoanManagementService loanManagementService, UserRepository userRepository, UserService userService, BorrowingRepository borrowingRepository, BorrowingService borrowingService, BookRepository bookRepository, BookService bookService, LoanManagementRepository loanManagementRepository, CurrentUserSessionService currentUserSessionService) {
        this.loanManagementService = loanManagementService;
        this.userRepository = userRepository;
        this.userService = userService;
        this.borrowingRepository = borrowingRepository;
        this.borrowingService = borrowingService;
        this.bookRepository = bookRepository;
        this.bookService = bookService;
        this.loanManagementRepository = loanManagementRepository;
        this.currentUserSessionService = currentUserSessionService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LoanManagement>> getLoansByUser(@PathVariable Long userId, @RequestParam(required = false) String privateKey) {
        if (validatePrivateKey(privateKey, "librarian", "user")) {
            try {
                User user = userRepository.findById(userId).orElse(null);
                if (user != null) {
                    List<LoanManagement> loans = loanManagementService.getLoansByUser(user);
                    return ResponseEntity.ok(loans);
                } else {
                    return ResponseEntity.notFound().build();
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<Book> getLoansByBook(@PathVariable Long bookId, @RequestParam(required = false) String privateKey) {
        if (validatePrivateKey(privateKey, "librarian", "user")) {
            try {
                Optional<Book> optionalBook = bookRepository.findById(bookId);
                if (optionalBook.isPresent()) {
                    Book book = optionalBook.get();
                    return ResponseEntity.ok(book);
                } else {
                    return ResponseEntity.notFound().build();
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<LoanManagement>> getLoansByStatus(@PathVariable String status, @RequestParam(required = false) String privateKey) {
        if (validatePrivateKey(privateKey, "librarian", "user")) {
            try {
                List<LoanManagement> loans = loanManagementService.getLoansByStatus(status);
                return ResponseEntity.ok(loans);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createLoan(@RequestBody CreateLoanRequest request, @RequestParam(required = false) String privateKey) {
        if (validatePrivateKey(privateKey, "user")) {
            String email = getEmailFromToken(privateKey);
            Optional<User> userOptional = userService.getUserByEmail(email);
            Optional<Book> bookOptional = bookService.getBookById(request.getBookId());

            if (userOptional.isEmpty() || bookOptional.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            User user = userOptional.get();
            Book book = bookOptional.get();

            List<Borrowing> borrowings = borrowingService.getBorrowingsByUserAndBook(user, book);
            if (borrowings.isEmpty()) {
                return ResponseEntity.badRequest().body("The user has not borrowed this book.");
            }

            Borrowing borrowing = borrowings.get(0); // Assuming we only need the first borrowing in the list
            LocalDate dueDate = borrowing.getDueDate();

            LoanManagement loan = new LoanManagement();
            loan.setUser(user);
            loan.setBook(book);
            loan.setDueDate(dueDate);
            loan.setStatus("Borrowed");

            loan = loanManagementService.createLoan(user, book, dueDate);

            return ResponseEntity.ok("Loan created successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping("/{loanId}")
    public ResponseEntity<String> deleteLoan(@PathVariable Long loanId, @RequestParam(required = false) String privateKey) {
        if (validatePrivateKey(privateKey, "librarian")) {
            try {
                Optional<LoanManagement> loanOptional = loanManagementRepository.findById(loanId);
                if (loanOptional.isPresent()) {
                    LoanManagement loan = loanOptional.get();
                    loanManagementRepository.delete(loan);

                    // Update the book's available quantity
                    Book book = loan.getBook();
                    book.setAvailableQuantity(book.getAvailableQuantity() + 1);
                    bookRepository.save(book);

                    return ResponseEntity.ok("Loan deleted successfully.");
                } else {
                    return ResponseEntity.notFound().build();
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/track-due-dates")
    public ResponseEntity<List<LoanManagementDTO>> trackDueDates(@RequestParam(required = false) String privateKey) {
        if (validatePrivateKey(privateKey, "librarian")) {
            try {
                List<LoanManagementDTO> updatedLoans = loanManagementService.trackDueDates();
                return ResponseEntity.ok(updatedLoans);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/send-reminders")
    public ResponseEntity<String> sendReminders(@RequestParam(required = false) String privateKey) {
        if (validatePrivateKey(privateKey, "librarian")) {
            try {
                loanManagementService.sendReminders();
                return ResponseEntity.ok("Reminders sent successfully.");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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

    private String getEmailFromToken(String privateKey) {
        CurrentUserSession currentUserSession = currentUserSessionService.findByPrivateKey(privateKey);
        if (currentUserSession != null) {
            return currentUserSession.getEmail();
        } else {
            // Handle the case where the token is invalid or expired
            // You can throw an exception or return null as per your requirement
            throw new RuntimeException("Invalid or expired token");
        }
    }
}
