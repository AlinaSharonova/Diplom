package ru.itis.timetable.service;

import org.springframework.stereotype.Service;
import ru.itis.timetable.model.Auditory;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class DistanceService {
    private final int[][] dist = new int[100][100];

    @PostConstruct
    public void init() throws FileNotFoundException {
        var file = new File("src/main/resources/data/distances.csv");

        var sc = new Scanner(file);

        while (sc.hasNext()) {
            var str = sc.nextLine();
            var ints = Arrays.stream(str.split(",")).map(Integer::parseInt).collect(Collectors.toList());

            dist[ints.get(0)][ints.get(1)] = ints.get(2);
            dist[ints.get(1)][ints.get(0)] = ints.get(2);
        }
    }

    public long getDistanceBetween(Auditory one, Auditory two) {
        return dist[one.getId().intValue()][two.getId().intValue()];
    }


}
