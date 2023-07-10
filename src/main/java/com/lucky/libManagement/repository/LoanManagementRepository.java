package com.lucky.libManagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucky.libManagement.entity.Book;
import com.lucky.libManagement.entity.LoanManagement;
import com.lucky.libManagement.entity.User;

public interface LoanManagementRepository extends JpaRepository<LoanManagement, Long> {
    List<LoanManagement> findByUser(User user);
    List<LoanManagement> findByBook(Book book);
    @Override
	Optional<LoanManagement> findById(Long loanId);
    List<LoanManagement> findByStatus(String status);
}
