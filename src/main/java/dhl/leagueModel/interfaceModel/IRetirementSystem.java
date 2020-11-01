package dhl.leagueModel.interfaceModel;

import java.util.List;
import java.util.Map;


public interface IRetirementSystem {
    ILeagueObjectModel getLeagueObjectModel();

    void initiateRetirement(Map<String,List<IPlayer>> playersToRetire, List<IPlayer> freeAgentsToRetire) throws Exception ;

}
