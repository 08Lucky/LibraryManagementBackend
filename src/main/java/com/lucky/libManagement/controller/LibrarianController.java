package com.lucky.libManagement.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lucky.libManagement.entity.CurrentUserSession;
import com.lucky.libManagement.entity.User;
import com.lucky.libManagement.entity.UserRequest;
import com.lucky.libManagement.repository.CurrentUserSessionRepository;
import com.lucky.libManagement.repository.UserRepository;
import com.lucky.libManagement.request.LibrarianRequest;
import com.lucky.libManagement.service.CurrentUserSessionService;
import com.lucky.libManagement.service.LibrarianService;
import com.lucky.libManagement.service.UserService;

import net.bytebuddy.utility.RandomString;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/librarian")
public class LibrarianController {
	
    private final LibrarianService librarianService;
    	
	private final UserRepository userRepository;
	
	private final CurrentUserSessionService currentUserSessionService;
	
	private final CurrentUserSessionRepository currentUserSessionRepository;
	
	public LibrarianController(LibrarianService librarianService, CurrentUserSessionService currentUserSessionService, UserRepository userRepository, CurrentUserSessionRepository currentUserSessionRepository) {
        this.librarianService = librarianService;
        this.currentUserSessionService = currentUserSessionService;
        this.userRepository = userRepository;
        this.currentUserSessionRepository = currentUserSessionRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerLibrarian(@RequestBody LibrarianRequest request) {
        librarianService.registerLibrarian(request.getUsername(), request.getPassword());
        return ResponseEntity.ok("Librarian registered successfully.");
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> loginLibrarian(@RequestBody LibrarianRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        if (librarianService.isLibrarianCredentialsValid(username, password)) {
            String privateKey = RandomString.make(6);
            CurrentUserSession currentUserSession = new CurrentUserSession();
            currentUserSession.setEmail(username);
            currentUserSession.setRole("librarian");
            currentUserSession.setPrivateKey(privateKey);
            currentUserSession.setLoginDateTime(LocalDateTime.now());
            currentUserSessionRepository.save(currentUserSession);
            return ResponseEntity.ok(privateKey);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestParam(required = false) String privateKey) {
        if (validatePrivateKeyNoRole(privateKey)) {
            currentUserSessionService.removeCurrentUserSession(privateKey);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
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
