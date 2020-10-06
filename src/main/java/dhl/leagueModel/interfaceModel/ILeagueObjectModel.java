package dhl.leagueModel.interfaceModel;

import java.util.ArrayList;
import java.util.HashMap;

public interface ILeagueObjectModel {

    public String getLeagueName();
    public void setLeagueName(String leagueName);

    public ArrayList<IConference> getConferences();
    public void setConferences(ArrayList<IConference> conferences);

    public ArrayList<IPlayer> getFreeAgents();
    public void setFreeAgents(ArrayList<IPlayer> freeAgents);

}
