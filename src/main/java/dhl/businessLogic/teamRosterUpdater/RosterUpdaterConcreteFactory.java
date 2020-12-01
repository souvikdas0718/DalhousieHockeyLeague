package dhl.businessLogic.teamRosterUpdater;

import dhl.businessLogic.teamRosterUpdater.interfaces.ITeamRosterUpdater;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;

public class RosterUpdaterConcreteFactory extends RosterUpdaterAbstractFactory {

    public ITeamRosterUpdater createAiTeamRosterUpdater() {
        return new AiTeamRosterUpdater();
    }

    public ITeamRosterUpdater createUpdateUserTeamRoster(IUserInputOutput ioObject) {
        return new UpdateUserTeamRoster(ioObject);
    }
}
