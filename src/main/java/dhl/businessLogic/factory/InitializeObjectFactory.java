package dhl.businessLogic.factory;

import dhl.businessLogic.leagueModel.Conference;
import dhl.businessLogic.leagueModel.Division;
import dhl.businessLogic.leagueModel.Player;
import dhl.businessLogic.leagueModel.Team;
import dhl.businessLogic.leagueModel.interfaceModel.IConference;
import dhl.businessLogic.leagueModel.interfaceModel.IDivision;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;

public class InitializeObjectFactory extends InitializeObjectsAbstractFactory {
    @Override
    public ITeam createTeam() {
        return new Team();
    }

    @Override
    public IPlayer createPlayer() {
        return new Player();
    }

    @Override
    public IDivision createDivision() {
        return new Division();
    }

    @Override
    public IConference createConference() {
        return new Conference();
    }

}
