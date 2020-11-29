package dhl.inputOutput.ui;

import java.util.Scanner;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;

public class UserInputOutput extends IUserInputOutput {
    Scanner sc = new Scanner(System.in);

    public void printMessage(String message) {
        System.out.println(message);
    }

    public String getUserInput() {
        String userInput = sc.nextLine();
        return userInput;
    }
}
