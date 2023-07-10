package com.lucky.libManagement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lucky.libManagement.entity.Book;
import com.lucky.libManagement.entity.Borrowing;
import com.lucky.libManagement.entity.LoanManagement;
import com.lucky.libManagement.entity.Report;
import com.lucky.libManagement.entity.User;
import com.lucky.libManagement.repository.BookRepository;
import com.lucky.libManagement.repository.BorrowingRepository;
import com.lucky.libManagement.repository.LoanManagementRepository;
import com.lucky.libManagement.repository.ReportRepository;
import com.lucky.libManagement.repository.ReservationRepository;
import com.lucky.libManagement.repository.UserRepository;

@Service
public class ReportsService {

	private final ReportRepository reportsRepository;
    private final BorrowingRepository borrowingRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ReservationRepository reservationRepository;
    private final LoanManagementRepository loanManagementRepository;
    private static int reportIdCounter = 1;

    public ReportsService(ReportRepository reportsRepository, BorrowingRepository borrowingRepository,
                          UserRepository userRepository, BookRepository bookRepository, ReservationRepository reservationRepository, LoanManagementRepository loanManagementRepository) {
        this.reportsRepository = reportsRepository;
        this.borrowingRepository = borrowingRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
		this.reservationRepository = reservationRepository;
		this.loanManagementRepository = loanManagementRepository;
    }

    private long generateReportId() {
        // Generate a unique report ID using a counter
        long reportId = reportIdCounter;
        reportIdCounter++;
        return reportId;
    }

    public List<Report> generateUserActivityReport() {
        List<Report> userActivityReports = new ArrayList<>();

        List<Borrowing> borrowings = borrowingRepository.findAll();
        for (Borrowing borrowing : borrowings) {
            User user = borrowing.getUser();
            String userActivity = borrowing.getBorrowingId() + ", " +
                    user.getFirstName() + ", " +
                    user.getLastName() + ", " +
                    user.getEmail() + ", " +
                    borrowing.getStatus() + ", " +
                    borrowing.getBorrowingId();

            Report report = new Report();
            long reportId = generateReportId();
            report.setReportId(reportId);
            report.setUserActivity(userActivity);
            userActivityReports.add(report);
        }

        reportsRepository.saveAll(userActivityReports);

        return userActivityReports;
    }




    public List<String> generateBookStatusReport() {
        List<String> bookStatusReports = new ArrayList<>();

        List<Book> books = bookRepository.findAll();
        for (Book book : books) {
            String bookStatus = getBookStatus(book);
            String formattedBookStatus = formatBookStatus(bookStatus);
            bookStatusReports.add(formattedBookStatus);
        }

        return bookStatusReports;
    }

    private String getBookStatus(Book book) {
        String bookId = String.valueOf(book.getBookId());
        String title = book.getTitle();
        int availability = book.getAvailableQuantity();

        return bookId + ", " + title + ", " + availability;
    }

    private String formatBookStatus(String bookStatus) {
        return bookStatus;
    }


    public List<Report> generateFinesCollectedReport() {
        List<Report> finesCollectedReports = new ArrayList<>();

        List<LoanManagement> loanManagements = loanManagementRepository.findAll();
        for (LoanManagement loanManagement : loanManagements) {
            double fine = loanManagement.getFine();

            Report report = new Report();
            long reportId = generateReportId();
            report.setReportId(reportId);
            report.setFinesCollected(fine);
            finesCollectedReports.add(report);
        }
        reportsRepository.saveAll(finesCollectedReports);

        return finesCollectedReports;
    }

    private String getUserActivity(Borrowing borrowing) {
        return "User " + borrowing.getUser() + " borrowed book " + borrowing.getBook();
    }

}
