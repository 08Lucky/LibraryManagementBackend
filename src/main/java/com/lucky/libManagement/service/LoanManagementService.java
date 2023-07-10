package com.lucky.libManagement.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lucky.libManagement.entity.Book;
import com.lucky.libManagement.entity.LoanManagement;
import com.lucky.libManagement.entity.LoanManagementDTO;
import com.lucky.libManagement.entity.User;
import com.lucky.libManagement.repository.LoanManagementRepository;
import com.lucky.libManagement.repository.UserRepository;

@Service
public class LoanManagementService {

	private final LoanManagementRepository loanManagementRepository;
	private final EmailServiceImpl emailService;
	private final UserRepository userRepository;

	public LoanManagementService(LoanManagementRepository loanManagementRepository, EmailServiceImpl emailService, UserRepository userRepository) {
        this.loanManagementRepository = loanManagementRepository;
        this.emailService = emailService;
        this.userRepository = userRepository;
	}

	public List<LoanManagement> getLoansByUser(User user) {
        return loanManagementRepository.findByUser(user);
	}

	public List<LoanManagement> getLoansByBook(Book book) {
       return loanManagementRepository.findByBook(book);
    }

	public List<LoanManagement> getLoansByStatus(String status) {
        return loanManagementRepository.findByStatus(status);
	}

	public Optional<LoanManagement> getLoanById(Long loanId) {
        return loanManagementRepository.findById(loanId);
    }

	public LoanManagement saveLoan(LoanManagement loan) {
        return loanManagementRepository.save(loan);
    }

	public LoanManagement createLoan(User user, Book book, LocalDate dueDate) {
        LoanManagement loanManagement = new LoanManagement();
        loanManagement.setUser(user);
        loanManagement.setBook(book);
        loanManagement.setDueDate(dueDate);
        loanManagement.setFine(0.0);
        loanManagement.setStatus("Borrowed");

        return loanManagementRepository.save(loanManagement);
    }

	public List<LoanManagementDTO> trackDueDates() {
	    List<LoanManagement> loans = loanManagementRepository.findAll();
	    LocalDate currentDate = LocalDate.now();
	    List<LoanManagementDTO> updatedLoans = new ArrayList<>();

	    for (LoanManagement loan : loans) {
	        if (loan.getDueDate().isBefore(currentDate) && loan.getStatus().equals("Borrowed")) {
	            long overdueDays = ChronoUnit.DAYS.between(loan.getDueDate(), currentDate);
	            double fine = overdueDays * 10.0; // Assuming a fine of 10 RS per day

	            loan.setFine(fine);
	            loanManagementRepository.save(loan);

	            LoanManagementDTO loanDTO = new LoanManagementDTO();
	            loanDTO.setLoanId(loan.getLoanId());
	            loanDTO.setDueDate(loan.getDueDate());
	            updatedLoans.add(loanDTO);
	        }
	    }

	    return updatedLoans;
	}


	public void sendReminders() {
        List<LoanManagement> loans = loanManagementRepository.findAll();

        LocalDate currentDate = LocalDate.now();

        for (LoanManagement loan : loans) {
            if (loan.getDueDate().isBefore(currentDate) && loan.getStatus().equals("Borrowed")) {

                // Send loan reminder to the user
                User user = loan.getUser();
                Long userId = user.getUserId();
                LocalDate dueDate = loan.getDueDate();
                emailService.sendLoanReminder(userId, dueDate);
            }
        }
    }
}
