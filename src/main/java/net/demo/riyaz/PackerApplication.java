package net.demo.riyaz;

import net.demo.riyaz.enums.SolverStrategy;
import net.demo.riyaz.exception.APIException;
import net.demo.riyaz.exception.ValidationException;
import net.demo.riyaz.factory.SolverStrategyFactory;
import net.demo.riyaz.packer.Packer;

import java.util.logging.Logger;

/**
 * Entry point for the Knapsack Packer Application.
 * @author Riyaz Saiyed
 */
public class PackerApplication {

    private final static Logger LOGGER = Logger.getLogger(PackerApplication.class.getName());

    public static void main(String[] args) {
        try {
            if (args != null && args.length > 0) {
                if (args.length > 1) {
                    Packer.setSolver(SolverStrategyFactory.getSolverStrategy(SolverStrategy.getStrategy(args[1])));
                    String solution = Packer.pack(args[0]);
                    LOGGER.info(solution);
                } else {
                    String solution = Packer.pack(args[0]);
                    LOGGER.info(solution);
                }
            } else {
                StringBuilder messageBuilder = new StringBuilder("\n");
                messageBuilder.append("Invalid Arguments supplied. Fully qualified file path is required. \n");
                messageBuilder.append("USAGE: java -jar implementation-1.0-SNAPSHOT.jar <FILE PATH>  <optional-name-of-strategy>\n ");
                messageBuilder.append("By default Dynamic Programming Algorithm is used. Alternatively Greedy method also can be used. \n");
                messageBuilder.append("Exampple: java -jar implementation-1.0-SNAPSHOT.jar <FILE PATH> greedy \n");

                LOGGER.info(messageBuilder.toString());
            }
        } catch (APIException apiException) {
            LOGGER.severe(apiException.getMessage());
        } catch (RuntimeException re) {
            LOGGER.severe(re.getMessage());

            if (re.getCause() instanceof ValidationException) {
                ValidationException validationException = (ValidationException) re.getCause();
                if (validationException.getValidationMessages() != null) {
                    for (String message : validationException.getValidationMessages()) {
                        LOGGER.severe(message);
                    }
                }
            }
        }
    }
}
