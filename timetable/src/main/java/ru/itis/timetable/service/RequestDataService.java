package ru.itis.timetable.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.itis.timetable.model.Auditory;
import ru.itis.timetable.model.BookingPeriod;
import ru.itis.timetable.model.BookingRequest;
import ru.itis.timetable.model.RequestStatus;
import ru.itis.timetable.repository.BookingRequestRepository;
import ru.itis.timetable.repository.RequirementRepository;

import java.io.File;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RequestDataService {
    private final BookingRequestRepository requestRepository;
    private final RequirementRepository requirementRepository;

    @SneakyThrows
    public void loadRequests() {
        var file = new File("src/main/resources/data/requests.csv");

        var sc = new Scanner(file);
        sc.nextLine();

        for (int i = 0; i < 28; i++) {
            var str = sc.nextLine().split(",");

            var request = new BookingRequest();
            request.setName(str[0]);
            request.setPeriod(BookingPeriod.WEEKLY);
            request.setStartDate(LocalDate.parse("2022-02-07"));
            request.setFinishDate(LocalDate.parse("2022-06-30"));
            request.setStatus(RequestStatus.TO_DECIDE);
            request.setTime(str[3]);
            request.setGroup(str[4]);
            request.setEmail(str[5]);
            request.setCapacity(Integer.parseInt(str[6]));

            if (str.length > 7) {
                var requirement = str[7];
                request.setRequirements(Set.of(requirementRepository.findByType(requirement)));
            }

            requestRepository.saveAndFlush(request);
        }
    }
}
