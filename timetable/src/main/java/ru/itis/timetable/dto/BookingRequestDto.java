package ru.itis.timetable.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.timetable.model.BookingPeriod;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingRequestDto {

    private Long id;
    private BookingPeriod period;
    private LocalDate finishDate;
    private LocalDate startDate;
    private String email;
}
