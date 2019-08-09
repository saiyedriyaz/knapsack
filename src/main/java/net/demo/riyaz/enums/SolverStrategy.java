package net.demo.riyaz.enums;

/**
 * Supported Strategies to solve the knapsack problem.
 */
public enum SolverStrategy {

    GREEDY("Greedy"),

    DYNAMIC("Dynamic");

    final private String name;

    SolverStrategy(String name) {
        this.name = name;
    }

    public static SolverStrategy getStrategy(String strategyName) {
        for (SolverStrategy solverStrategy : SolverStrategy.values()) {
            if (solverStrategy.name.equalsIgnoreCase(strategyName)) {
                return solverStrategy;
            }
        }
        return null;
    }
}
