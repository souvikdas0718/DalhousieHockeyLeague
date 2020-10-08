package dhl.leagueModelTests;


import dhl.leagueModel.*;
import dhl.leagueModel.interfaceModel.*;
import dhl.leagueModelData.ILeagueObjectModelData;

import java.util.ArrayList;

public class MockDatabase implements ILeagueObjectModelData {

    @Override
    public void insertLeagueModel(ILeagueObjectModel leagueObjectModel) {

    }

    @Override
    public ILeagueObjectModel loadLeagueModel(String leagueName, String teamName) {
        ArrayList<IPlayer> playersList=new ArrayList<>();
        playersList.add(new Player("Henry","forward",false));
        playersList.add(new Player("Max","goalie",true));
        ITeam team = new Team("Ontario","Mathew","henry",playersList);
        ArrayList<ITeam> teamArrayList=new ArrayList<>();
        teamArrayList.add(team);
        IDivision division = new Division("Atlantic",teamArrayList);
        ArrayList<IDivision> divisionsList=new ArrayList<>();
        divisionsList.add(division);
        IConference conference=new Conference("Western",divisionsList);
        ArrayList<IConference> conferences= new ArrayList<>();
        conferences.add(conference);
        ArrayList<IPlayer> freeAgentsList=new ArrayList<>();
        ILeagueObjectModel leagueModel=new LeagueObjectModel("Dhl",conferences,freeAgentsList);
        return leagueModel;
    }

    @Override
    public boolean checkIfLeagueAlreadyExists(String leagueName) {
        return false;
    }

    @Override
    public boolean checkIfTeamAlreadyExists(String teamName, String divisionName) {
        return false;
    }

}
