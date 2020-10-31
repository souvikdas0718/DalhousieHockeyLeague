package dhl.leagueModel;

import dhl.importJson.Interface.IGameConfig;
import dhl.leagueModel.interfaceModel.*;
import dhl.database.interfaceDB.ILeagueObjectModelData;

import java.util.ArrayList;
import java.util.List;

public class LeagueObjectModel implements ILeagueObjectModel {
    public String leagueName;
    public List<IConference> conferences;
    public List<IPlayer>freeAgents;
    public List<ICoach> coaches;
    public List managers;

    public LeagueObjectModel(){
        leagueName="";
        conferences=new ArrayList<>();
        freeAgents = new ArrayList<>();
    }

    public LeagueObjectModel(String leagueName,List<IConference> conferences, List<IPlayer>freeAgents){
        this.leagueName=leagueName;
        this.conferences=conferences;
        this.freeAgents = freeAgents;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public List<IConference> getConferences() {
        return conferences;
    }

    public List<IPlayer> getFreeAgents() {
        return freeAgents;
    }

    public List<ICoach> getCoaches() {
        return coaches;
    }

    public void setCoaches(List<ICoach> coaches) {
        this.coaches = coaches;
    }

    public List getManagers() {
        return managers;
    }

    public void setManagers(List managers) {
        this.managers = managers;
    }

    public boolean checkIfLeagueModelValid(IValidation validation,ILeagueObjectModelValidation leagueObjectModelValidation) throws Exception{
        return leagueObjectModelValidation.checkIfLeagueObjectModelValid(validation,this);
    }

    public ILeagueObjectModel saveLeagueObjectModel(ILeagueObjectModelData leagueDatabase, ILeagueObjectModelInput saveLeagueInput) throws Exception{
        ILeagueObjectModelValidation leagueObjectModelValidation=saveLeagueInput.getLeagueObjectModelValidation();
        leagueObjectModelValidation.checkUserInputForLeague(this,saveLeagueInput);
        List<IConference> conferenceArrayList=this.getConferences();
        boolean newTeamAddedToLeague=false;
        for(int i=0; i< conferenceArrayList.size();i++){
            IConference conference=  conferenceArrayList.get(i);
            if(conference.getConferenceName()==saveLeagueInput.getConferenceName()){
                List<IDivision> divisionArrayList=conference.getDivisions();
                for(int j=0;j<divisionArrayList.size();j++){
                    IDivision division=divisionArrayList.get(j);
                    if(division.getDivisionName()==saveLeagueInput.getDivisionName()){
                        List<ITeam> teamArrayList =division.getTeams();
                        teamArrayList.add(saveLeagueInput.getNewlyCreatedTeam());
                        newTeamAddedToLeague=true;
                        break;
                    }
                }
                if(newTeamAddedToLeague){
                    break;
                }
            }
        }
        this.conferences=conferenceArrayList;
        leagueDatabase.insertLeagueModel(this);
        return this;
    }

    public ILeagueObjectModel loadLeagueObjectModel(ILeagueObjectModelData leagueDatabase,String leagueName,String teamName) throws Exception{
        return leagueDatabase.loadLeagueModel(leagueName,teamName);
    }

}
