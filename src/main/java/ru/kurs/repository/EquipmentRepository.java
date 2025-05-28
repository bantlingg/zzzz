package ru.kurs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kurs.entity.Equipment;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
} 