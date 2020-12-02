package dhl.businessLogic.leagueModel.factory;

import dhl.businessLogic.leagueModel.*;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.ISerializeLeagueObjectModel;
import org.json.simple.JSONObject;

import java.util.List;

public class LeagueModelFactory extends LeagueModelAbstractFactory {

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

    public IGeneralManager createGeneralManager(String managerName, String personality) {
        return new GeneralManager(managerName, personality);
    }

    public ITeam createTeam(String teamName, IGeneralManager generalManager, ICoach headCoach, List<IPlayer> playersList) {
        return new Team(teamName, generalManager, headCoach, playersList);
    }

    public IPlayerStatistics createPlayerStatistics(int skating, int shooting, int checking, int saving) {
        return new PlayerStatistics(skating, shooting, checking, saving);
    }

    public IPlayer createPlayer(String playerName, String position, Boolean captain, IPlayerStatistics playerStats) {
        return new Player(playerName, position, captain, playerStats);
    }

    public IPlayer createFreeAgent(String playerName, String position, IPlayerStatistics playerStatistics) {
        return new FreeAgent(playerName, position, playerStatistics);
    }

    public IGameConfig createGameConfig(JSONObject leagueJson) {
        return new GameConfig(leagueJson);
    }

    public ILeagueObjectModelInput createLeagueObjectModelInput(String leagueName, String conferenceName, String divisionName, ITeam newlyCreatedTeam, ISerializeLeagueObjectModel serializeLeagueObjectModel) {
        ILeagueObjectModelValidation leagueObjectModelValidation = createLeagueObjectModelValidation();
        return new LeagueObjectModelInput(leagueName, conferenceName, divisionName, newlyCreatedTeam, leagueObjectModelValidation, serializeLeagueObjectModel);
    }

    public ILeagueObjectModel createDefaultLeagueObjectModel() {
        return new LeagueObjectModel();
    }

    public IConference createConferenceDefault() {
        return new Conference();
    }

    public IDivision createDivisionDefault() {
        return new Division();
    }

    public ICoach createCoachDefault() {
        return new Coach();
    }

    public ITeam createTeamDefault() {
        return new Team();
    }

    public IPlayer createPlayerDefault() {
        return new Player();
    }

    public IPlayer createFreeAgentDefault() {
        return new FreeAgent();
    }

    public PlayerDraftAbstract createPlayerDraft() {
        return PlayerDraftAbstract.instance();
    }

    public IGeneralManager createGeneralManagerDefault() {
        return new GeneralManager();
    }

}
