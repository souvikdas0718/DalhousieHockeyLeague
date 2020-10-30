package dhl.Mocks;

import dhl.InputOutput.importJson.GameConfig;
import dhl.InputOutput.importJson.ImportJsonFile;
import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.leagueModel.*;
import dhl.leagueModel.interfaceModel.*;

import java.util.ArrayList;

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



    public IPlayerStatistics getPlayerStatisticsMock(){
        IPlayerStatistics playerStatistics =new PlayerStatistics(20,10,10,10,10);
        return playerStatistics;
    }

    public IFreeAgent getFreeAgentObjectMock(){
        return new FreeAgent("Mock Free Agent", "defense",getPlayerStatisticsMock());
    }

    public ArrayList<IFreeAgent> getFreeAgentArrayMock(){
        ArrayList<IFreeAgent> freeAgentArrayListMock = new ArrayList<>();

        freeAgentArrayListMock.add(new FreeAgent("Mock Free Agent 2" , "defense", getPlayerStatisticsMock()));
        freeAgentArrayListMock.add(new FreeAgent("Mock Free Agent 1" , "forward", getPlayerStatisticsMock()));

        return freeAgentArrayListMock;
    }

    public ArrayList<IFreeAgent> get20FreeAgentArrayMock(){
        ArrayList<IFreeAgent> freeAgents = new ArrayList<>();

        for (int i=0; i<18; i++) {
            freeAgents.add(new FreeAgent("Henry", "forward", getPlayerStatistics()));
            if (i<2)
            {
                freeAgents.add(new FreeAgent("Henry", "goalie", getPlayerStatistics()));
            }
        }
        return freeAgents;
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
        IPlayerStatistics playerStatistics =new PlayerStatistics(20,10,10,10,10);
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
        IFreeAgent freeAgent = new FreeAgent("Matt","forward",getPlayerStatistics());
        freeAgentsList.add(freeAgent);
        ArrayList<ICoach> coachList=new ArrayList<>();
        ArrayList<IGeneralManager> generalManagerList=new ArrayList<>();

        leagueObjectMock = new LeagueObjectModel("Dhl",conferences,freeAgentsList);
        return leagueObjectMock;
    }

    public ArrayList<ICoach> getCoaches(){
        ArrayList<ICoach> coachList= new ArrayList<>();
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

    public ArrayList getManagers(){
        ArrayList managers=new ArrayList();
        managers.add("Karen Potam");
        managers.add("Joseph Squidly");
        managers.add("Tom Spaghetti");
        return managers;
    }

    public ILeagueObjectModel leagueModelMock() {
        ArrayList<IPlayer> playersList=new ArrayList<>();
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

        IConference conference=new Conference("Western",divisionsList);
        ArrayList<IConference> conferences= new ArrayList<>();
        conferences.add(conference);

        ArrayList<IFreeAgent> freeAgentsList=new ArrayList<>();
        IPlayerStatistics freeAgentStatistics =new PlayerStatistics(20,10,10,10,0);
        freeAgentsList.add(new FreeAgent("Henry","forward",playerStatistics));
        freeAgentsList.add(new FreeAgent("Max","goalie",playerStatistics));

        ILeagueObjectModel leagueModel=new LeagueObjectModel("Dhl",conferences,freeAgentsList);

        ArrayList<ICoach> coaches = new ArrayList<>();
        ICoach coach1 = new Coach("Todd McLellan",0.1,0.5,1.0,0.2);
        ICoach coach2 = new Coach("Todd McLellan",0.1,0.5,1.0,0.2);
        coaches.add(coach1);
        coaches.add(coach2);
        leagueModel.setCoaches(coaches);

        ArrayList<IGeneralManager> managers = new ArrayList<>();
        IGeneralManager gm1 = new GeneralManager("Todd McLellan");
        IGeneralManager gm2 = new GeneralManager("Todd McLellan1");
        managers.add(gm1);
        managers.add(gm2);
        leagueModel.setGeneralManagers(managers);
        return leagueModel;
    }

    public IGameConfig getGameConfig() {
        JsonFilePathMock filePathMock = new JsonFilePathMock();
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
