package ru.kurs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kurs.entity.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
} 