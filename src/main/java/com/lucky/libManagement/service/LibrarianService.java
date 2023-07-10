package com.lucky.libManagement.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lucky.libManagement.entity.Librarian;
import com.lucky.libManagement.repository.LibrarianRepository;

@Service
public class LibrarianService {

	private final LibrarianRepository librarianRepository;

    public LibrarianService(LibrarianRepository librarianRepository) {
        this.librarianRepository = librarianRepository;
    }

    public boolean isLibrarianCredentialsValid(String username, String password) {
        Optional<Librarian> optionalLibrarian = Optional.ofNullable(librarianRepository.findByUsername(username));
        if (optionalLibrarian.isPresent()) {
            Librarian librarian = optionalLibrarian.get();
            return librarian.getPassword().equals(password);
        }
        return false;
    }

    public void registerLibrarian(String username, String password) {
        Librarian librarian = new Librarian();
        librarian.setUsername(username);
        librarian.setPassword(password);
        librarianRepository.save(librarian);
    }

}
