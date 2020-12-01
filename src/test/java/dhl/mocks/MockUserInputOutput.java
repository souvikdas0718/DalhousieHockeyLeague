package dhl.mocks;

import dhl.inputOutput.ui.interfaces.IUserInputOutput;

public class MockUserInputOutput extends IUserInputOutput {

    String mockOutput;

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
