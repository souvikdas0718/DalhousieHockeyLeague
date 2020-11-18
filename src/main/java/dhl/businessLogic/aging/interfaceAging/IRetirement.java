package dhl.businessLogic.aging.interfaceAging;

import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;

import java.util.List;
import java.util.Map;


public interface IRetirement {
    ILeagueObjectModel getLeagueObjectModel();

    void initiateRetirement(Map<String, List<IPlayer>> playersToRetire, List<IPlayer> freeAgentsToRetire) throws Exception;

}
