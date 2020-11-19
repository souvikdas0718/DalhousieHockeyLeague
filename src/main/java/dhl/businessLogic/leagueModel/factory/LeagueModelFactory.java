package dhl.businessLogic.leagueModel.factory;

import dhl.inputOutput.importJson.interfaces.IGameConfig;
import dhl.inputOutput.importJson.interfaces.ISerializeLeagueObjectModel;
import dhl.businessLogic.leagueModel.*;
import dhl.businessLogic.leagueModel.interfaceModel.*;

import java.util.List;

public class LeagueModelFactory extends LomAbstractFactory {

    public ICoach createCoach(String coachName, double skating, double shooting, double checking, double saving) {
        return new Coach(coachName, skating, shooting, checking, saving);
    }

    public IValidation createCommonValidation() {
        return new CommonValidation();
    }

    public IConference createConference(String conferenceName, List<IDivision> divisions) {
        return new Conference(conferenceName, divisions);
    }

    public IDivision createDivision(String divisionName, List<ITeam> teamsList) {
        return new Division(divisionName, teamsList);
    }

    public IPlayer createFreeAgent(String playerName, String position, IPlayerStatistics playerStatistics) {
        return new FreeAgent(playerName, position, playerStatistics);
    }

    public IGeneralManager createGeneralManager() {
        return new GeneralManager();
    }

    public ILeagueObjectModel createLeagueObjectModel(String leagueName, List<IConference> conferences, List<IPlayer> freeAgents, List<ICoach> coaches, List<IGeneralManager> managers, IGameConfig gameConfig) {
        return new LeagueObjectModel(leagueName, conferences, freeAgents, coaches, managers, gameConfig);
    }

    public ILeagueObjectModelInput createLeagueObjectModelInput(String leagueName, String conferenceName, String divisionName, ITeam newlyCreatedTeam, ILeagueObjectModelValidation leagueObjectModelValidation, ISerializeLeagueObjectModel serializeLeagueObjectModel) {
        return new LeagueObjectModelInput(leagueName, conferenceName, divisionName, newlyCreatedTeam, leagueObjectModelValidation, serializeLeagueObjectModel);
    }

    public ILeagueObjectModelValidation createLeagueObjectModelValidation() {
        return new LeagueObjectModelValidation();
    }

    public IPlayer createPlayer(String playerName, String position, Boolean captain, IPlayerStatistics playerStats) {
        return new Player(playerName, position, captain, playerStats);
    }

    public IPlayerStatistics createPlayerStatistics(int age, int skating, int shooting, int checking, int saving) {
        return new PlayerStatistics(age, skating, shooting, checking, saving);
    }

    public ITeam createTeam(String teamName, String generalManager, ICoach headCoach, List<IPlayer> playersList) {
        return new Team(teamName, generalManager, headCoach, playersList);
    }
}
