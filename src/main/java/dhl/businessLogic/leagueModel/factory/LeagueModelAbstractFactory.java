package dhl.businessLogic.leagueModel.factory;

import dhl.businessLogic.leagueModel.Player;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.ISerializeLeagueObjectModel;
import org.json.simple.JSONObject;

import java.util.List;

public abstract class LeagueModelAbstractFactory {

    private static LeagueModelAbstractFactory uniqueInstance = null;

    protected LeagueModelAbstractFactory() {

    }

    public static LeagueModelAbstractFactory instance() {
        if (null == uniqueInstance) {
            uniqueInstance = new LeagueModelFactory();
        }
        return uniqueInstance;
    }

    public static void setFactory(LeagueModelAbstractFactory factory) {
        uniqueInstance = factory;
    }

    public abstract IValidation createCommonValidation();

    public abstract ILeagueObjectModelValidation createLeagueObjectModelValidation();

    public abstract IConference createConference(String conferenceName, List<IDivision> divisions);

    public abstract IDivision createDivision(String divisionName, List<ITeam> teamsList);

    public abstract IGeneralManager createGeneralManager(String managerName, String personality);

    public abstract ICoach createCoach(String coachName, double skating, double shooting, double checking, double saving);

    public abstract ITeam createTeam(String teamName, IGeneralManager generalManager, ICoach headCoach, List<IPlayer> playersList);

    public abstract IPlayer createPlayer(String playerName, String position, Boolean captain, IPlayerStatistics playerStats);

    public abstract IPlayerStatistics createPlayerStatistics( int skating, int shooting, int checking, int saving);

    public abstract IGameConfig createGameConfig(JSONObject leagueJson);

    public abstract IPlayer createFreeAgent(String playerName, String position, IPlayerStatistics playerStatistics);

    public abstract ILeagueObjectModelInput createLeagueObjectModelInput(String leagueName, String conferenceName, String divisionName, ITeam newlyCreatedTeam, ISerializeLeagueObjectModel serializeLeagueObjectModel);

    public abstract IConference createConferenceDefault();

    public abstract IDivision createDivisionDefault();

    public abstract ICoach createCoachDefault();

    public abstract IGeneralManager createGeneralManagerDefault();

    public abstract ITeam createTeamDefault();

    public abstract IPlayer createPlayerDefault();

    public abstract IPlayer createFreeAgentDefault();

    public abstract IPlayerDraft createPlayerDraft();
}
