package ru.itis.timetable.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.itis.timetable.model.Auditory;
import ru.itis.timetable.repository.AuditoryRepository;
import ru.itis.timetable.repository.RequirementRepository;

import java.io.File;
import java.util.Scanner;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuditoryDataService {
    private final RequirementRepository requirementRepository;
    private final AuditoryRepository auditoryRepository;


    @SneakyThrows
    public void loadAuditories() {
        var file = new File("src/main/resources/data/auditory.csv");

        var sc = new Scanner(file);
        sc.nextLine();

        for (int i = 0; i < 87; i++) {
            var str = sc.nextLine().split(",");

            var auditory = new Auditory();
            auditory.setId(Long.getLong(str[0]));
            auditory.setAddress(str[1] + " " + str[2] + " " + str[3]);

            auditory.setNumber(str[4]);

            if (str.length < 6) {
                continue;
            }
            var capacity = str[5];
            auditory.setCapacity(Integer.parseInt(capacity));

            if (str.length > 6) {
                var type = str[6];
                var requirement = requirementRepository.findByType(type);
                auditory.setRequirements(Set.of(requirement));
            }
            auditoryRepository.saveAndFlush(auditory);
        }
    }
}
