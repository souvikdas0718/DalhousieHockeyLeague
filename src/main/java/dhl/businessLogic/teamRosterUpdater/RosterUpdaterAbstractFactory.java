package dhl.businessLogic.teamRosterUpdater;

import dhl.businessLogic.teamRosterUpdater.interfaces.ITeamRosterUpdater;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;

public abstract class RosterUpdaterAbstractFactory {

    private static RosterUpdaterAbstractFactory uniqueInstance = null;

    protected RosterUpdaterAbstractFactory() {

    }

    public static RosterUpdaterAbstractFactory instance() {
        if (null == uniqueInstance) {
            uniqueInstance = new RosterUpdater();
        }
        return uniqueInstance;
    }

    public static void setFactory(RosterUpdaterAbstractFactory factory) {
        uniqueInstance = factory;
    }

    public abstract ITeamRosterUpdater createAiTeamRosterUpdater();
    public abstract ITeamRosterUpdater createUpdateUserTeamRoster(IUserInputOutput ioObject);
}
