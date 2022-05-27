package ru.itis.timetable.util.getenetic.algorithm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itis.timetable.model.Auditory;
import ru.itis.timetable.model.BookingRequest;
import ru.itis.timetable.repository.AuditoryRepository;
import ru.itis.timetable.repository.BookingRequestRepository;
import ru.itis.timetable.util.getenetic.algorithm.service.GeneticAlgorithmService;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

import static ru.itis.timetable.model.RequestStatus.TO_DECIDE;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeneticAlgorithm {
    private final GeneticAlgorithmService service;
    private final BookingRequestRepository requestRepository;
    private final AuditoryRepository auditoryRepository;


    private static final boolean verbose = true;
    public static final Random rn = new Random();

    public Individual train() {
        var maxGeneration = 200;
        var popSize = 100;

        var auditories = auditoryRepository.findAll()
                .stream()
                .map(Auditory::getId)
                .collect(Collectors.toList());
        var requests = requestRepository.findAllByStatus(TO_DECIDE)
                .stream()
                .map(BookingRequest::getId)
                .collect(Collectors.toList());

        Individual fittest = null;
        Individual secondFittest;
        int generationCount = 0;
        var numberOfGenes = requests.size();

        //Initialize population

        var population = service.generatePopulation(popSize, auditories, requests);

        log.info("Population of " + population.getPopSize() + " individual(s).");

        //Calculate fitness of each individual
        Arrays.stream(population.getIndividuals()).forEach(service::calcFitness);

        log.info("\nGeneration: " + generationCount + " Fittest: " + population.getFittestScore());
        //show genetic pool
        showGeneticPool(population.getIndividuals());

        //While population gets an individual with maximum fitness
        while (population.getFittestScore() < numberOfGenes || generationCount < maxGeneration) {
            ++generationCount;

            //Do selection
            fittest = population.selectFittest();

            secondFittest = population.selectSecondFittest();

            //Do crossover
            int crossOverPoint = rn.nextInt(numberOfGenes);

            //Swap values among parents
            for (int i = 0; i < crossOverPoint; i++) {
                Gen temp = fittest.getGenes()[i].clone();
                fittest.getGenes()[i] = secondFittest.getGenes()[i];
                secondFittest.getGenes()[i] = temp;

            }

            //С шансом 10% производим мутацию в двух наиболее приспособленных
            if (rn.nextInt() % 10 < 2) {
                int mutationPoint = rn.nextInt(numberOfGenes);

                fittest.getGenes()[mutationPoint].auditory = auditories.get(rn.nextInt(auditories.size()));

                mutationPoint = rn.nextInt(numberOfGenes);

                secondFittest.getGenes()[mutationPoint].auditory = auditories.get(rn.nextInt(auditories.size()));
            }

            //Add fittest offspring to population
            service.calcFitness(fittest);
            service.calcFitness(secondFittest);

            //Get index of least fit individual
            int leastFittestIndex = population.getLeastFittestIndex();

            //Replace least fittest individual from most fittest offspring
            population.getIndividuals()[leastFittestIndex] =
                    fittest.getFitness() > secondFittest.getFitness()
                            ? fittest
                            : secondFittest;

            //Calculate new fitness value
            Arrays.stream(population.getIndividuals()).forEach(service::calcFitness);


            log.info("\nGeneration: " + generationCount + " Fittest score: " + population.getFittestScore());

            //show genetic pool
            showGeneticPool(population.getIndividuals());
        }

        log.info("\nSolution found in generation " + generationCount);
        log.info("Index of winner Individual: " + population.getFittestIndex());
        log.info("Fitness: " + population.getFittestScore());
        System.out.print("Genes: ");
        for (int i = 0; i < numberOfGenes; i++) {
            System.out.print(population.selectFittest().getGenes()[i]);
        }

        log.info("");

        return fittest;
    }

    //show genetic state of the population pool
    static void showGeneticPool(Individual[] individuals) {
        if (!verbose) return;
        log.info("==Genetic Pool==");
        int increment = 0;
        for (Individual individual : individuals) {
            log.info("> Individual  " + increment + " | " + (individual.toString()) + " |");
            increment++;
        }
        log.info("================");
    }

}
