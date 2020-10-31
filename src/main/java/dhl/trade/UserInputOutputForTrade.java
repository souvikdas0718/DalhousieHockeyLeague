package dhl.trade;

import dhl.InputOutput.UI.IUserInputOutput;

public class UserInputOutputForTrade implements IUserInputOutput {

    String mockOutput;

    @Override
    public void printMessage(String message) {

    }

    @Override
    public String getUserInput() {
        return mockOutput;
    }

    public void setMockOutput(String s){
        this.mockOutput = s;
    }

}
