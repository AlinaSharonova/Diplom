package ru.itis.timetable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.timetable.model.Auditory;

import java.util.UUID;

@Repository
public interface AuditoryRepository extends JpaRepository<Auditory, Long> {
}
