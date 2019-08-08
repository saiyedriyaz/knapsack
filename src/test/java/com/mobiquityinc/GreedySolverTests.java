package com.mobiquityinc;

import com.mobiquityinc.dto.Consignment;
import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.packer.DataPreparator;
import com.mobiquityinc.solver.impl.GreedySolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 */
public class GreedySolverTests {

    @Test
    public void noItemAccepted() throws APIException {
        Consignment solution = new GreedySolver().solve(DataPreparator.populateConsignments("8 : (1,15.3,$34)"));
        Assertions.assertTrue(solution.getItems().isEmpty(), "List of accepted items shoud be empty");
    }

    @Test
    public void noItemProvided() throws APIException {
        Consignment solution = new GreedySolver().solve(DataPreparator.populateConsignments("55 : "));
        Assertions.assertTrue(solution.getItems().isEmpty(), "List of accepted items shoud be empty");
    }

    @Test
    public void singleItemAccepted() throws APIException {
        Consignment solution = new GreedySolver().solve((DataPreparator.populateConsignments("81 : (1,53.38,$45) (2,88.62,$98) (3,78.48,$3) (4,72.30,$76) (5,30.18,$9) (6,46.34,$48)")));
        Assertions.assertNotNull(solution.getItems(), "Expecting Accepted Items");
        Assertions.assertEquals(Integer.valueOf(4), solution.getItems().get(0).getIndex());
    }

    @Test
    public void multipleItemsAccepted() throws APIException {
        Consignment solution = new GreedySolver().solve(DataPreparator.populateConsignments("56 : (1,90.72,$13) (2,33.80,$40) (3,43.15,$10) (4,37.97,$16) (5,46.81,$36) (6,48.77,$79) (7,81.80,$45) (8,19.36,$79) (9,6.76,$64)"));
        Assertions.assertNotNull(solution.getItems(), "Expecting Accepted Items");
        Assertions.assertEquals(1, solution.getItems().stream().filter(c -> c.getIndex().equals(6)).count(), "Expecting Item with index 6");
        Assertions.assertEquals(1, solution.getItems().stream().filter(c -> c.getIndex().equals(9)).count(), "Expecting Item with index 9");
    }

    @Test
    public void invalidInput() throws APIException {
        APIException thrown =
                Assertions.assertThrows(APIException.class,
                        () -> new GreedySolver().solve(DataPreparator.populateConsignments("75 : (1.85.31,$29) (2,14.55,$74) (3,3.98,$16) (4,26.24,$55) (5,63.69,$52) (6,76.25,$75) (7,60.02,$74) (8,93.18,$35) (9,89.95,$78)")),
                        "Expected APIException");
        Assertions.assertTrue(thrown.getMessage().contains("Invalid Input"));
    }
}
