package dhl.Mocks;

import dhl.leagueModel.*;
import dhl.leagueModel.interfaceModel.*;

import java.util.ArrayList;

public class LeagueObjectModelMocks {

    public IPlayerStatistics getPlayerStatistics(){
        IPlayerStatistics playerStatistics= new PlayerStatistics( 25,10,10,10,0);
        return playerStatistics;
    }

    public IPlayer getPlayerObjectMock(){
        IPlayer playerMock = new Player(
                "Mock Player",
                "defense",
                false,
                getPlayerStatistics()
        );
        return playerMock;
    }

    public ArrayList<IPlayer> getPlayerArrayMock(){
        ArrayList<IPlayer> playerArrayListMock = new ArrayList<>();

        playerArrayListMock.add(new Player(
                "Mock Player",
                "defense",
                false,
                getPlayerStatistics()
        ));
        playerArrayListMock.add(new Player(
                "Mock Player2",
                "forward",
                true,
                getPlayerStatistics()
        ));
        return playerArrayListMock;
    }

    public ITeam getTeamObjectMock(){
        ICoach headCoach = new Coach("Todd McLellan",0.1,0.5,1.0,0.2);
        return new Team("Mock Team", "Mock Manager", headCoach, getPlayerArrayMock() );
    }

    public ArrayList<ITeam> getTeamArrayMock(){
        ArrayList<ITeam> teamArrayListMock = new ArrayList<>();
        ICoach headCoachOne = new Coach("Todd McLellan",0.1,0.5,1.0,0.2);
        ICoach headCoachTwo = new Coach("Todd McLellan",0.1,0.5,1.0,0.2);
        teamArrayListMock.add(new Team("Mock Team2" , "mock Manager2", headCoachTwo, getPlayerArrayMock()));
        teamArrayListMock.add(new Team("Mock Team1" , "mock Manager1", headCoachOne, getPlayerArrayMock()));

        return teamArrayListMock;
    }

    public IDivision getDivisionObjectMock(){
        return new Division("Mock Division" , getTeamArrayMock());
    }

    public ArrayList<IDivision> getDivisionArrayMock(){
        ArrayList<IDivision> divisionArrayListMock = new ArrayList<>();

        divisionArrayListMock.add(new Division("Mock Division2" , getTeamArrayMock()));
        divisionArrayListMock.add(new Division("Mock Division1" , getTeamArrayMock()));

        return divisionArrayListMock;
    }

    public IConference getConferenceObjectMock(){
        return new Conference( "Mock Conference" , getDivisionArrayMock());
    }

    public ArrayList<IConference> getConferenceArrayMock(){
        ArrayList<IConference> conferenceArrayListMock= new ArrayList<>();

        conferenceArrayListMock.add(new Conference("Mock Conference1" , getDivisionArrayMock()));
        conferenceArrayListMock.add(new Conference("Mock Conference2" , getDivisionArrayMock()));

        return conferenceArrayListMock;
    }

    public IConference getConferenceTestMock(){
        ArrayList<IPlayer> playersList = new ArrayList<>();
        IPlayerStatistics playerStatistics =new PlayerStatistics(20,10,10,10,0);
        playersList.add(new Player("Henry","forward",false,playerStatistics));
        playersList.add(new Player("Max","goalie",true,playerStatistics));
        ICoach headCoach = new Coach("Todd McLellan",0.1,0.5,1.0,0.2);
        ITeam team = new Team("Ontario","Mathew",headCoach,playersList);
        ArrayList<ITeam> teamArrayList=new ArrayList<>();
        teamArrayList.add(team);
        IDivision division = new Division("Atlantic",teamArrayList);
        ArrayList<IDivision> divisionsList=new ArrayList<>();
        divisionsList.add(division);
        return new Conference("Western",divisionsList);
    }

    public ILeagueObjectModel getLeagueObjectMock(){
        ILeagueObjectModel leagueObjectMock=null;

        ArrayList<IPlayer> playersList=new ArrayList<>();
        playersList.add(new Player("Henry","forward",false,getPlayerStatistics()));
        playersList.add(new Player("Max","goalie",true,getPlayerStatistics()));
        ICoach headCoach = new Coach("Todd McLellan",0.1,0.5,1.0,0.2);
        ITeam team = new Team("Ontario","Mathew",headCoach,playersList);
        ArrayList<ITeam> teamArrayList=new ArrayList<>();
        teamArrayList.add(team);

        IDivision division = new Division("Atlantic",teamArrayList);
        ArrayList<IDivision> divisionsList=new ArrayList<>();
        divisionsList.add(division);

        IConference conference=new Conference("Western",divisionsList);
        ArrayList<IConference> conferences= new ArrayList<>();
        conferences.add(conference);

        ArrayList<IFreeAgent> freeAgentsList=new ArrayList<>();

        leagueObjectMock = new LeagueObjectModel("Dhl",conferences,freeAgentsList);
        return leagueObjectMock;
    }
}
