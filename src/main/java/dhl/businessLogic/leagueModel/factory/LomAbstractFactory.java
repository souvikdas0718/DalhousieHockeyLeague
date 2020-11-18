package dhl.businessLogic.leagueModel.factory;

import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.InputOutput.importJson.Interface.ISerializeLeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.database.interfaceDB.ILeagueObjectModelDB;

import java.util.List;

public interface LomAbstractFactory {

    public ICoach createCoach(String coachName, double skating, double shooting, double checking, double saving);
    public IValidation createCommonValidation();
    public IConference createConference(String conferenceName, List<IDivision> divisions);
    public IDivision createDivision(String divisionName, List<ITeam> teamsList);
    public IPlayer createFreeAgent(String playerName, String position, IPlayerStatistics playerStatistics);
    public IGeneralManager createGeneralManager();
    public ILeagueObjectModel createLeagueObjectModel(String leagueName, List<IConference> conferences, List<IPlayer> freeAgents, List<ICoach> coaches, List<IGeneralManager> managers, IGameConfig gameConfig);
    public ILeagueObjectModelInput createLeagueObjectModelInput(String leagueName, String conferenceName, String divisionName, ITeam newlyCreatedTeam, ILeagueObjectModelValidation leagueObjectModelValidation, ISerializeLeagueObjectModel serializeLeagueObjectModel);
    public ILeagueObjectModelValidation createLeagueObjectModelValidation();
    public IPlayer createPlayer(String playerName, String position, Boolean captain, IPlayerStatistics playerStats);
    public IPlayerStatistics createPlayerStatistics(int age, int skating, int shooting, int checking, int saving);
    public ITeam createTeam(String teamName, String generalManager, ICoach headCoach, List<IPlayer> playersList);

}
