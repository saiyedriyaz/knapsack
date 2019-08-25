package net.demo.riyaz.validator;

import net.demo.riyaz.dto.Consignment;
import net.demo.riyaz.dto.Item;
import net.demo.riyaz.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;

/**
 * The request validator class to validate the packing request.
 * This class can be optimised to return the error coded which could be mapped with error messages.
 * @author Riyaz Saiyed
 */
public class RequestValidator {

    private RequestValidator() {}

    /**
     * The method which validate the consignment request
     *
     * @param consignment the request object
     * @throws ValidationException in case one or more request parameters are invalid
     */
    public static void validate(Consignment consignment) throws ValidationException {
        String summaryMessage = "Invalid Consignment Request.";

        if (consignment == null || consignment.getItems() == null || consignment.getItems().isEmpty()) {
            throw new ValidationException(summaryMessage, null);
        }

        List<String> validationIssues = new ArrayList<>();
        if (consignment.getWeight() > 100) {
            validationIssues.add("Max weight that a package can take should be less or equal to 100.");
        }

        if (consignment.getItems().size() > 15) {
            validationIssues.add("Max consignment items per request should be less or equal to 15.");
        }

        for (Item item : consignment.getItems()) {
            if (item.getWeight() > 100) {
                validationIssues.add("Max weight of an item is less or equal to 100 for item with index " + item.getIndex() + ".");
            }
            if (item.getCost() > 100) {
                validationIssues.add("Max cost of an item is less or equal to 100 for item with index " + item.getIndex() + ".");
            }
        }

        if (!validationIssues.isEmpty()) {
            throw new ValidationException(summaryMessage, validationIssues);
        }
    }
}
