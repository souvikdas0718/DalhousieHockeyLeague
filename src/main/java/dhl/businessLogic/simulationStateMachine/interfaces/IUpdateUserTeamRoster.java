package dhl.businessLogic.simulationStateMachine.interfaces;

import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.factory.LeagueModelFactory;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.UpdateUserTeamRoster;
import dhl.inputOutput.ui.IUserInputOutput;
import dhl.inputOutput.ui.UserInputOutput;

public abstract class IUpdateUserTeamRoster {

    private static IUpdateUserTeamRoster uniqueInstance = null;

    protected IUpdateUserTeamRoster() {

    }

    public static IUpdateUserTeamRoster instance(IUserInputOutput ioObject) {
        if (null == uniqueInstance)
        {
            uniqueInstance = new UpdateUserTeamRoster(ioObject);
        }
        return uniqueInstance;
    }

    public static void setFactory(IUpdateUserTeamRoster factory) {
        uniqueInstance = factory;
    }

    public abstract void dropPlayer(String playerPosition, ITeam team, ILeagueObjectModel league);

    public abstract void addPlayer(String playerPosition, ITeam team, ILeagueObjectModel league);
}
