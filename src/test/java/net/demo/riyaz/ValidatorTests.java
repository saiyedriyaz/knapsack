package net.demo.riyaz;

import net.demo.riyaz.dto.Consignment;
import net.demo.riyaz.exception.ValidationException;
import net.demo.riyaz.packer.DataPreparator;
import net.demo.riyaz.validator.RequestValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ValidatorTests {

    @Test
    public void invalidConsignment() {
        ValidationException thrown =
                Assertions.assertThrows(ValidationException.class,
                        () -> RequestValidator.validate(new Consignment(0, null)),
                        "Expected ValidationException");
    }

    @Test
    public void invalidConsignmentCapacity() {
        ValidationException thrown =
                Assertions.assertThrows(ValidationException.class,
                        () -> RequestValidator.validate(DataPreparator.populateConsignments("175 : (1,85.31,$29) (2,14.55,$74) " +
                                "(3,3.98,$16) (4,26.24,$55) (5,63.69,$52) (6,76.25,$75) " +
                                "(7,60.02,$74) (8,93.18,$35) (9,89.95,$78)" +
                                "(10,60.02,$74) (11,93.18,$35) (12,89.95,$78)" +
                                "(13,60.02,$74) (14,93.18,$35) (15,89.95,$78)" +
                                "(16,60.02,$74)" +
                                "")),
                        "Expected ValidationException");
        Assertions.assertTrue(thrown.getValidationMessages().size() == 2, "Expecting 2 validation messages");
    }

    @Test
    public void invalidConsignmentItems() {
        ValidationException thrown =
                Assertions.assertThrows(ValidationException.class,
                        () -> RequestValidator.validate(DataPreparator.populateConsignments("15 : (1,185.31,$29) (2,14.55,$174) " +
                                "")),
                        "Expected ValidationException");
        Assertions.assertTrue(thrown.getValidationMessages().size() == 2, "Expecting 2 validation messages");
    }
}
