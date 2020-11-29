package dhl.businessLogicTest.agingTest.mocks;

import dhl.businessLogic.leagueModel.FreeAgent;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.factory.LeagueObjectModelBuilder;
import dhl.businessLogic.leagueModel.factory.LeagueObjectModelDirector;
import dhl.businessLogic.leagueModel.factory.interfaceFactory.ILeagueObjectModelBuilder;
import dhl.businessLogic.leagueModel.factory.interfaceFactory.ILeagueObjectModelDirector;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.GameplayConfigMock;
import dhl.businessLogicTest.leagueModelTests.mocks.LeagueMock;
import dhl.businessLogicTest.leagueModelTests.mocks.PlayerMock;

import java.util.ArrayList;
import java.util.List;

public class AgingMock {
    LeagueModelAbstractFactory leagueFactory;
    LeagueModelMockAbstractFactory mockFactory;
    ILeagueObjectModelBuilder leagueBuilder;
    ILeagueObjectModelDirector leagueDirector;

    public AgingMock() {
        leagueFactory = LeagueModelAbstractFactory.instance();
        mockFactory = LeagueModelMockAbstractFactory.instance();
        leagueBuilder = new LeagueObjectModelBuilder();
        leagueDirector = new LeagueObjectModelDirector(leagueBuilder);
    }

    public ITeam teamWithPlayersAtMaxAge() {
        return varyByAge(50);
    }

    public ITeam teamWithPlayersMoreThanAvg() {
        return varyByAge(36);
    }

    public ITeam teamWithPlayersLessThanAvg() {
        return varyByAge(34);
    }

    public ITeam varyByAge(int age) {
        List<IPlayer> players = new ArrayList<>();
        IPlayerStatistics playerStatistics = leagueFactory.createPlayerStatistics(20, 20, 20, 20);
        playerStatistics.setAge(age);
        players.add(leagueFactory.createPlayer("PlayerOne", "forward", true, playerStatistics));
        ICoach headCoach = leagueFactory.createCoach("Todd McLellan", 0.1, 0.5, 1.0, 0.2);
        IGeneralManager manager = leagueFactory.createGeneralManager("Mathew", "normal");
        return leagueFactory.createTeam("Mock Team", manager, headCoach, players);
    }

    public IPlayer getPlayerAtMaxAge() {
        IPlayerStatistics playerStatistics = leagueFactory.createPlayerStatistics(2, 2, 2, 2);
        playerStatistics.setAge(50);
        return leagueFactory.createPlayer("PlayerOne", "forward", true, playerStatistics);
    }

    public ILeagueObjectModel getFreeAgentsInLeague() {
        List<IPlayer> freeAgents = new ArrayList<>();
        IPlayerStatistics playerStatistics1 = leagueFactory.createPlayerStatistics(20, 20, 20, 20);
        playerStatistics1.setAge(50);
        freeAgents.add(leagueFactory.createFreeAgent("PlayerOne", "forward", playerStatistics1));

        LeagueMock leagueMock = mockFactory.createLeagueMock();
        ILeagueObjectModel leagueObjectModel = leagueMock.getLeagueObjectModel();
        return leagueDirector.construct(leagueObjectModel.getLeagueName(), leagueObjectModel.getConferences(), freeAgents, leagueMock.getCoaches(), leagueMock.getManagers(), leagueMock.getGameplayConfig());
    }

    public List<IPlayer> getFreeAgents() {
        List<IPlayer> freeAgentsList = new ArrayList<>();
        IPlayerStatistics freeAgentStatistics1 = leagueFactory.createPlayerStatistics(20, 20, 15, 15);
        freeAgentStatistics1.setAge(20);
        IPlayerStatistics freeAgentStatistics2 = leagueFactory.createPlayerStatistics(13, 14, 12, 11);
        freeAgentStatistics2.setAge(35);
        IPlayerStatistics freeAgentStatistics3 = leagueFactory.createPlayerStatistics(1, 3, 3, 3);
        freeAgentStatistics3.setAge(20);
        IPlayerStatistics freeAgentStatistics4 = leagueFactory.createPlayerStatistics(6, 3, 3, 5);
        freeAgentStatistics4.setAge(25);
        IPlayerStatistics freeAgentStatistics5 = leagueFactory.createPlayerStatistics(1, 2, 15, 13);
        freeAgentStatistics5.setAge(25);

        freeAgentsList.add(leagueFactory.createFreeAgent("F2", "goalie", freeAgentStatistics2));
        freeAgentsList.add(leagueFactory.createFreeAgent("F3", "goalie", freeAgentStatistics3));
        freeAgentsList.add(leagueFactory.createFreeAgent("F1", "forward", freeAgentStatistics1));
        freeAgentsList.add(leagueFactory.createFreeAgent("F4", "goalie", freeAgentStatistics4));
        freeAgentsList.add(leagueFactory.createFreeAgent("F5", "goalie", freeAgentStatistics5));
        return freeAgentsList;
    }

    public ILeagueObjectModel retirementLeagueMock() {
        PlayerMock playerMock = mockFactory.createPlayerMock();
        LeagueMock leagueMock = mockFactory.createLeagueMock();
        GameplayConfigMock gameplayConfigMock = mockFactory.createGameplayConfig();
        ILeagueObjectModel leagueObjectMock = null;

        List<IPlayer> playersList = new ArrayList<>();
        playersList.add(leagueFactory.createPlayer("Henry", "forward", false, playerMock.getPlayerStats()));
        playersList.add(leagueFactory.createPlayer("Max", "goalie", true, playerMock.getPlayerStats()));
        ICoach headCoach = leagueFactory.createCoach("Todd McLellan", 0.1, 0.5, 1.0, 0.2);
        IGeneralManager manager = leagueFactory.createGeneralManager("Mathew", "normal");
        ITeam team = leagueFactory.createTeam("Ontario", manager, headCoach, playersList);
        List<ITeam> teamArrayList = new ArrayList<>();
        teamArrayList.add(team);

        IDivision division = leagueFactory.createDivision("Atlantic", teamArrayList);
        List<IDivision> divisionsList = new ArrayList<>();
        divisionsList.add(division);

        IConference conference = leagueFactory.createConference("Western", divisionsList);
        List<IConference> conferences = new ArrayList<>();
        conferences.add(conference);

        List<IPlayer> freeAgentsList = new ArrayList<>();
        IPlayer freeAgent = leagueFactory.createFreeAgent("Matt", "forward", playerMock.getPlayerStats());
        freeAgentsList.add(freeAgent);
        IPlayerStatistics playerStatistics2 = leagueFactory.createPlayerStatistics(11, 20, 15, 16);
        playerStatistics2.setAge(20);
        IPlayer freeAgent2 = new FreeAgent("Jack", "forward", playerStatistics2);
        freeAgentsList.add(freeAgent2);
        List<ICoach> coachList = new ArrayList<>();
        leagueObjectMock = leagueDirector.construct("Dhl", conferences, freeAgentsList, coachList, leagueMock.getManagers(), gameplayConfigMock.getAgingGameConfig());
        return leagueObjectMock;

    }


}
