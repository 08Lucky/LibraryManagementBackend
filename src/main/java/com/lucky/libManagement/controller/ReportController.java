package com.lucky.libManagement.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lucky.libManagement.entity.CurrentUserSession;
import com.lucky.libManagement.entity.Report;
import com.lucky.libManagement.entity.UserActivityDTO;
import com.lucky.libManagement.service.BookService;
import com.lucky.libManagement.service.CurrentUserSessionService;
import com.lucky.libManagement.service.ReportsService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/reports")
public class ReportController {

	private final ReportsService reportsService;
    private final BookService bookService;
    private final CurrentUserSessionService currentUserSessionService;

    public ReportController(ReportsService reportsService, BookService bookService, CurrentUserSessionService currentUserSessionService) {
        this.reportsService = reportsService;
        this.bookService = bookService;
        this.currentUserSessionService = currentUserSessionService;
    }

    @GetMapping("/user-activity")
    public ResponseEntity<List<UserActivityDTO>> generateUserActivityReport(@RequestParam(required = false) String privateKey) {
        if (validatePrivateKey(privateKey, "librarian")) {
            List<Report> userActivityReports = reportsService.generateUserActivityReport();
            List<UserActivityDTO> userActivities = extractUserActivities(userActivityReports);
            return ResponseEntity.ok(userActivities);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/book-status")
    public ResponseEntity<List<String>> generateBookStatusReport(@RequestParam(required = false) String privateKey) {
        if (validatePrivateKey(privateKey, "librarian")) {
            List<String> bookStatuses = reportsService.generateBookStatusReport();
            return ResponseEntity.ok(bookStatuses);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/fines-collected")
    public ResponseEntity<List<Double>> generateFinesCollectedReport(@RequestParam(required = false) String privateKey) {
        if (validatePrivateKey(privateKey, "librarian")) {
            List<Report> finesCollectedReports = reportsService.generateFinesCollectedReport();
            List<Double> finesCollected = extractFinesCollected(finesCollectedReports);
            return ResponseEntity.ok(finesCollected);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    // Helper methods to extract specific values from the reports

    private List<UserActivityDTO> extractUserActivities(List<Report> userActivityReports) {
        List<UserActivityDTO> userActivities = new ArrayList<>();

        for (Report report : userActivityReports) {
            String userActivity = report.getUserActivity();

            // Split the user activity string by a delimiter to extract individual values
            String[] activityValues = userActivity.split(",");

            // Extract the relevant information from the split values
            String userId = activityValues[0].trim();
            String firstName = activityValues[1].trim();
            String lastName = activityValues[2].trim();
            String email = activityValues[3].trim();
            String activityType = activityValues[4].trim();

            // Create a UserActivityDTO object with the extracted information
            UserActivityDTO userActivityDTO = new UserActivityDTO(userId, firstName, lastName, email, activityType);

            // Add the UserActivityDTO to the list
            userActivities.add(userActivityDTO);
        }

        return userActivities;
    }



    private List<String> extractBookStatuses(List<Report> bookStatusReports) {
        List<String> bookStatuses = new ArrayList<>();
        
        for (Report report : bookStatusReports) {
            String bookStatus = report.getBookStatus();
            
            // Split the book status string by a delimiter to extract individual values
            String[] statusValues = bookStatus.split(",");
            
            // Extract the relevant information from the split values
            String bookId = statusValues[0].trim();
            String bookTitle = statusValues[1].trim();
            String bookAvailability = statusValues[2].trim();
            
            // Customize the format of the book status string as per your requirements
            String formattedBookStatus = "Book ID: " + bookId + ", ";
            formattedBookStatus += "Title: " + bookTitle + ", ";
            formattedBookStatus += "Availability: " + bookAvailability;
            
            // Add the formatted book status string to the list
            bookStatuses.add(formattedBookStatus);
        }
        
        return bookStatuses;
    }


    private List<Double> extractFinesCollected(List<Report> finesCollectedReports) {
        // Extract fines collected values from the reports
        // You can customize the logic as per your requirements
        List<Double> finesCollected = new ArrayList<>();
        for (Report report : finesCollectedReports) {
        	double fines = report.getFinesCollected();
            finesCollected.add(fines);

        }
        return finesCollected;
    }
    
    public boolean validatePrivateKey(String privateKey, String... allowedRoles) {
        if (privateKey == null) {
            return false;
        }
        CurrentUserSession currentUserSession = currentUserSessionService.findByPrivateKey(privateKey);
        if (currentUserSession == null) {
            return false;
        }
        String userRole = currentUserSession.getRole();
        for (String allowedRole : allowedRoles) {
            if (userRole.equals(allowedRole)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean validatePrivateKeyNoRole(String PrivateKey) {
    	if (PrivateKey == null) {
    		return false; // Or handle the null case according to your requirements
        }
        CurrentUserSession currentUserSession = currentUserSessionService.findByPrivateKey(PrivateKey);
        boolean isOk = false;
        if(currentUserSession != null && currentUserSession.getPrivateKey().equals(PrivateKey)) {
        	isOk = true;
        }
        return isOk;
   }
}
