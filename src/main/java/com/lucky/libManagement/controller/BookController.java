package com.lucky.libManagement.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.lucky.libManagement.entity.Book;
import com.lucky.libManagement.entity.CurrentUserSession;
import com.lucky.libManagement.service.BookService;
import com.lucky.libManagement.service.CurrentUserSessionService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/books")
public class BookController {

	private final BookService bookService;
    private final CurrentUserSessionService currentUserSessionService;

    public BookController(BookService bookService, CurrentUserSessionService currentUserSessionService) {
        this.bookService = bookService;
        this.currentUserSessionService = currentUserSessionService;
    }
    
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Book> addBook(@RequestPart("book") String bookDataJson,
                                        @RequestPart("imageFile") MultipartFile imageFile,
                                        @RequestParam(required = false) String privateKey) {
        if (validatePrivateKey(privateKey, "librarian")) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                Book book = objectMapper.readValue(bookDataJson, Book.class);
                
                byte[] imageBytes = imageFile.getBytes();
                book.setImage(imageBytes);

                Book addedBook = bookService.addBook(book);
                return ResponseEntity.ok(addedBook);
            } catch (IOException e) {
                // Handle IOException
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    
    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable Long bookId, @RequestParam(required = false) String privateKey) {
        if (validatePrivateKey(privateKey, "user" ,"librarian")) {
            Optional<Book> optionalBook = bookService.getBookById(bookId);
            return optionalBook.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(@RequestParam(required = false) String privateKey) {
        if (validatePrivateKey(privateKey, "user" ,"librarian")) {
            List<Book> books = bookService.getAllBooks();
            return ResponseEntity.ok(books);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Book> updateBook(@RequestPart("book") String bookDataJson,
                                           @RequestPart(value = "imageFile", required = false) MultipartFile imageFile,
                                           @RequestParam(required = false) String privateKey) {
        if (validatePrivateKey(privateKey, "librarian")) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                Book updatedBook = objectMapper.readValue(bookDataJson, Book.class);

                if (imageFile != null) {
                    byte[] imageBytes = imageFile.getBytes();
                    updatedBook.setImage(imageBytes);
                }

                Book savedBook = bookService.updateBook(updatedBook);
                return ResponseEntity.ok(savedBook);
            } catch (IOException e) {
                // Handle IOException
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId, @RequestParam(required = false) String privateKey) {
        if (validatePrivateKey(privateKey, "librarian")) {
            bookService.deleteBook(bookId);
            return ResponseEntity.noContent().build();
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
