package dhl.inputOutput.ui.interfaces;

import dhl.inputOutput.ui.UserInputOutput;

public abstract class IUserInputOutput {
    private static IUserInputOutput uniqueInstance = null;

    protected IUserInputOutput() {

    }

    public static IUserInputOutput getInstance() {
        if (null == uniqueInstance) {
            uniqueInstance = new UserInputOutput();
        }
        return uniqueInstance;
    }

    public static void setFactory(IUserInputOutput factory) {
        uniqueInstance = factory;
    }

    public abstract void printMessage(String message);

    public abstract String getUserInput();
}
