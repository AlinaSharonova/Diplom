package ru.itis.timetable.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.timetable.dto.BookingDto;
import ru.itis.timetable.mapper.BookingMapper;
import ru.itis.timetable.model.Booking;
import ru.itis.timetable.repository.AuditoryRepository;
import ru.itis.timetable.repository.BookingRepository;
import ru.itis.timetable.repository.BookingRequestRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingRequestRepository bookingRequestRepository;
    private final AuditoryRepository auditoryRepository;
    private final BookingMapper bookingMapper;

    @Override
    public List<BookingDto> findAll() {
        return bookingRepository.findAll()
                .stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookingDto save(BookingDto booking) {
        var bookingEntity = new Booking();

        var auditory = auditoryRepository.findById(booking.getAuditory().getId())
                .orElseThrow(IllegalArgumentException::new);

        var bookingRequest = bookingRequestRepository.findById(booking.getBookingRequest().getId())
                .orElseThrow(IllegalArgumentException::new);

        bookingEntity.setBookingRequest(bookingRequest);
        bookingEntity.setAuditory(auditory);
        bookingEntity.setTime(booking.getTime());

        var savedBooking = bookingRepository.save(bookingEntity);
        return bookingMapper.toDto(savedBooking);
    }

    @Override
    public void delete(Long id) {
        bookingRepository.deleteById(id);
    }
}
