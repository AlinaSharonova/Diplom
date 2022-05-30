package ru.itis.timetable.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.timetable.service.AuditoryDataService;
import ru.itis.timetable.service.DistanceService;
import ru.itis.timetable.service.RequestDataService;
import ru.itis.timetable.util.getenetic.algorithm.GeneticAlgorithm;

@RestController
@RequiredArgsConstructor
public class AlgorithmController {
    private final GeneticAlgorithm algorithm;
    private final AuditoryDataService auditoryDataService;
    private final DistanceService distanceService;
    private final RequestDataService requestDataService;

    @PostMapping("/api/train")
    public void train() {
        algorithm.train();
    }

    @GetMapping("/api/load-data")
    public void load() {
        auditoryDataService.loadAuditories();
        requestDataService.loadRequests();
    }
}
