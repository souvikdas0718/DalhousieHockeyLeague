package dhl.leagueModel.interfaceModel;

import java.util.List;
import java.util.Map;

public interface IRetirementSystem {
    public ILeagueObjectModel getLeagueObjectModel();

    public void setLeagueObjectModel(ILeagueObjectModel leagueObjectModel) ;

    public void initiateRetirement(Map<String, List<IPlayer>> playersToRetire, Map<String,List<IFreeAgent>> freeAgentsToRetire) throws Exception;

    public void insertVeterans(Map<String,List<IPlayer>> playersToRetire, Map<String,List<IFreeAgent>> freeAgentsToRetire) throws Exception;
}
