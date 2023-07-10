package com.lucky.libManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucky.libManagement.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    // Additional methods if needed
}
