package dhl.importJsonTest;

import dhl.InputOutput.importJson.CheckInputFileFormat;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CheckInputFileFormatTest {

    CheckInputFileFormat testClassObject;

    @BeforeEach
    public void initObject() {
        testClassObject = new CheckInputFileFormat();
    }

    @Test
    public void isCorrectFormatedTest() throws Exception {
        String correctJson = "{ \"Name\" : \"correctJsonFile\" , \"secondKey\": \" SecondValue \" }";
        String wrongJson = "{ \"Name\"  \"missing : \" , \"secondKey\": \" SecondValue \" }";

        Assert.assertTrue(testClassObject.isCorrectFormated(correctJson));
        Assertions.assertThrows(Exception.class, () -> {
            testClassObject.isCorrectFormated(wrongJson);
        });
    }

}
