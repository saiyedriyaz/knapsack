package net.demo.riyaz.packer;

import net.demo.riyaz.dto.Consignment;
import net.demo.riyaz.dto.Item;
import net.demo.riyaz.exception.APIException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is a utility class to prepare the Consignment DTO objects from the well structured input text.
 * The the structure of the text must be as following:
 * <p>
 * {capacity} : ( {index},{weight in decimal},{currency symbol wth cost} )
 * <p>
 * Example 1 = 75 : (1,85.31,€29)
 * Example 2 = 75 : (1,85.31,€29) (2,14.55,€74)
 *
 * @author Riyaz Saiyed
 */
public class DataPreparator {

    private static final Logger LOGGER = Logger.getLogger(DataPreparator.class.getName());

    private static final String GROUP_REGEX = "\\((.*?)\\)";

    private DataPreparator() {

    }

    /**
     * @param line input text with knapsack problem statement e.g. "75 : (1,85.31,€29) (2,14.55,€74)"
     * @return the consignment data transfer object created and populated based on input
     * @throws APIException in case invalid text input is specified.
     */
    public static Consignment populateConsignments(String line) throws APIException {
        String[] values = line.split(":");
        Pattern pattern = Pattern.compile(GROUP_REGEX);
        Matcher matcher = pattern.matcher(values[1]);
        List<Item> items = new ArrayList<>();
        while (matcher.find()) {
            try {

                String group = matcher.group(1);
                String[] itemAttributes = group.split(",");
                Item item = new Item(
                        Integer.valueOf(itemAttributes[0]),
                        getNextInt(Double.valueOf(itemAttributes[1])),
                        itemAttributes[2].substring(0, 1),
                        Integer.valueOf(itemAttributes[2].substring(1)));
                items.add(item);
            } catch (IllegalArgumentException e) {
                LOGGER.log(Level.SEVERE, "Exception while creating consignment. message={0}", e.getMessage());
                throw new APIException("Invalid Input parameters.");
            }
        }
        return new Consignment(Integer.valueOf(values[0].trim()), items);
    }

    private static Integer getNextInt(Double weight) {
        return weight.intValue();
    }
}
