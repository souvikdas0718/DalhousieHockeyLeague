package dhl.leagueModel;

import dhl.leagueModel.interfaceModel.*;

import java.util.ArrayList;
import java.util.HashMap;

public class LeagueObjectModel implements ILeagueObjectModel {
    public String leagueName;
    public ArrayList<IConference> conferences;
    public ArrayList<IPlayer>freeAgents;

    public LeagueObjectModel(){
        leagueName="";
        conferences=new ArrayList<>();
        freeAgents = new ArrayList<>();
    }
    public LeagueObjectModel(String leagueName,ArrayList<IConference> conferences, ArrayList<IPlayer>freeAgents){
        this.leagueName=leagueName;
        this.conferences=conferences;
        this.freeAgents = freeAgents;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public ArrayList<IConference> getConferences() {
        return conferences;
    }

    public void setConferences(ArrayList<IConference> conferences) {
        this.conferences = conferences;
    }

    public ArrayList<IPlayer> getFreeAgents() {
        return freeAgents;
    }

    public void setFreeAgents(ArrayList<IPlayer> freeAgents) {
        this.freeAgents = freeAgents;
    }

    public boolean checkIfLeagueModelValid(IValidation validation) throws Exception{
        validation.isStringEmpty(leagueName,"League name");
        return true;
    }

}
