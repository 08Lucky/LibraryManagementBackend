package com.lucky.libManagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucky.libManagement.entity.Book;
import com.lucky.libManagement.entity.Borrowing;
import com.lucky.libManagement.entity.User;

@Repository
public interface BorrowingRepository extends JpaRepository<Borrowing, Long> { 
	//JpaRepository extends the CrudRepository interface and provides additional methods for performing common database operations 
	List<Borrowing> findByUser(Optional<User> user);
    List<Borrowing> findByBook(Optional<Book> book);
    List<Borrowing> findByStatus(String status);
    List<Borrowing> findByUserAndBook (User user, Book book);
}
