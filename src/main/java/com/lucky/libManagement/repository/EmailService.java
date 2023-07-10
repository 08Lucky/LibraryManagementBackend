package com.lucky.libManagement.repository;

import java.time.LocalDate;

public interface EmailService {

	void sendLoanReminder(Long userId, LocalDate dueDate);
    String getUserEmail(Long userId);
}

