package dhl.mocks;

import dhl.businessLogic.leagueModel.*;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.inputOutput.importJson.ImportJsonFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LeagueObjectModelMocks {

    public IPlayerStatistics getPlayerStatistics() {
        IPlayerStatistics playerStatistics = new PlayerStatistics(10, 10, 10, 10);
        playerStatistics.setAge(25);
        return playerStatistics;
    }

    public List<IPlayer> getPlayerArrayMock() {
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

    public ITeam getTeamObjectMock() {
        ICoach headCoach = new Coach("Todd McLellan", 0.1, 0.5, 1.0, 0.2);
        IGeneralManager manager = new GeneralManager("Mock Manager", "normal");
        return new Team("Mock Team", manager, headCoach, getPlayerArrayMock());
    }

    public List<ITeam> getTeamArrayMock() {
        List<ITeam> teamArrayListMock = new ArrayList<>();
        ICoach headCoachOne = new Coach("Todd McLellan", 0.1, 0.5, 1.0, 0.2);
        ICoach headCoachTwo = new Coach("Todd McLellan", 0.1, 0.5, 1.0, 0.2);
        IGeneralManager manager1 = new GeneralManager("Mock Manager1", "normal");
        IGeneralManager manager2 = new GeneralManager("Mock Manager2", "normal");
        teamArrayListMock.add(new Team("Mock Team2", manager1, headCoachTwo, getPlayerArrayMock()));
        teamArrayListMock.add(new Team("Mock Team1", manager2, headCoachOne, getPlayerArrayMock()));

        return teamArrayListMock;
    }

    public List<IDivision> getDivisionArrayMock() {
        List<IDivision> divisionArrayListMock = new ArrayList<>();

        divisionArrayListMock.add(new Division("Mock Division2", getTeamArrayMock()));
        divisionArrayListMock.add(new Division("Mock Division1", getTeamArrayMock()));

        return divisionArrayListMock;
    }


    public IPlayerStatistics getPlayerStatisticsMock() {
        IPlayerStatistics playerStatistics = new PlayerStatistics( 10, 10, 10, 10);
        playerStatistics.setAge(20);
        return playerStatistics;
    }

    public List<IPlayer> getFreeAgentArrayMock() {
        List<IPlayer> freeAgentArrayListMock = new ArrayList<>();

        freeAgentArrayListMock.add(new FreeAgent("Mock Free Agent 2", "defense", getPlayerStatisticsMock()));
        freeAgentArrayListMock.add(new FreeAgent("Mock Free Agent 1", "forward", getPlayerStatisticsMock()));

        return freeAgentArrayListMock;
    }

    public List<IPlayer> get20FreeAgentArrayMock() {
        List<IPlayer> freeAgents = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            if (i < 2) {
                freeAgents.add(new FreeAgent("Henry" + i, "goalie", getPlayerStatistics()));
            } else {
                freeAgents.add(new FreeAgent("Henry" + i, "forward", getPlayerStatistics()));
            }
        }
        return freeAgents;
    }

    public List<IPlayer> get30FreeAgentArrayMock() {
        List<IPlayer> freeAgents = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            if (i < 2) {
                freeAgents.add(new FreeAgent("Henry" + i, "goalie", getPlayerStatistics()));
            } else {
                freeAgents.add(new FreeAgent("Henry" + i, "forward", getPlayerStatistics()));
            }
        }
        return freeAgents;
    }

    public List<IConference> getConferenceArrayMock() {
        List<IConference> conferenceArrayListMock = new ArrayList<>();

        conferenceArrayListMock.add(new Conference("Mock Conference1", getDivisionArrayMock()));
        conferenceArrayListMock.add(new Conference("Mock Conference2", getDivisionArrayMock()));

        return conferenceArrayListMock;
    }

    public ILeagueObjectModel getLeagueObjectMock() {
        ILeagueObjectModel leagueObjectMock = null;

        List<IPlayer> playersList = new ArrayList<>();
        playersList.add(new Player("Henry", "forward", false, getPlayerStatistics()));
        playersList.add(new Player("Max", "goalie", true, getPlayerStatistics()));
        ICoach headCoach = new Coach("Todd McLellan", 0.1, 0.5, 1.0, 0.2);
        IGeneralManager manager = new GeneralManager("Mathew", "normal");
        ITeam team = new Team("Ontario", manager, headCoach, playersList);
        List<ITeam> teamArrayList = new ArrayList<>();
        teamArrayList.add(team);

        IDivision division = new Division("Atlantic", teamArrayList);
        List<IDivision> divisionsList = new ArrayList<>();
        divisionsList.add(division);

        IConference conference = new Conference("Western", divisionsList);
        List<IConference> conferences = new ArrayList<>();
        conferences.add(conference);

        List<IPlayer> freeAgentsList = new ArrayList<>();
        IPlayer freeAgent = new FreeAgent("Matt", "forward", getPlayerStatistics());
        freeAgentsList.add(freeAgent);
        IPlayerStatistics playerStatistics2 = new PlayerStatistics(11, 20, 15, 16);
        playerStatistics2.setAge(20);
        IPlayer freeAgent2 = new FreeAgent("Jack", "forward", playerStatistics2);
        freeAgentsList.add(freeAgent2);
        List<ICoach> coachList = new ArrayList<>();

        leagueObjectMock = new LeagueObjectModel("Dhl", conferences, freeAgentsList, coachList, getManagers(), getGameConfig());
        return leagueObjectMock;
    }

    public List<ICoach> getCoaches() {
        List<ICoach> coachList = new ArrayList<>();
        ICoach headCoach1 = new Coach("Todd McLellan", 0.1, 0.5, 1.0, 0.2);
        ICoach headCoach2 = new Coach("Mary Smith", 0.1, 0.5, 1.0, 0.2);
        coachList.add(headCoach1);
        coachList.add(headCoach2);
        return coachList;
    }

    public ICoach getSingleCoach() {
        ICoach headCoach = new Coach("Todd McLellan", 0.1, 0.5, 1.0, 0.2);
        return headCoach;
    }

    public List<IGeneralManager> getManagers() {
        List<IGeneralManager> managers = new ArrayList();
        managers.add(new GeneralManager("Karen Potam"));
        managers.add(new GeneralManager("Joseph Squidly"));
        managers.add(new GeneralManager("Tom Spaghetti"));
        return managers;
    }

    public ILeagueObjectModel leagueModelMockWith30Players() {
        List<IPlayer> playersList = new ArrayList<>();
        IPlayerStatistics playerStatistics = new PlayerStatistics( 5, 5, 5, 5);
        playerStatistics.setAge(16);
        playerStatistics.setDateOfBirth(1,1,1988);
        playersList.add(new Player("Henry", "forward", true, playerStatistics));
        playersList.add(new Player("MaxG1", "goalie", false, playerStatistics));
        playersList.add(new Player("MaxG2", "goalie", false, playerStatistics));
        for (int i = 1; i <= 27; i++) {
            playersList.add(new Player("Max" + i, "forward", false, playerStatistics));
        }

        ICoach headCoach = new Coach("Todd McLellan", 0.1, 0.5, 1.0, 0.2);
        IGeneralManager manager = new GeneralManager("Mathew", "normal");
        ITeam team = new Team("Ontario", manager, headCoach, playersList);
        List<ITeam> teamArrayList = new ArrayList<>();
        teamArrayList.add(team);

        IDivision division1 = new Division("Atlantic1", teamArrayList);
        IDivision division2 = new Division("Atlantic2", teamArrayList);
        List<IDivision> divisionsList = new ArrayList<>();
        divisionsList.add(division1);
        divisionsList.add(division2);

        IConference conference1 = new Conference("Western1", divisionsList);
        IConference conference2 = new Conference("Western2", divisionsList);
        List<IConference> conferences = new ArrayList<>();
        conferences.add(conference1);
        conferences.add(conference2);

        ArrayList<IPlayer> freeAgentsList = new ArrayList<>();
        IPlayerStatistics freeAgentStatistics = new PlayerStatistics( 10, 10, 10, 5);
        freeAgentStatistics.setAge(20);
        freeAgentStatistics.setDateOfBirth(1,1,1988);
        freeAgentsList.add(new FreeAgent("Henry", "forward", freeAgentStatistics));
        freeAgentsList.add(new FreeAgent("Max", "goalie", freeAgentStatistics));


        ArrayList<ICoach> coaches = new ArrayList<>();
        ICoach coach1 = new Coach("Todd McLellan", 0.1, 0.5, 1.0, 0.2);
        ICoach coach2 = new Coach("Todd McLellan1", 0.1, 0.5, 1.0, 0.2);
        coaches.add(coach1);
        coaches.add(coach2);
        ArrayList<IGeneralManager> managers = new ArrayList<>();
        IGeneralManager gm1 = new GeneralManager("Todd McLellan");
        IGeneralManager gm2 = new GeneralManager("Todd McLellan1");
        managers.add(gm1);
        managers.add(gm2);
        ILeagueObjectModel leagueModel = new LeagueObjectModel("Dhl", conferences, freeAgentsList, coaches, managers, getGameConfig());

        return leagueModel;
    }

    public IGameConfig getGameConfig() {
        ImportJsonFile importJsonFile = new ImportJsonFile("src/test/java/dhl/importJsonTest/GameConfigMockFile.json");
        IGameConfig gameConfig = null;
        try {
            gameConfig = new GameConfig(importJsonFile.getJsonObject());
        } catch (Exception e) {

        }
        return gameConfig;
    }

    public List<IPlayer> getTeamPlayers(){
        List<IPlayer> playerList = new ArrayList<>();
        Random rand = new Random();

        for(int i=0;i<30;i++){
            IPlayerStatistics playerStatistics= new PlayerStatistics(rand.nextInt((20 - 1) + 1) + 1,rand.nextInt((20 - 1) + 1) + 1,rand.nextInt((20 - 1) + 1) + 1,rand.nextInt((20 - 1) + 1) + 1);
            playerStatistics.setAge(20);
            IPlayer player;
            if(i==0){
                player= new Player("Player"+i, PlayerPosition.FORWARD.toString(),true,playerStatistics);
            }
            else if(i< 16){
                player= new Player("Player"+i,PlayerPosition.FORWARD.toString(),false,playerStatistics);
            }
            else if(i< (30- 4)){
                player = new Player("Player"+i,PlayerPosition.DEFENSE.toString(),false,playerStatistics);
            }
            else{
                player= new Player("Player"+i,PlayerPosition.GOALIE.toString(),false,playerStatistics);
            }
            playerList.add(player);
        }
        return playerList;
    }
}
