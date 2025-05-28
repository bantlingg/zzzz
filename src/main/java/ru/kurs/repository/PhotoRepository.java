package ru.kurs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kurs.entity.Photo;
import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findByQuarryId(Long quarryId);
    List<Photo> findByUploadDateBetween(java.time.LocalDateTime start, java.time.LocalDateTime end);
} 