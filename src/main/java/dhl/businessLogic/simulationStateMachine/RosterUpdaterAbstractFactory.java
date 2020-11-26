package dhl.businessLogic.simulationStateMachine;

import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.factory.LeagueModelFactory;
import dhl.businessLogic.leagueModel.interfaceModel.IValidation;
import dhl.businessLogic.simulationStateMachine.interfaces.ITeamRosterUpdater;
import dhl.inputOutput.ui.IUserInputOutput;

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
