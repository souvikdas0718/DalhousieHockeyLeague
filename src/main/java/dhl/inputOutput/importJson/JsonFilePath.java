package dhl.inputOutput.importJson;

import dhl.inputOutput.importJson.interfaces.IJsonFilePath;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class JsonFilePath implements IJsonFilePath {

    IUserInputOutput ioObject;

    public JsonFilePath(){
        ioObject = IUserInputOutput.getInstance();
    }

    public String getFilePath() {
        String path = null;
        while (path == null) {
            ioObject.printMessage("Enter JSON File Path or Enter Exit if u want to exit: ");
            String inputPath = ioObject.getUserInput();

            if (inputPath.equals("Exit")){
                System.exit(0);
            }
            else {
                if (validatePath(inputPath)) {
                    ioObject.printMessage("File Path is valid");
                    path = inputPath;
                } else {
                    ioObject.printMessage("Invalid Json Path");
                }
            }
        }
        return path;
    }

    public boolean validatePath(String inputPath) {
        Path path;
        if (inputPath.length() > 0) {
            path = Paths.get(inputPath);
            if (Files.exists(path)) {
                return true;
            }
        }
        return false;
    }

}

