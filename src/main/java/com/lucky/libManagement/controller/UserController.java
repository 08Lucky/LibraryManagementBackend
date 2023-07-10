package com.lucky.libManagement.controller;

import java.util.Optional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lucky.libManagement.entity.CurrentUserSession;
import com.lucky.libManagement.entity.User;
import com.lucky.libManagement.entity.UserRequest;
import com.lucky.libManagement.exception.UserNotFoundException;
import com.lucky.libManagement.repository.CurrentUserSessionRepository;
import com.lucky.libManagement.repository.UserRepository;
import com.lucky.libManagement.service.CurrentUserSessionService;
import com.lucky.libManagement.service.UserService;

import net.bytebuddy.utility.RandomString;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;
	
	private final UserRepository userRepository;
	
	private final CurrentUserSessionService currentUserSessionService;
	
	private final CurrentUserSessionRepository currentUserSessionRepository;

    public UserController(UserService userService, CurrentUserSessionService currentUserSessionService, UserRepository userRepository, CurrentUserSessionRepository currentUserSessionRepository) {
        this.userService = userService;
        this.currentUserSessionService = currentUserSessionService;
        this.userRepository = userRepository;
        this.currentUserSessionRepository = currentUserSessionRepository;
    }
    
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email");
        }

        if (user.getPassword().equals(password)) {
            String privateKey = RandomString.make(6);
            CurrentUserSession currentUserSession = new CurrentUserSession();
            currentUserSession.setEmail(email);
            currentUserSession.setRole("user");
            currentUserSession.setPrivateKey(privateKey);
            currentUserSession.setLoginDateTime(LocalDateTime.now());
            currentUserSessionRepository.save(currentUserSession);
            return ResponseEntity.ok(privateKey);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
        }
    }
    
    @GetMapping("/{userId}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable Long userId, @RequestParam(required = false) String privateKey) {
        if (validatePrivateKey(privateKey, "user", "librarian")) {
        	Optional<User> optionalUser = userService.getUserById(userId);
            if (optionalUser != null) {
                return ResponseEntity.ok(optionalUser);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    
    @GetMapping("/getall")
    public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) String privateKey) {
        if (validatePrivateKey(privateKey, "librarian")) {
            List<User> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    
    @PutMapping("/update")
    public ResponseEntity<User> updateEmployee( @RequestBody User user, @RequestParam(required = false) String privateKey) {
        if (validatePrivateKey(privateKey, "user")) {
        	User updatedUser = userService.updateUser(user);
            if (updatedUser != null) {
                return ResponseEntity.ok(updatedUser);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId, @RequestParam(required = false) String privateKey) {
        if (validatePrivateKey( privateKey, "librarian")) {
            boolean deleted = userService.deleteUser(userId);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
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


