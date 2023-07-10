package com.lucky.libManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucky.libManagement.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
    // Custom query methods can be added if needed
}