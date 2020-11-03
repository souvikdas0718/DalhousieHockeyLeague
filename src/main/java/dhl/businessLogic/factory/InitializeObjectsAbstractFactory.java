package dhl.businessLogic.factory;

import dhl.businessLogic.leagueModel.interfaceModel.IConference;
import dhl.businessLogic.leagueModel.interfaceModel.IDivision;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;

public abstract class InitializeObjectsAbstractFactory {
    public abstract ITeam createTeam();

    public abstract IPlayer createPlayer();

    public abstract IDivision createDivision();

    public abstract IConference createConference();

}
