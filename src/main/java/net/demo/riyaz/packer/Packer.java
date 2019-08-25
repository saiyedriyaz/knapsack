package net.demo.riyaz.packer;

import net.demo.riyaz.dto.Consignment;
import net.demo.riyaz.dto.Item;
import net.demo.riyaz.enums.SolverStrategy;
import net.demo.riyaz.exception.APIException;
import net.demo.riyaz.exception.ValidationException;
import net.demo.riyaz.factory.SolverStrategyFactory;
import net.demo.riyaz.solver.Solver;
import net.demo.riyaz.validator.RequestValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class solves the classic knapsack problem using the the specified solver strategy (algorithm)
 * @author Riyaz Saiyed
 */
public class Packer {

    private Solver solver;

    public void setSolver(Solver s) {
        solver = s;
    }

    /**
     * This methods solves the packing problem which is specified in an input file.
     *
     * @param filePath - path to the file which contains the problem statement
     * @return a string containing the list of items included in the solution
     */
    public String pack(String filePath) throws APIException {

        // In case strategy is not initialised, use default which is dynamic.
        if (solver == null) {
            solver = SolverStrategyFactory.getSolverStrategy(SolverStrategy.DYNAMIC);
        }

        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            List<Consignment> consignments = stream.map(line -> {
                // catch the checked APIException and wrap in runtime exception to
                // re throw from lambda function.
                try {
                    return DataPreparator.populateConsignments(line);
                } catch (APIException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList());

            List<String> solution = consignments.stream().map(consignment -> {
                // catch the checked ValidationException and wrap in runtime exception to
                // re throw from lambda function.
                try {
                    RequestValidator.validate(consignment);
                } catch (ValidationException ve) {
                    throw new RuntimeException(ve);
                }

                Consignment accepted = solver.solve(consignment);
                String names = "-";
                if (!accepted.getItems().isEmpty()) {
                    names = accepted.getItems().stream()
                            .sorted(Comparator.comparingInt(Item::getIndex))
                            .map(p -> p.getIndex().toString())
                            .collect(Collectors.joining(","));
                }
                return names;
            }).collect(Collectors.toList());

            return solution.stream().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new APIException("Invalid File Path");
        }
    }
}
