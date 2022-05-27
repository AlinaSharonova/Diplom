package ru.itis.timetable.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.timetable.dto.BookingRequestDto;
import ru.itis.timetable.mapper.BookingRequestMapper;
import ru.itis.timetable.repository.BookingRequestRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingRequestServiceImpl implements BookingRequestService {

    private final BookingRequestMapper bookingRequestMapper;
    private final BookingRequestRepository bookingRequestRepository;

    @Override
    public List<BookingRequestDto> findAll() {
        return bookingRequestRepository.findAll()
                .stream()
                .map(bookingRequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookingRequestDto save(BookingRequestDto bookingRequest) {
        var savedAuditory = bookingRequestRepository.save(bookingRequestMapper.toModel(bookingRequest));
        return bookingRequestMapper.toDto(savedAuditory);
    }


    @Override
    public void delete(Long id) {
        bookingRequestRepository.deleteById(id);
    }
}
