package dhl.businessLogic.leagueModel.factory;

import dhl.businessLogic.leagueModel.GameConfig;
import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.businessLogic.leagueModel.*;
import dhl.businessLogic.leagueModel.factory.interfaceFactory.LomAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import org.json.simple.JSONObject;

import java.util.List;

public class LeagueModelFactory implements LomAbstractFactory {

    private static LeagueModelFactory uniqueInstance = null;

    private LeagueModelFactory() {

    }

    public static LeagueModelFactory instance() {
        if (null == uniqueInstance)
        {
            uniqueInstance = new LeagueModelFactory();
        }
        return uniqueInstance;
    }

    public IValidation createCommonValidation() {
        return new CommonValidation();
    }

    public ILeagueObjectModelValidation createLeagueObjectModelValidation() {
        return new LeagueObjectModelValidation();
    }

    public IConference createConference(String conferenceName, List<IDivision> divisions) {
        return new Conference(conferenceName, divisions);
    }

    public IDivision createDivision(String divisionName, List<ITeam> teamsList) {
        return new Division(divisionName, teamsList);
    }

    public ICoach createCoach(String coachName, double skating, double shooting, double checking, double saving) {
        return new Coach(coachName, skating, shooting, checking, saving);
    }

    public IGeneralManager createGeneralManager(String managerName) {
        return new GeneralManager(managerName);
    }

    public ITeam createTeam(String teamName, String generalManager, ICoach headCoach, List<IPlayer> playersList) {
        return new Team(teamName, generalManager, headCoach, playersList);
    }

    public IPlayerStatistics createPlayerStatistics(int age, int skating, int shooting, int checking, int saving) {
        return new PlayerStatistics(age, skating, shooting, checking, saving);
    }

    public IPlayer createPlayer(String playerName, String position, Boolean captain, IPlayerStatistics playerStats) {
        return new Player(playerName, position, captain, playerStats);
    }

    public IPlayer createFreeAgent(String playerName, String position, IPlayerStatistics playerStatistics) {
        return new FreeAgent(playerName, position, playerStatistics);
    }

    public IGameConfig createGameConfig(JSONObject leagueJson ){
        return new GameConfig(leagueJson);
    }

}
