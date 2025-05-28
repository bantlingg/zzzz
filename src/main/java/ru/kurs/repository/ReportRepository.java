package ru.kurs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kurs.entity.Report;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByQuarryId(Long quarryId);
    List<Report> findByAuthorId(Long authorId);
    List<Report> findByReportType(String reportType);
} 