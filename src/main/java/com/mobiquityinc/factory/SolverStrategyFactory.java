package com.mobiquityinc.factory;

import com.mobiquityinc.enums.SolverStrategy;
import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.solver.Solver;
import com.mobiquityinc.solver.impl.DynamicSolver;
import com.mobiquityinc.solver.impl.GreedySolver;

/**
 * The strategy factory class to return the instance of suitable Strategy class to solve the packing problem.
 */
public class SolverStrategyFactory {

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
