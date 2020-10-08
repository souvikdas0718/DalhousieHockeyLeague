package dhl.leagueModel;

import dhl.leagueModel.interfaceModel.IConference;
import dhl.leagueModel.interfaceModel.IDivision;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.ITeam;

public abstract class InitializeObjectsAbstractFactory {

    public abstract ITeam createTeam();

    public abstract IPlayer createPlayer();

    public abstract IDivision createDivision();

    public abstract IConference createConference();


}
