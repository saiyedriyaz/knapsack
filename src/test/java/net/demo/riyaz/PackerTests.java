package net.demo.riyaz;

import net.demo.riyaz.exception.APIException;
import net.demo.riyaz.packer.Packer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 */
public class PackerTests {

    @Test
    public void testFromFileWithValidContent() throws APIException {
        String result = Packer.pack("./src/test/resources/input.txt");
        Assertions.assertEquals("4\n-\n2,7\n8,9", result);
    }

    @Test
    public void testFromFileWithInValidContent() {
        RuntimeException thrown =
                Assertions.assertThrows(RuntimeException.class,
                        () -> Packer.pack("./src/test/resources/invalidInput.txt"),
                        "Expected RuntimeException due to APIException thrown from DataPreparator");
    }

    @Test
    public void testFromFileWithInValidPath() throws APIException {
        APIException thrown =
                Assertions.assertThrows(APIException.class,
                        () -> Packer.pack("./no-such-file.txt"),
                        "Expected APIException due to file does not exists");
    }
}
