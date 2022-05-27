package ru.itis.timetable.mapper;

import org.springframework.stereotype.Component;
import ru.itis.timetable.dto.AuditoryDto;
import ru.itis.timetable.model.Auditory;

import java.time.LocalDateTime;

@Component
public class AuditoryMapper {
    public AuditoryDto toDto(Auditory auditory) {
        return AuditoryDto
                .builder()
                .id(auditory.getId())
                .address(auditory.getAddress())
                .number(auditory.getNumber())
                .build();
    }

    public Auditory toModel(AuditoryDto auditory) {
        return Auditory
                .builder()
                .address(auditory.getAddress())
                .id(auditory.getId())
                .build();
    }
}
