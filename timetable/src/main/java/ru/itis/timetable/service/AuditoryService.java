package ru.itis.timetable.service;

import ru.itis.timetable.dto.AuditoryDto;

import java.util.List;
import java.util.UUID;

public interface AuditoryService {

    List<AuditoryDto> findAll();

    AuditoryDto save(AuditoryDto auditory);

    void delete(Long id);
}
