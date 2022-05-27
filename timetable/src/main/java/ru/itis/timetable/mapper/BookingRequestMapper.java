package ru.itis.timetable.mapper;

import org.springframework.stereotype.Component;
import ru.itis.timetable.dto.BookingRequestDto;
import ru.itis.timetable.model.BookingRequest;

import java.time.LocalDateTime;

@Component
public class BookingRequestMapper {
    public BookingRequestDto toDto(BookingRequest bookingRequest) {
        return BookingRequestDto
                .builder()
                .id(bookingRequest.getId())
                .finishDate(bookingRequest.getFinishDate())
                .email(bookingRequest.getEmail())
                .period(bookingRequest.getPeriod())
                .startDate(bookingRequest.getStartDate())
                .build();
    }

    public BookingRequest toModel(BookingRequestDto bookingRequestDto) {
        return BookingRequest
                .builder()
                .id(bookingRequestDto.getId())
                .email(bookingRequestDto.getEmail())
                .finishDate(bookingRequestDto.getFinishDate())
                .period(bookingRequestDto.getPeriod())
                .startDate(bookingRequestDto.getStartDate())
                .build();
    }
}
