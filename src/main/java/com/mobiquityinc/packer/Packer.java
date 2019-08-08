package com.mobiquityinc.packer;

import com.mobiquityinc.enums.SolverStrategy;
import com.mobiquityinc.dto.Consignment;
import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.exception.ValidationException;
import com.mobiquityinc.factory.SolverStrategyFactory;
import com.mobiquityinc.solver.Solver;
import com.mobiquityinc.validator.RequestValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class solves the classic knapsack problem using the the specified solver strategy (algorithm)
 * @author Riyaz Saiyed
 */
public class Packer {

    private final static Logger LOGGER = Logger.getLogger(Packer.class.getName());

    private static Solver solver;

    public static void setSolver(Solver s) {
        solver = s;
    }

    /**
     * This methods solves the packing problem which is specified in an input file.
     *
     * @param filePath - path to the file which contains the problem statement
     * @return a string containing the list of items included in the solution
     */
    public static String pack(String filePath) throws APIException {

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
                            .sorted((c1, c2) -> c1.getIndex() - c2.getIndex())
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
