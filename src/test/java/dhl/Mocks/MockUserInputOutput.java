package dhl.Mocks;

import dhl.inputOutput.ui.IUserInputOutput;

public class MockUserInputOutput implements IUserInputOutput {

    String mockOutput;

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public String getUserInput() {
        return mockOutput;
    }

    public void setMockOutput(String s) {
        this.mockOutput = s;
    }

}
