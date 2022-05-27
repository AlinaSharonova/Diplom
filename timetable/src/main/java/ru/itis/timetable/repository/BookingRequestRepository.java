package ru.itis.timetable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.timetable.model.BookingRequest;
import ru.itis.timetable.model.RequestStatus;

import java.util.List;

@Repository
public interface BookingRequestRepository extends JpaRepository<BookingRequest, Long> {
    List<BookingRequest> findAllByStatus(RequestStatus status);

}
