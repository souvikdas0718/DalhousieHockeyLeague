package dhl.InputOutput.UI;

import java.util.Scanner;

public class UserInputOutput implements IUserInputOutput {
    Scanner sc = new Scanner(System.in);

    public void printMessage(String message) {
        System.out.println(message);
    }

    public String getUserInput() {
        String userInput = sc.nextLine();
        return userInput;
    }
}
