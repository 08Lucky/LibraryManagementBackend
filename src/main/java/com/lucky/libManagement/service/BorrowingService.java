package com.lucky.libManagement.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lucky.libManagement.entity.Book;
import com.lucky.libManagement.entity.Borrowing;
import com.lucky.libManagement.entity.User;
import com.lucky.libManagement.repository.BookRepository;
import com.lucky.libManagement.repository.BorrowingRepository;

@Service
public class BorrowingService {

	private final BorrowingRepository borrowingRepository;
	private final BookRepository bookRepository;

	public BorrowingService(BorrowingRepository borrowingRepository, BookRepository bookRepository) {
        this.borrowingRepository = borrowingRepository;
        this.bookRepository = bookRepository;
    }

    public List<Borrowing> getBorrowingsByUser(Optional<User> user) {
        return borrowingRepository.findByUser(user);
    }

    public List<Borrowing> getBorrowingsByBook(Optional<Book> book) {
        return borrowingRepository.findByBook(book);
    }

    public List<Borrowing> getBorrowingsByStatus(String status) {
        return borrowingRepository.findByStatus(status);
    }

    public List<Borrowing> getBorrowingsByUserAndBook(User user, Book book) {
        return borrowingRepository.findByUserAndBook(user, book);
    }

    public Optional<Borrowing> getBorrowingsById(Long borrowingId) {
        return borrowingRepository.findById(borrowingId);
    }


    public Borrowing borrowBook(User user, Book book, LocalDate borrowDate, LocalDate dueDate) {
        Borrowing borrowing = new Borrowing();
        borrowing.setUser(user);
        borrowing.setBook(book);
        borrowing.setBorrowDate(borrowDate);
        borrowing.setDueDate(dueDate);
        borrowing.setStatus("Borrowed");

        borrowing = borrowingRepository.save(borrowing);

        // Update the available quantity of the book
        book.setAvailableQuantity(book.getAvailableQuantity() - 1);
        bookRepository.save(book);

        return borrowing;
    }

    public void returnBook(Borrowing borrowing, LocalDate returnDate) {
        borrowing.setReturnDate(returnDate);
        borrowing.setStatus("Returned");
        borrowingRepository.save(borrowing);

        Book book = borrowing.getBook();
        book.setAvailableQuantity(book.getAvailableQuantity() + 1);
        bookRepository.save(book);
    }
}
