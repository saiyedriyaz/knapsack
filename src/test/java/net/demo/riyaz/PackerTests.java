package net.demo.riyaz;

import net.demo.riyaz.enums.SolverStrategy;
import net.demo.riyaz.exception.APIException;
import net.demo.riyaz.factory.SolverStrategyFactory;
import net.demo.riyaz.packer.Packer;
import net.demo.riyaz.solver.Solver;
import net.demo.riyaz.solver.impl.DynamicSolver;
import net.demo.riyaz.solver.impl.GreedySolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 */
public class PackerTests {

    @Test
    public void testFromFileWithValidContent() throws APIException {
        String result = new Packer().pack("./src/test/resources/input.txt");
        Assertions.assertEquals("4\n-\n2,7\n8,9", result);
    }

    @Test
    public void testFromFileWithInValidContent() {
        RuntimeException thrown =
                Assertions.assertThrows(RuntimeException.class,
                        () -> new Packer().pack("./src/test/resources/invalidInput.txt"),
                        "Expected RuntimeException due to APIException thrown from DataPreparator");
    }

    @Test
    public void testFromFileWithInValidPath() throws APIException {
        APIException thrown =
                Assertions.assertThrows(APIException.class,
                        () -> new Packer().pack("./no-such-file.txt"),
                        "Expected APIException due to file does not exists");
    }

    @Test
    void testGetStrategyGreedy() throws APIException {
        SolverStrategy strategy = SolverStrategy.getStrategy("greedy");
        Solver solver = SolverStrategyFactory.getSolverStrategy(strategy);
        Assertions.assertTrue(solver instanceof GreedySolver);
    }

    @Test
    void testGetStrategyDynamic() throws APIException {
        SolverStrategy strategy = SolverStrategy.getStrategy("dynamic");
        Solver solver = SolverStrategyFactory.getSolverStrategy(strategy);
        Assertions.assertTrue(solver instanceof DynamicSolver);
    }

    @Test
    void testGetStrategyException() throws APIException {
        SolverStrategy strategy = SolverStrategy.getStrategy("null");
        APIException thrown =
                Assertions.assertThrows(APIException.class,
                        () -> SolverStrategyFactory.getSolverStrategy(strategy),
                        "Expected APIException due to null strategy.");
    }
}
