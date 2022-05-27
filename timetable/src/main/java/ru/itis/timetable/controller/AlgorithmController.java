package ru.itis.timetable.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.timetable.util.getenetic.algorithm.GeneticAlgorithm;

@RestController
@RequiredArgsConstructor
public class AlgorithmController {
    private final GeneticAlgorithm algorithm;

    @PostMapping("/api/train")
    public void train() {
        algorithm.train();
    }
}
