package com.lucky.libManagement.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lucky.libManagement.entity.Book;
import com.lucky.libManagement.entity.CurrentUserSession;
import com.lucky.libManagement.service.CurrentUserSessionService;
import com.lucky.libManagement.service.SearchBookService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/books")
public class SearchBookController {

	private final SearchBookService bookService;
    private final CurrentUserSessionService currentUserSessionService;

    public SearchBookController(SearchBookService bookService, CurrentUserSessionService currentUserSessionService) {
        this.bookService = bookService;
        this.currentUserSessionService = currentUserSessionService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) String privateKey) {

        if (validatePrivateKeyNoRole(privateKey)) {
            List<Book> books = new ArrayList<>();

            if (title != null) {
                books.addAll(bookService.searchBooksByTitle(title));
            }

            if (author != null) {
                books.addAll(bookService.searchBooksByAuthor(author));
            }

            if (subject != null) {
                books.addAll(bookService.searchBooksBySubject(subject));
            }

            if (books.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } else {
                return ResponseEntity.ok(books);
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
