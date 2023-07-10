package com.lucky.libManagement.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.lucky.libManagement.entity.User;
import com.lucky.libManagement.repository.EmailService;
import com.lucky.libManagement.repository.UserRepository;

@Service
public class EmailServiceImpl implements EmailService{

	private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;

    public EmailServiceImpl(JavaMailSender javaMailSender, UserRepository userRepository) {
        this.javaMailSender = javaMailSender;
        this.userRepository = userRepository;
    }

    @Override
	public void sendLoanReminder(Long userId, LocalDate dueDate) {
		String userEmail = getUserEmail(userId);
        if (userEmail != null) {
            String subject = "Loan Reminder";
            String body = "Your book was due on " + dueDate.toString() + ". Please return as soon as possibile. You will be charged 10 Rs per day from the due date.";
            sendEmail(userEmail, subject, body);
        }
	}

    @Override
    public String getUserEmail(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.map(User::getEmail).orElse(null);
    }

    private void sendEmail(String recipientEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }
}

