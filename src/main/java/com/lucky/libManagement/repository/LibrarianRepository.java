package com.lucky.libManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucky.libManagement.entity.Librarian;

public interface LibrarianRepository extends JpaRepository<Librarian, Long>{

	 Librarian findByUsername(String username);
}
