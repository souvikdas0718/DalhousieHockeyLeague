package dhl.businessLogic.leagueModel.factory;

import dhl.businessLogic.leagueModel.PlayerDraftAbstract;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.ISerializeLeagueObjectModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import java.util.List;

public abstract class LeagueModelAbstractFactory {

    private static LeagueModelAbstractFactory uniqueInstance = null;
    private static final Logger logger = LogManager.getLogger(LeagueModelAbstractFactory.class);

    protected LeagueModelAbstractFactory() {

    }

    public static LeagueModelAbstractFactory instance() {
        if (null == uniqueInstance) {
            logger.debug("Instance of LeagueModelAbstractFactory created");
            uniqueInstance = new LeagueModelFactory();
        }
        logger.debug("Instance of LeagueModelAbstractFactory accessed");
        return uniqueInstance;
    }

    public static void setFactory(LeagueModelAbstractFactory factory) {
        logger.debug("LeagueModelAbstractFactory's instance updated");
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

    public abstract IPlayerStatistics createPlayerStatistics(int skating, int shooting, int checking, int saving);

    public abstract IGameConfig createGameConfig(JSONObject leagueJson);

    public abstract IPlayer createFreeAgent(String playerName, String position, IPlayerStatistics playerStatistics);

    public abstract ILeagueObjectModelInput createLeagueObjectModelInput(String leagueName, String conferenceName, String divisionName, ITeam newlyCreatedTeam, ISerializeLeagueObjectModel serializeLeagueObjectModel);

    public abstract ILeagueObjectModel createDefaultLeagueObjectModel();

    public abstract IConference createConferenceDefault();

    public abstract IDivision createDivisionDefault();

    public abstract ICoach createCoachDefault();

    public abstract ITeam createTeamDefault();

    public abstract IPlayer createPlayerDefault();

    public abstract IPlayer createFreeAgentDefault();

    public abstract PlayerDraftAbstract createPlayerDraft();

    public abstract IGeneralManager createGeneralManagerDefault();
}
