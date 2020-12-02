package dhl.inputOutput.importJson;

import dhl.inputOutput.importJson.interfaces.IJsonFilePath;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class JsonFilePath implements IJsonFilePath {

    IUserInputOutput ioObject;
    private static final Logger logger = LogManager.getLogger(JsonFilePath.class);

    public JsonFilePath() {
        ioObject = IUserInputOutput.getInstance();
    }

    public String getFilePath() {
        logger.info("Getting Json File path from User");
        String path = null;
        while (path == null) {
            ioObject.printMessage("Enter JSON File Path or Enter Exit if u want to exit: ");
            String inputPath = ioObject.getUserInput();

            if (inputPath.equals("Exit")) {
                logger.info("User Exiting the system");
                System.exit(0);
            } else {
                if (validatePath(inputPath)) {
                    ioObject.printMessage("File Path is valid");
                    logger.debug("Valid file path given");
                    path = inputPath;
                } else {
                    ioObject.printMessage("Invalid Json Path");
                    logger.debug("Invalid file path given " + path);
                }
            }
        }
        return path;
    }

    public boolean validatePath(String inputPath) {
        Path path;
        logger.info("Checking file Path");
        if (inputPath.length() > 0) {
            path = Paths.get(inputPath);
            if (Files.exists(path)) {
                return true;
            }
        }
        return false;
    }

}

