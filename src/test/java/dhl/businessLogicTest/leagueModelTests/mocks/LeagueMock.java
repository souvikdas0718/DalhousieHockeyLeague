package dhl.businessLogicTest.leagueModelTests.mocks;

import dhl.mocks.JsonFilePathMock;
import dhl.businessLogic.leagueModel.FreeAgent;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.factory.LeagueObjectModelBuilder;
import dhl.businessLogic.leagueModel.factory.LeagueObjectModelDirector;
import dhl.businessLogic.leagueModel.factory.interfaceFactory.ILeagueObjectModelBuilder;
import dhl.businessLogic.leagueModel.factory.interfaceFactory.ILeagueObjectModelDirector;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.inputOutput.importJson.ImportJsonFile;
import dhl.inputOutput.importJson.interfaces.IImportJsonFile;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LeagueMock {

    LeagueModelAbstractFactory factory;
    LeagueModelMockAbstractFactory mockFactory;
    ConferenceMock conferenceMock;
    FreeAgentMock freeAgentMock;
    CoachMock coachMock;
    ManagerMock managerMock;
    GameplayConfigMock gameplayConfigMock;
    ILeagueObjectModelBuilder leagueBuilder;
    ILeagueObjectModelDirector leagueDirector;


    public LeagueMock(){
        factory = LeagueModelAbstractFactory.instance();
        mockFactory = LeagueModelMockAbstractFactory.instance();
        leagueBuilder = new LeagueObjectModelBuilder();
        leagueDirector = new LeagueObjectModelDirector(leagueBuilder);

        conferenceMock = mockFactory.createConferenceMock();
        freeAgentMock = mockFactory.createFreeAgentMock();
        coachMock = mockFactory.createCoachMock();
        managerMock = mockFactory.createManagerMock();
        gameplayConfigMock = mockFactory.createGameplayConfig();

    }

    public ILeagueObjectModel getLeagueObjectModel()  {

        List<IConference> conferences = getConferences();
        List<IPlayer> freeAgents = getFreeAgents();
        List<ICoach> coaches = getCoaches();
        List<IGeneralManager> managers = getManagers();
        IGameConfig gameConfig = getGameplayConfig();

        return leagueDirector.construct("Dhl",conferences,freeAgents,coaches,managers,gameConfig);
    }

    public List<IConference> getConferences(){
        return conferenceMock.getConferences();
    }

    public  List<IPlayer> getFreeAgents(){
        return freeAgentMock.getFreeAgents();
    }

    public  List<ICoach> getCoaches(){
        return coachMock.getCoaches();
    }

    public  List<IGeneralManager> getManagers(){
        return managerMock.getManagers();
    }

    public IGameConfig getGameplayConfig(){
        return gameplayConfigMock.getGameplayConfig();
    }

    public ILeagueObjectModel getLeagueObjectModelDuplicateConference()  {
        List<IConference> conferences = getConferences();
        for(int i=0;i<2;i++){
            conferences.add( conferenceMock.getConference());
        }
        List<IPlayer> freeAgents = getFreeAgents();
        List<ICoach> coaches = getCoaches();
        List<IGeneralManager> managers = getManagers();
        IGameConfig gameConfig = getGameplayConfig();

        return leagueDirector.construct("Dhl",conferences,freeAgents,coaches,managers,gameConfig);
    }

    public ILeagueObjectModel getLeagueObjectModelOddConf()  {
        List<IConference> conferences = getConferences();
        conferences.add( conferenceMock.getConference());
        List<IPlayer> freeAgents = getFreeAgents();
        List<ICoach> coaches = getCoaches();
        List<IGeneralManager> managers = getManagers();
        IGameConfig gameConfig = getGameplayConfig();

        return leagueDirector.construct("Dhl",conferences,freeAgents,coaches,managers,gameConfig);
    }

    public ILeagueObjectModel getLeagueObjectModelSameDivision()  {
        PlayerMock playerMock = mockFactory.createPlayerMock();
        LeagueMock leagueMock = mockFactory.createLeagueMock();
        GameplayConfigMock gameplayConfigMock = mockFactory.createGameplayConfig();
        ILeagueObjectModel leagueObjectMock = null;

        List<IPlayer> playersList = new ArrayList<>();
        playersList.add(factory.createPlayer( "Henry", "forward", false, playerMock.getPlayerStats()));
        playersList.add(factory.createPlayer("Max", "goalie", true, playerMock.getPlayerStats()));
        ICoach headCoach = factory.createCoach("Todd McLellan", 0.1, 0.5, 1.0, 0.2);
        IGeneralManager manager = factory.createGeneralManager("Mathew", "normal");
        ITeam team = factory.createTeam("Ontario", manager, headCoach, playersList);
        List<ITeam> teamArrayList = new ArrayList<>();
        teamArrayList.add(team);

        IDivision division1 = factory.createDivision("Atlantic", teamArrayList);
        IDivision division2 = factory.createDivision("Pacific", teamArrayList);
        List<IDivision> divisionsList = new ArrayList<>();
        divisionsList.add(division1);
        divisionsList.add(division2);

        IConference conference = factory.createConference("Western", divisionsList);
        List<IConference> conferences = new ArrayList<>();
        conferences.add(conference);

        List<IPlayer> freeAgentsList = new ArrayList<>();
        IPlayer freeAgent = factory.createFreeAgent("Matt", "forward", playerMock.getPlayerStats());
        freeAgentsList.add(freeAgent);
        IPlayerStatistics playerStatistics2 =factory.createPlayerStatistics(11, 20, 15, 16);
        playerStatistics2.setAge(20);
        IPlayer freeAgent2 = new FreeAgent("Jack", "forward", playerStatistics2);
        freeAgentsList.add(freeAgent2);
        List<ICoach> coachList = new ArrayList<>();
        leagueObjectMock = leagueDirector.construct("Dhl", conferences, freeAgentsList, coachList, leagueMock.getManagers(), gameplayConfigMock.getAgingGameConfig());
        return leagueObjectMock;
    }

    public ILeagueObjectModel getLeagueObjectModelFromJson() throws Exception {

        JsonFilePathMock filePathMock = new JsonFilePathMock();
        IImportJsonFile importJsonFile = new ImportJsonFile(filePathMock.getFilePath());
        JSONObject leagueObject = importJsonFile.getJsonObject();
        ILeagueObjectModel leagueObjectModel =  leagueDirector.constructFromJson(leagueObject);
        return leagueObjectModel;
    }
}
