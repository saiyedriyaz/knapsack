package net.demo.riyaz.solver.impl;

import net.demo.riyaz.dto.Consignment;
import net.demo.riyaz.dto.Item;
import net.demo.riyaz.solver.Solver;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * This class solves the 1/0 knapsack problem with dynamic greedy method. This method sorts the items in decreasing order of cost per item.
 * It then proceeds to insert them into the sack, starting with as many copies as possible of the first kind of item until there is no
 * more space available in package.
 * <p>
 * Steps :
 * 1.   Sort items by cost, in descending order.
 * 2.   Start with the highest value item. Put items into the bag until the next item on the list cannot fit.
 * 3.   Try to fill any remaining capacity with the next item on the list that can fit.
 *
 * @author Riyaz Saiyed
 */
public class GreedySolver implements Solver {

    private static final Logger LOGGER = Logger.getLogger(GreedySolver.class.getName());

    /**
     * Method which solve the knapsack problem with Greedy approach for the fastest solution.
     * The time complexity of this solution is O(N) where M is possible items.
     *
     * @param consignment a consignment containing the bag limit and a list of available items
     * @return a consignment containing the list of items which can be included in the solution
     */
    @Override
    public Consignment solve(Consignment consignment) {
        // sort item in descending order of of its cost so that the iteam with higher value will be picked up first.
        Item[] items = consignment.getItems().stream().sorted((o1, o2) -> o2.getCost().compareTo(o1.getCost()))
                .collect(Collectors.toList())
                .toArray(new Item[consignment.getItems().size()]);

        int capacity = consignment.getWeight();
        List<Item> itemsSolution = new ArrayList<>();
        for (Item item : items) {
            if (item.getWeight() < capacity) {
                itemsSolution.add(item);
                capacity = capacity - item.getWeight();
            }
        }

        Integer totalAcceptedWeight = itemsSolution.stream().mapToInt(Item::getWeight).sum();
        Integer totalAcceptedCost = itemsSolution.stream().mapToInt(Item::getCost).sum();

        LOGGER.log(Level.INFO, "Capacity= {0}, Accepted Weight= {1} ,Cost of Accepted Items= {2}",
                new Object[]{consignment.getWeight(), totalAcceptedWeight, totalAcceptedCost});

        return new Consignment(totalAcceptedWeight, itemsSolution);
    }
}
