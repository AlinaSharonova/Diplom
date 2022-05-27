package ru.itis.timetable.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.itis.timetable.dto.BookingDto;
import ru.itis.timetable.model.Booking;

@Component
@RequiredArgsConstructor
public class BookingMapper {
    private final BookingRequestMapper bookingRequestMapper;
    private final AuditoryMapper auditoryMapper;

    public BookingDto toDto(Booking booking) {
        return BookingDto
                .builder()
                .id(booking.getId())
                .bookingRequest(bookingRequestMapper.toDto(booking.getBookingRequest()))
                .auditory(auditoryMapper.toDto(booking.getAuditory()))
                .build();
    }
}
