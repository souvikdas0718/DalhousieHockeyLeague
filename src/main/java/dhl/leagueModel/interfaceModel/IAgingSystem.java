package dhl.leagueModel.interfaceModel;


import java.util.*;

public interface IAgingSystem {

    public void ageAllPlayers(ILeagueObjectModel leagueObjectModel, int noOfDays);

    public Map<String, List<IPlayer>> selectPlayersToRetire(List<ITeam> teams);

    public Map<String,List<IFreeAgent>> selectFreeAgentsToRetire(ILeagueObjectModel leagueObjectModel);

    public void healInjuredPlayers(Date currentDate, IPlayer player);
}
