package dhl.businessLogic.leagueModel.factory.interfaceFactory;

import dhl.businessLogic.leagueModel.interfaceModel.*;
import org.json.simple.JSONObject;

import java.util.List;

public interface ILeagueObjectModelDirector {
    ILeagueObjectModel construct(String leagueName, List<IConference> conferences, List<IPlayer> freeAgents, List<ICoach> coaches, List<IGeneralManager> managers, IGameConfig gameConfig);

    ILeagueObjectModel constructFromJson(JSONObject jsonLeagueObject);
}
