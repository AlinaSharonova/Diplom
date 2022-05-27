package ru.itis.timetable.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.timetable.dto.AuditoryDto;
import ru.itis.timetable.mapper.AuditoryMapper;
import ru.itis.timetable.repository.AuditoryRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuditoryServiceImpl implements AuditoryService {

    private final AuditoryRepository auditoryRepository;
    private final AuditoryMapper auditoryMapper;

    @Override
    public List<AuditoryDto> findAll() {
        return auditoryRepository.findAll()
                .stream()
                .map(auditoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AuditoryDto save(AuditoryDto auditory) {
        var savedAuditory = auditoryRepository.save(auditoryMapper.toModel(auditory));
        return auditoryMapper.toDto(savedAuditory);
    }

    @Override
    public void delete(Long id) {
        auditoryRepository.deleteById(id);
    }
}
