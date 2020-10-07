package dhl.simulationStateMachine;

import dhl.simulationStateMachine.Interface.IJsonFilePath;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class JsonFilePath implements IJsonFilePath {
    @Override
    public String getFilePath() {
        String path = null;

        while(path== null){
            System.out.println("Enter JSON File Path or Enter Exit if u want to exit: ");
            Scanner sc = new Scanner(System.in);
            String inputPath = sc.nextLine();

            if(inputPath.equals("Exit")) System.exit(0);
            else{
                if(validatePath(inputPath)){
                    System.out.println("File Path is valid");
                    path = inputPath;
                }else{System.out.println("Invalid Json Path");}
            }
        }
        return path;
    }

    public boolean validatePath(String inputPath){
        Path path= null;
        if (inputPath.length() > 0){
            path = Paths.get(inputPath);
            if(Files.exists(path)) return true;
        }
        return false;
    }
}

