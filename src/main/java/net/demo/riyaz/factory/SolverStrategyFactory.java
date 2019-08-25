package net.demo.riyaz.factory;

import net.demo.riyaz.enums.SolverStrategy;
import net.demo.riyaz.exception.APIException;
import net.demo.riyaz.solver.Solver;
import net.demo.riyaz.solver.impl.DynamicSolver;
import net.demo.riyaz.solver.impl.GreedySolver;

/**
 * The strategy factory class to return the instance of suitable Strategy class to solve the packing problem.
 */
public class SolverStrategyFactory {

    private SolverStrategyFactory() {}

    /**
     * @param solverStrategy name of the strategy to solve the knapsack problem
     * @return the instance of Solver identified based on strategy name
     * @throws APIException in case suitable strategy is not identified
     */
    public static Solver getSolverStrategy(SolverStrategy solverStrategy) throws APIException {
        if (SolverStrategy.DYNAMIC == solverStrategy) {
            return new DynamicSolver();
        } else if (SolverStrategy.GREEDY == solverStrategy) {
            return new GreedySolver();
        } else {
            throw new APIException("Not supported strategy specified which is " + solverStrategy);
        }
    }
}
