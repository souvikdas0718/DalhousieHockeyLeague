package dhl.businessLogic.leagueModel.factory;

import dhl.inputOutput.importJson.interfaces.IGameConfig;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.ISerializeLeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.*;

import java.util.List;

public abstract class LomAbstractFactory {

    private static LomAbstractFactory uniqueInstance = null;

    protected LomAbstractFactory() {

    }

    public static LomAbstractFactory instance() {
        if (null == uniqueInstance) {
            uniqueInstance = new LeagueModelFactory();
        }
        return uniqueInstance;
    }

    public static void setFactory(LomAbstractFactory factory) {
        uniqueInstance = factory;
    }

    public abstract ICoach createCoach(String coachName, double skating, double shooting, double checking, double saving);
    public abstract IValidation createCommonValidation();
    public abstract IConference createConference(String conferenceName, List<IDivision> divisions);
    public abstract IDivision createDivision(String divisionName, List<ITeam> teamsList);
    public abstract IPlayer createFreeAgent(String playerName, String position, IPlayerStatistics playerStatistics);
    public abstract IGeneralManager createGeneralManager();
    public abstract ILeagueObjectModel createLeagueObjectModel(String leagueName, List<IConference> conferences, List<IPlayer> freeAgents, List<ICoach> coaches, List<IGeneralManager> managers, IGameConfig gameConfig);
    public abstract ILeagueObjectModelInput createLeagueObjectModelInput(String leagueName, String conferenceName, String divisionName, ITeam newlyCreatedTeam, ILeagueObjectModelValidation leagueObjectModelValidation, ISerializeLeagueObjectModel serializeLeagueObjectModel);
    public abstract ILeagueObjectModelValidation createLeagueObjectModelValidation();
    public abstract IPlayer createPlayer(String playerName, String position, Boolean captain, IPlayerStatistics playerStats);
    public abstract IPlayerStatistics createPlayerStatistics(int age, int skating, int shooting, int checking, int saving);
    public abstract ITeam createTeam(String teamName, String generalManager, ICoach headCoach, List<IPlayer> playersList);
}
