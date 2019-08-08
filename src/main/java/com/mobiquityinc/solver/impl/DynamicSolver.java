package com.mobiquityinc.solver.impl;

import com.mobiquityinc.dto.Consignment;
import com.mobiquityinc.dto.Item;
import com.mobiquityinc.solver.Solver;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * This class solves the 1/0 knapsack problem with dynamic programming method. Dynamic programming requires an optimal substructure
 * and overlapping sub problems. 1 represent included item (whole) while 0 represent not included item.
 *
 * <p>
 * Algorithm:
 * If  (Item weight > capacity),
 * Then We cannot pick the item  In this case and max value will remain same as value we got in previous step without working on this item.
 * <p>
 * If  (Item weight <=  capacity),
 * Here we have 2 options, either we pick the item or we don’t, based on the value we get by these two options
 * If value of picking the item is more, then we pick else we leave the item and value will be same as of our previous step
 * <p>
 * Steps :
 * 1.   Create a matrix representing all subsets of the items (the solution space) with rows representing items
 * and columns representing the bag's remaining weight capacity
 * 2.   Loop through the matrix and calculate the value that can be obtained by each combination of items at each stage of the bag's capacity.
 * 3.   Examine the completed matrix to determine which items to add to the bag in order to produce the maximum possible worth for the bag in total
 *
 * @author Riyaz Saiyed
 */
public class DynamicSolver implements Solver {

    private final static Logger LOGGER = Logger.getLogger(DynamicSolver.class.getName());

    /**
     * Method which solve the knapsack problem with Dynamic Programming approach for the Optimal Solution.
     * The time complexity of this solution is O(M*N) and space complexity of O(M*N) where M is possible items and N is the capacity of bag
     *
     * @param consignment a consignment containing the bag limit and a list of available items
     * @return a consignment containing the list of items which can be included in the solution
     */
    @Override
    public Consignment solve(Consignment consignment) {
        // sort item in descending order of of its cost so that the item with lower weight will be ranked higher that the
        // item with higher value in case of same cost of both the items.
        Item[] items = consignment.getItems().stream().sorted((o1, o2) -> o1.getWeight().compareTo(o2.getWeight()))
                .collect(Collectors.toList())
                .toArray(new Item[consignment.getItems().size()]);

        int capacity = consignment.getWeight().intValue();

        int NB_ITEMS = items.length;

        // use a 2D matrix to store the highest cost at each n-th item
        int[][] matrix = new int[NB_ITEMS + 1][capacity + 1];

        // first row and first column is initialized to 0
        for (int i = 0; i <= capacity; i++) {
            matrix[0][i] = 0;
        }

        // for each iteam
        for (int i = 1; i <= NB_ITEMS; i++) {

            // for each capacity
            for (int j = 0; j <= capacity; j++) {
                if (items[i - 1].getWeight() > j) {
                    matrix[i][j] = matrix[i - 1][j];
                } else {
                    // we find the maximum cost/value at this point and store in the matrix
                    matrix[i][j] = Math.max(matrix[i - 1][j], matrix[i - 1][j - items[i - 1].getWeight()]
                            + items[i - 1].getCost());
                }
            }
        }

        int res = matrix[NB_ITEMS][capacity];
        int w = capacity;
        List<Item> itemsSolution = new ArrayList<>();

        for (int i = NB_ITEMS; i > 0 && res > 0; i--) {
            if (res != matrix[i - 1][w]) {
                itemsSolution.add(items[i - 1]);
                // we remove items cost and weight
                res -= items[i - 1].getCost();
                w -= items[i - 1].getWeight();
            }
        }
        Integer totalAcceptedWeight = itemsSolution.stream().mapToInt(mItem -> mItem.getWeight()).sum();
        Integer totalAcceptedCost = itemsSolution.stream().mapToInt(mItem -> mItem.getCost()).sum();

        LOGGER.info("Capacity=" + consignment.getWeight() + ", Accepted Weight=" + totalAcceptedWeight + ",Cost of Accepted Items=" + totalAcceptedCost);

        return new Consignment(totalAcceptedWeight, itemsSolution);
    }
}
