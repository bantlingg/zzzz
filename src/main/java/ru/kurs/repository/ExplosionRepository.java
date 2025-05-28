package ru.kurs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kurs.entity.Explosion;
import java.util.List;

public interface ExplosionRepository extends JpaRepository<Explosion, Long> {
    List<Explosion> findByQuarryId(Long quarryId);
    List<Explosion> findByStatus(String status);
} 