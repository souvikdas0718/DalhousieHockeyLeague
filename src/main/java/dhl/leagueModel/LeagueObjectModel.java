package dhl.leagueModel;

import dhl.leagueModel.interfaceModel.*;
import dhl.leagueModelData.ILeagueObjectModelData;
import java.util.ArrayList;

public class LeagueObjectModel implements ILeagueObjectModel {
    public String leagueName;
    public ArrayList<IConference> conferences;
    public ArrayList<IPlayer>freeAgents;
    public ILeagueObjectModelValidation leagueValidation;
    public LeagueObjectModel(){
        leagueName="";
        conferences=new ArrayList<>();
        freeAgents = new ArrayList<>();
        leagueValidation=new LeagueObjectModelValidation();
    }

    public LeagueObjectModel(String leagueName,ArrayList<IConference> conferences, ArrayList<IPlayer>freeAgents){
        this.leagueName=leagueName;
        this.conferences=conferences;
        this.freeAgents = freeAgents;
        leagueValidation=new LeagueObjectModelValidation();
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
        return leagueValidation.checkIfLeagueObjectModelValid(validation,this);
    }

    public ILeagueObjectModel saveLeagueObjectModel(ILeagueObjectModelData leagueDatabase, String leagueName, String conferenceName, String divisionName,  ITeam newlyCreatedTeam) throws Exception{
        leagueValidation.checkUserInputForLeague(this,leagueName,conferenceName,divisionName, newlyCreatedTeam.getTeamName());
        ArrayList<IConference> conferenceArrayList=this.getConferences();
        boolean newTeamAddedToLeague=false;
        for(int i=0; i< conferenceArrayList.size();i++){
            IConference conference=  conferenceArrayList.get(i);
            if(conference.getConferenceName()==conferenceName){
                ArrayList<IDivision> divisionArrayList=conference.getDivisions();
                for(int j=0;j<divisionArrayList.size();j++){
                    IDivision division=divisionArrayList.get(j);
                    if(division.getDivisionName()==divisionName){
                        ArrayList<ITeam> teamArrayList =division.getTeams();
                        teamArrayList.add(newlyCreatedTeam);
                        division.setTeams(teamArrayList);
                        divisionArrayList.set(j,division);
                        newTeamAddedToLeague=true;
                        break;
                    }
                }
                if(newTeamAddedToLeague){
                    conference.setDivisions(divisionArrayList);
                    conferenceArrayList.set(i,conference);
                    break;
                }
            }
        }
        this.setConferences(conferenceArrayList);
        leagueDatabase.insertLeagueModel(this);
        return this;
    }

    public ILeagueObjectModel loadLeagueObjectModel(ILeagueObjectModelData leagueDatabase,String leagueName,String teamName) throws Exception{
        return leagueDatabase.loadLeagueModel(leagueName,teamName);
    }

}
