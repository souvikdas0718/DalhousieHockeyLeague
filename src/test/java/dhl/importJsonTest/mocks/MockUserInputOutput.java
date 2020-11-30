package dhl.importJsonTest.mocks;

import dhl.inputOutput.ui.interfaces.IUserInputOutput;

public class MockUserInputOutput extends IUserInputOutput {

    String mockOutput;
    private static IUserInputOutput uniqueInstance = null;

    private MockUserInputOutput(){

    }

    public static IUserInputOutput instance(){
        if (null == uniqueInstance){
            uniqueInstance = new MockUserInputOutput();
        }
        return uniqueInstance;
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public String getUserInput() {
        return mockOutput;
    }

    public void setMockOutput(String s) {
        this.mockOutput = s;
    }

}