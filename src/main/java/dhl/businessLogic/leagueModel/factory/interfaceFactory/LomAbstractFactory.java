package dhl.businessLogic.leagueModel.factory.interfaceFactory;

import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.InputOutput.importJson.Interface.ISerializeLeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.database.interfaceDB.ILeagueObjectModelDB;
import org.json.simple.JSONObject;

import java.util.List;

public interface LomAbstractFactory {


    IValidation createCommonValidation();

    IConference createConference(String conferenceName, List<IDivision> divisions);

    IDivision createDivision(String divisionName, List<ITeam> teamsList);

    IPlayer createFreeAgent(String playerName, String position, IPlayerStatistics playerStatistics);

    ILeagueObjectModelValidation createLeagueObjectModelValidation();

    IGeneralManager createGeneralManager(String managerName);

    ICoach createCoach(String coachName, double skating, double shooting, double checking, double saving);

    ITeam createTeam(String teamName, String generalManager, ICoach headCoach, List<IPlayer> playersList);

    IPlayer createPlayer(String playerName, String position, Boolean captain, IPlayerStatistics playerStats);

    IPlayerStatistics createPlayerStatistics(int age, int skating, int shooting, int checking, int saving);

    IGameConfig createGameConfig(JSONObject leagueJson);

}
