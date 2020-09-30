package dhl.leagueModel.interfaceModel;

import java.util.ArrayList;

public interface IParserOutput {
    public ArrayList<IPlayer> getPlayers();
    public void setPlayers( ArrayList<IPlayer> players);

    public ArrayList<ITeam> getTeams();
    public void setTeams(ArrayList<ITeam> teams);

    public ArrayList<IDivision> getDivisions();
    public void setDivisions(ArrayList<IDivision> divisions);

    public ArrayList<IConference> getConferences();
    public void setConferences(ArrayList<IConference> conferences);

    public ArrayList<IPlayer> getFreeAgents();
    public void setFreeAgents(ArrayList<IPlayer> freeAgents);


}
