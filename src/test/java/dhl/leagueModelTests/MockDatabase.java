package dhl.leagueModelTests;

import dhl.InputOutput.importJson.GameConfig;
import dhl.leagueModel.*;
import dhl.leagueModel.interfaceModel.*;
import dhl.database.interfaceDB.ILeagueObjectModelDB;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MockDatabase implements ILeagueObjectModelDB {
    @Override
    public void insertLeagueModel(ILeagueObjectModel leagueObjectModel) {

    }

    @Override
    public ILeagueObjectModel loadLeagueModel(String leagueName, String teamName) {
        List<IPlayer> playersList=new ArrayList<>();
        IPlayerStatistics playerStatistics =new PlayerStatistics(20,10,10,10,0);
        playersList.add(new Player("Henry","forward",false,playerStatistics));
        playersList.add(new Player("Max","goalie",true,playerStatistics));
        ICoach headCoach = new Coach("Todd McLellan",0.1,0.5,1.0,0.2);
        ITeam team = new Team("Ontario","Mathew",headCoach,playersList);
        List<ITeam> teamArrayList=new ArrayList<>();
        teamArrayList.add(team);
        IDivision division = new Division("Atlantic",teamArrayList);
        List<IDivision> divisionsList=new ArrayList<>();
        divisionsList.add(division);
        IConference conference=new Conference("Western",divisionsList);
        List<IConference> conferences= new ArrayList<>();
        conferences.add(conference);
        List<IPlayer> freeAgentsList=new ArrayList<>();
        ILeagueObjectModel leagueModel=new LeagueObjectModel("Dhl",conferences,freeAgentsList,new ArrayList<>(),new ArrayList<>(),new GameConfig(new JSONObject()));
        return leagueModel;
    }

}
