package dhl.businessLogic.aging.Interface;

import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;

import java.util.List;
import java.util.Map;


public interface IRetirementSystem {
    ILeagueObjectModel getLeagueObjectModel();

    void initiateRetirement(Map<String, List<IPlayer>> playersToRetire, List<IPlayer> freeAgentsToRetire) throws Exception;

}
