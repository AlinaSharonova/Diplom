package ru.itis.timetable.service;

import ru.itis.timetable.dto.BookingDto;

import java.util.List;

public interface BookingService {
    List<BookingDto> findAll();

    BookingDto save(BookingDto booking);

    void delete(Long id);
}
