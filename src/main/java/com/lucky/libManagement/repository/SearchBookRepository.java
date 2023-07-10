package com.lucky.libManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucky.libManagement.entity.Book;

public interface SearchBookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleIgnoreCase(String title);
    List<Book> findByAuthorIgnoreCase(String author);
    List<Book> findBySubjectIgnoreCase(String subject);
}
