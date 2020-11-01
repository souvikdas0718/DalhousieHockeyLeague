package dhl.Mocks;

import dhl.InputOutput.importJson.GameConfig;
import dhl.InputOutput.importJson.ImportJsonFile;
import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.leagueModel.*;
import dhl.leagueModel.interfaceModel.*;

import java.util.ArrayList;
import java.util.List;

public class LeagueObjectModelMocks {

    public IPlayerStatistics getPlayerStatistics(){
        IPlayerStatistics playerStatistics= new PlayerStatistics( 25,10,10,10,10);
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

    public List<IPlayer> getPlayerArrayMock(){
        List<IPlayer> playerArrayListMock = new ArrayList<>();

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

    public List<ITeam> getTeamArrayMock(){
        List<ITeam> teamArrayListMock = new ArrayList<>();
        ICoach headCoachOne = new Coach("Todd McLellan",0.1,0.5,1.0,0.2);
        ICoach headCoachTwo = new Coach("Todd McLellan",0.1,0.5,1.0,0.2);
        teamArrayListMock.add(new Team("Mock Team2" , "mock Manager2", headCoachTwo, getPlayerArrayMock()));
        teamArrayListMock.add(new Team("Mock Team1" , "mock Manager1", headCoachOne, getPlayerArrayMock()));

        return teamArrayListMock;
    }

    public IDivision getDivisionObjectMock(){
        return new Division("Mock Division" , getTeamArrayMock());
    }

    public List<IDivision> getDivisionArrayMock(){
        List<IDivision> divisionArrayListMock = new ArrayList<>();

        divisionArrayListMock.add(new Division("Mock Division2" , getTeamArrayMock()));
        divisionArrayListMock.add(new Division("Mock Division1" , getTeamArrayMock()));

        return divisionArrayListMock;
    }



    public IPlayerStatistics getPlayerStatisticsMock(){
        IPlayerStatistics playerStatistics =new PlayerStatistics(20,10,10,10,10);
        return playerStatistics;
    }

    public IPlayer getFreeAgentObjectMock(){
        return new FreeAgent("Mock Free Agent", "defense",getPlayerStatisticsMock());
    }

    public List<IPlayer> getFreeAgentArrayMock(){
        List<IPlayer> freeAgentArrayListMock = new ArrayList<>();

        freeAgentArrayListMock.add(new FreeAgent("Mock Free Agent 2" , "defense", getPlayerStatisticsMock()));
        freeAgentArrayListMock.add(new FreeAgent("Mock Free Agent 1" , "forward", getPlayerStatisticsMock()));

        return freeAgentArrayListMock;
    }

    public List<IPlayer> get20FreeAgentArrayMock(){
        List<IPlayer> freeAgents = new ArrayList<>();
        for (int i=0; i<20; i++) {
            if (i<2)
            {
                freeAgents.add(new FreeAgent("Henry"+i, "goalie", getPlayerStatistics()));
            }else {
                freeAgents.add(new FreeAgent("Henry" + i, "forward", getPlayerStatistics()));
            }
        }
        return freeAgents;
    }

    public IConference getConferenceObjectMock(){
        return new Conference( "Mock Conference" , getDivisionArrayMock());
    }

    public List<IConference> getConferenceArrayMock(){
        List<IConference> conferenceArrayListMock= new ArrayList<>();

        conferenceArrayListMock.add(new Conference("Mock Conference1" , getDivisionArrayMock()));
        conferenceArrayListMock.add(new Conference("Mock Conference2" , getDivisionArrayMock()));

        return conferenceArrayListMock;
    }

    public IConference getConferenceTestMock(){
        List<IPlayer> playersList = new ArrayList<>();
        IPlayerStatistics playerStatistics =new PlayerStatistics(20,10,10,10,10);
        playersList.add(new Player("Henry","forward",false,playerStatistics));
        playersList.add(new Player("Max","goalie",true,playerStatistics));
        ICoach headCoach = new Coach("Todd McLellan",0.1,0.5,1.0,0.2);
        ITeam team = new Team("Ontario","Mathew",headCoach,playersList);
        List<ITeam> teamArrayList=new ArrayList<>();
        teamArrayList.add(team);
        IDivision division = new Division("Atlantic",teamArrayList);
        List<IDivision> divisionsList=new ArrayList<>();
        divisionsList.add(division);
        return new Conference("Western",divisionsList);
    }

    public ILeagueObjectModel getLeagueObjectMock(){
        ILeagueObjectModel leagueObjectMock=null;

        List<IPlayer> playersList=new ArrayList<>();
        playersList.add(new Player("Henry","forward",false,getPlayerStatistics()));
        playersList.add(new Player("Max","goalie",true,getPlayerStatistics()));
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
        IPlayer freeAgent = new FreeAgent("Matt","forward",getPlayerStatistics());
        freeAgentsList.add(freeAgent);
        IPlayerStatistics playerStatistics2= new PlayerStatistics(20,11,20,15,16);
        IPlayer freeAgent2 = new FreeAgent("Jack","forward",playerStatistics2);
        freeAgentsList.add(freeAgent2);
        List<ICoach> coachList=new ArrayList<>();
        List<IGeneralManager> generalManagerList=new ArrayList<>();

        leagueObjectMock = new LeagueObjectModel("Dhl",conferences,freeAgentsList,coachList,generalManagerList,getGameConfig());
        return leagueObjectMock;
    }

    public List<ICoach> getCoaches(){
        List<ICoach> coachList= new ArrayList<>();
        ICoach headCoach1 = new Coach("Todd McLellan",0.1,0.5,1.0,0.2);
        ICoach headCoach2 = new Coach("Mary Smith",0.1,0.5,1.0,0.2);
        coachList.add(headCoach1);
        coachList.add(headCoach2);
        return coachList;
    }

    public ICoach getSingleCoach(){
        ICoach headCoach = new Coach("Todd McLellan",0.1,0.5,1.0,0.2);
        return headCoach;
    }

    public List getManagers(){
        List<IGeneralManager> managers=new ArrayList();
        managers.add(new GeneralManager("Karen Potam"));
        managers.add(new GeneralManager("Joseph Squidly"));
        managers.add(new GeneralManager("Tom Spaghetti"));
        return managers;
    }

    public ILeagueObjectModel leagueModelMock() {
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

        ArrayList<IPlayer> freeAgentsList=new ArrayList<>();
        IPlayerStatistics freeAgentStatistics =new PlayerStatistics(20,10,10,10,0);
        freeAgentsList.add(new FreeAgent("Henry","forward",freeAgentStatistics));
        freeAgentsList.add(new FreeAgent("Max","goalie",freeAgentStatistics));


        ArrayList<ICoach> coaches = new ArrayList<>();
        ICoach coach1 = new Coach("Todd McLellan",0.1,0.5,1.0,0.2);
        ICoach coach2 = new Coach("Todd McLellan",0.1,0.5,1.0,0.2);
        coaches.add(coach1);
        coaches.add(coach2);
        ArrayList<IGeneralManager> managers = new ArrayList<>();
        IGeneralManager gm1 = new GeneralManager("Todd McLellan");
        IGeneralManager gm2 = new GeneralManager("Todd McLellan1");
        managers.add(gm1);
        managers.add(gm2);
        ILeagueObjectModel leagueModel=new LeagueObjectModel("Dhl",conferences,freeAgentsList,coaches,managers,getGameConfig());

        return leagueModel;
    }

    public IGameConfig getGameConfig() {
        ImportJsonFile importJsonFile = new ImportJsonFile("src/test/java/dhl/importJsonTest/GameConfigMockFile.json");
        IGameConfig gameConfig=null;
        try{
             gameConfig = new GameConfig(importJsonFile.getJsonObject());
        }
        catch (Exception e){

        }
        return gameConfig;
    }
}
