package ru.itis.timetable.service;

import ru.itis.timetable.dto.BookingRequestDto;

import java.util.List;
import java.util.UUID;

public interface BookingRequestService {
    List<BookingRequestDto> findAll();

    BookingRequestDto save(BookingRequestDto bookingRequest);

    void delete(Long id);
}
