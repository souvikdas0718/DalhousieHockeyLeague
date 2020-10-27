package dhl.simulationStateMachine.States;

import java.util.Scanner;

public class UserInterface {
    Scanner sc = new Scanner(System.in);

    public void printMessage(String message){
        System.out.println(message);
    }

    public String getUserInput(){
        return sc.nextLine();
    }
}
