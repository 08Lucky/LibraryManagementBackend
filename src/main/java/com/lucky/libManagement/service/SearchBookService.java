package com.lucky.libManagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lucky.libManagement.entity.Book;
import com.lucky.libManagement.repository.SearchBookRepository;

@Service
public class SearchBookService {

	 private final SearchBookRepository bookRepository;

	    public SearchBookService(SearchBookRepository bookRepository) {
	        this.bookRepository = bookRepository;
	    }

	    public List<Book> searchBooksByTitle(String title) {
	        return bookRepository.findByTitleIgnoreCase(title);
	    }

	    public List<Book> searchBooksByAuthor(String author) {
	        return bookRepository.findByAuthorIgnoreCase(author);
	    }

	    public List<Book> searchBooksBySubject(String subject) {
	        return bookRepository.findBySubjectIgnoreCase(subject);
	    }
}
