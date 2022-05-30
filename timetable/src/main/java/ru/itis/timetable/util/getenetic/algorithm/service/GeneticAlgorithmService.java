package ru.itis.timetable.util.getenetic.algorithm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.timetable.model.Booking;
import ru.itis.timetable.model.Time;
import ru.itis.timetable.repository.AuditoryRepository;
import ru.itis.timetable.repository.BookingRepository;
import ru.itis.timetable.repository.BookingRequestRepository;
import ru.itis.timetable.service.DistanceService;
import ru.itis.timetable.util.getenetic.algorithm.Gen;
import ru.itis.timetable.util.getenetic.algorithm.Individual;
import ru.itis.timetable.util.getenetic.algorithm.Population;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeneticAlgorithmService {
    public final AuditoryRepository auditoryRepository;
    public final BookingRepository bookingRepository;
    public final BookingRequestRepository requestRepository;
    public final DistanceService distanceService;

    public static final Random rn = new Random();

    public Population generatePopulation(int popSize, List<Long> auditories, List<Long> requests) {
        var population = new Population(popSize);

        for (int i = 0; i < popSize; i++) {
            population.getIndividuals()[i] = new Individual(generateRandomGenes(auditories, requests));
        }

        return population;
    }

    private Gen[] generateRandomGenes(List<Long> auditories, List<Long> requests) {
        var gens = new Gen[requests.size()];
        var idx = 0;
        for (Long request : requests) {
            gens[idx] = new Gen(request, auditories.get(rn.nextInt(auditories.size())));
            idx++;
        }

        return gens;
    }

    public void calcFitness(Individual individual) {
        var bookings = toBookings(individual);

        var capacity = checkCapacity(bookings);
        var auditoryClashes = auditoryClashes(bookings);
        var requirementsFailed = requirementFailed(bookings);
        var distances = calculateDistances(bookings);
        individual.setDistances(distances);

        var fitness = (int) (distances * 200 + (capacity * 100) + (auditoryClashes * 1000) + (requirementsFailed * 700));
        individual.setFitness(fitness);
    }

    public List<Booking> toBookings(Individual individual) {
        return Arrays.stream(individual.getGenes())
                .map(this::genToBookings)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<Booking> genToBookings(Gen gen) {
        var request = requestRepository.findById(gen.getRequest()).orElseThrow(IllegalArgumentException::new);
        var auditory = auditoryRepository.findById(gen.getAuditory()).orElseThrow(IllegalArgumentException::new);

        switch (request.getPeriod()) {
            case ONCE: {
                return Collections.singletonList(Booking.builder()
                        .auditory(auditory)
                        .bookingRequest(request)
                        .date(request.getStartDate())
                        .time(request.getTime())
                        .build());
            }
            case WEEKLY: {
                var bookings = new ArrayList<Booking>();
                var date = request.getStartDate();
                while (date.isBefore(request.getFinishDate())) {
                    bookings.add(Booking
                            .builder()
                            .auditory(auditory)
                            .bookingRequest(request)
                            .date(date)
                            .time(request.getTime())
                            .build());
                    date = date.plusWeeks(1);
                }
                return bookings;
            }
            case MONTHLY: {
                var bookings = new ArrayList<Booking>();
                var date = request.getStartDate();
                while (date.isBefore(request.getFinishDate())) {
                    bookings.add(Booking
                            .builder()
                            .auditory(auditory)
                            .bookingRequest(request)
                            .date(date)
                            .time(request.getTime())
                            .build());
                    date = date.plusMonths(1);
                }
                return bookings;
            }
            case ONE_IN_TWO_WEEKS: {
                var bookings = new ArrayList<Booking>();
                var date = request.getStartDate();
                while (date.isBefore(request.getFinishDate())) {
                    bookings.add(Booking
                            .builder()
                            .auditory(auditory)
                            .bookingRequest(request)
                            .date(date)
                            .time(request.getTime())
                            .build());
                    date = date.plusWeeks(2);
                }
                return bookings;
            }
            default:
                return Collections.emptyList();
        }
    }

    private long checkCapacity(List<Booking> bookings) {
        return bookings
                .stream()
                .filter(b -> b.getBookingRequest().getCapacity() > b.getAuditory().getCapacity())
                .count();
    }

    private long auditoryClashes(List<Booking> bookings) {

        return bookings
                .stream()
                .collect(Collectors.groupingBy(Booking::getDate))
                .values()
                .stream()
                .map(list -> list
                        .stream()
                        .collect(Collectors.groupingBy(booking -> booking.getAuditory().getId()))
                        .values()
                        .stream()
                        .map(bookingsInOneDayOfOneAuditory -> bookingsInOneDayOfOneAuditory
                                .stream()
                                .collect(Collectors.groupingBy(Booking::getTime))
                                .values()
                                .stream()
                                .map(Collection::size)
                                .filter(size -> size > 1)
                                .count()
                        ).reduce(Long::sum).orElse(0L)
                ).reduce(Long::sum).orElse(0L);
    }

    private long requirementFailed(List<Booking> bookings) {
        return bookings
                .stream()
                .filter(b -> !b.getBookingRequest().getRequirements().containsAll(b.getAuditory().getRequirements()))
                .count();
    }

    private long calculateDistances(List<Booking> bookings) {
        var map = bookings.stream().collect(Collectors.groupingBy(Booking::getDate));

        var dist = new AtomicLong();
        for (Map.Entry<LocalDate, List<Booking>> entry : map.entrySet()) {
            entry.getValue()
                    .stream()
                    .collect(Collectors.groupingBy(b -> b.getBookingRequest().getGroup()))
                    .values()
                    .forEach(bookingList -> bookingList
                            .stream()
                            .sorted(Comparator.comparing(Booking::getTime))
                            .map(Booking::getAuditory)
                            .reduce((a, a1) -> {
                                dist.addAndGet(Math.round(distanceService.getDistanceBetween(a, a1)));
                                return a1;
                            })
                    );

            entry.getValue()
                    .stream()
                    .collect(Collectors.groupingBy(b -> b.getBookingRequest().getEmail()))
                    .values()
                    .forEach(bookingList -> bookingList
                            .stream()
                            .sorted(Comparator.comparing(Booking::getTime))
                            .map(Booking::getAuditory)
                            .reduce((a, a1) -> {
                                dist.addAndGet(Math.round(distanceService.getDistanceBetween(a, a1)));
                                return a1;
                            })
                    );

        }
        return dist.get();
    }
}