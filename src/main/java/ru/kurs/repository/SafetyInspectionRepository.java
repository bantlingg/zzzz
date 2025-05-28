package ru.kurs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kurs.entity.SafetyInspection;
import java.util.List;

public interface SafetyInspectionRepository extends JpaRepository<SafetyInspection, Long> {
    List<SafetyInspection> findByQuarryId(Long quarryId);
    List<SafetyInspection> findByInspectorId(Long inspectorId);
    List<SafetyInspection> findByStatus(String status);
    List<SafetyInspection> findByInspectionType(String inspectionType);
} 