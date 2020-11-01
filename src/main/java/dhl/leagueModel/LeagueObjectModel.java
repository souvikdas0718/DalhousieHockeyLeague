package dhl.leagueModel;

import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.leagueModel.interfaceModel.*;
import dhl.database.interfaceDB.ILeagueObjectModelDB;

import java.util.ArrayList;
import java.util.List;

public class LeagueObjectModel implements ILeagueObjectModel {
    public String leagueName;
    public List<IConference> conferences;
    public List<IPlayer>freeAgents;
    public List<ICoach> coaches;
    public List <IGeneralManager> managers;
    public IGameConfig gameConfig;

    public LeagueObjectModel(){
        leagueName="";
        conferences=new ArrayList<>();
        freeAgents = new ArrayList<>();
    }

    public LeagueObjectModel(String leagueName, List<IConference> conferences, List<IPlayer>freeAgents, List<ICoach> coaches, List<IGeneralManager> managers, IGameConfig gameConfig){
        this.leagueName=leagueName;
        this.conferences=conferences;
        this.freeAgents=freeAgents;
        this.coaches=coaches;
        this.managers=managers;
        this.gameConfig=gameConfig;
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

    public IGameConfig getGameConfig() {
        return gameConfig;
    }

    public List<IGeneralManager> getGeneralManagers() {
        return managers;
    }

    public boolean checkIfLeagueModelValid(IValidation validation,ILeagueObjectModelValidation leagueObjectModelValidation) throws Exception{
        return leagueObjectModelValidation.checkIfLeagueObjectModelValid(validation,this);
    }

    public ILeagueObjectModel saveLeagueObjectModel(ILeagueObjectModelDB leagueDatabase, ILeagueObjectModelInput saveLeagueInput) throws Exception{
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

    public ILeagueObjectModel loadLeagueObjectModel(ILeagueObjectModelDB leagueDatabase, String leagueName, String teamName) throws Exception{
        return leagueDatabase.loadLeagueModel(leagueName,teamName);
    }

}
