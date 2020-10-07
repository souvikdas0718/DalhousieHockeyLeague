package dhl.leagueModel;

import dhl.leagueModel.interfaceModel.*;
import dhl.leagueModelData.ILeagueObjectModelData;

import java.util.*;
import java.util.stream.Collectors;

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
        checkIfLeagueHasEvenConferences();
        return true;
    }
    public void checkIfLeagueHasEvenConferences() throws Exception{
        if(conferences!=null && conferences.size()%2!=0){
            throw new Exception("A League must contain even number of conferences");
        }
        List<String> conferenceNames=new ArrayList<>();
        conferences.stream().map(conference-> conference.getConferenceName()).forEach(confName->conferenceNames.add(confName));
        Set<String> conferenceSet = new HashSet<>(conferenceNames);
        if (conferenceSet.size() < conferences.size()) {
            throw new Exception("The names of conferences inside a league must be unique");
        }
    }

    public ILeagueObjectModel createTeam(ILeagueObjectModelData leagueDatabase, String leagueName, String conferenceName, String divisionName, String teamName, String generalManager, String headCoach) throws Exception{
        checkUserInputForCreateTeams(this,leagueName,conferenceName,divisionName,teamName);
        ArrayList<IPlayer> players= new ArrayList<>();
        ITeam newlyCreatedTeam=new Team(teamName,generalManager,headCoach,players);
        ArrayList<IConference> conferenceArrayList=this.getConferences();
        boolean teamAdded=false;
        for(int i=0; i< conferenceArrayList.size();i++){
            IConference conference=  conferenceArrayList.get(i);
            if(conference.getConferenceName()==conferenceName){
                ArrayList<IDivision> divisionArrayList=conference.getDivisions();
                for(int j=0;j<divisionArrayList.size();j++){
                    IDivision division=divisionArrayList.get(i);
                    if(division.getDivisionName()==divisionName){
                        ArrayList<ITeam> teamArrayList =division.getTeams();
                        teamArrayList.add(newlyCreatedTeam);
                        division.setTeams(teamArrayList);
                        divisionArrayList.set(j,division);
                        teamAdded=true;
                        break;
                    }
                }
                if(teamAdded){
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

    public boolean checkUserInputForCreateTeams(ILeagueObjectModel leagueObjectModel,String leagueName, String conferenceName, String divisionName, String teamName)throws Exception{
        if(leagueName != leagueObjectModel.getLeagueName()){
            throw new Exception("League name is not present in file imported.");
        }
        if(leagueObjectModel.getConferences()!=null){
            List<IConference> conferenceList=leagueObjectModel.getConferences().stream().filter((IConference conference)->
            {return conferenceName.equals(conference.getConferenceName());
            }).collect(Collectors.toList());
            if(conferenceList.size()==0){
                throw new Exception("Conference name is not present in file imported");
            }
            IConference selectedConference=conferenceList.get(0);
            List<IDivision> divisionList=selectedConference.getDivisions().stream().filter((IDivision division)->{
                return divisionName.equals(division.getDivisionName());
            }).collect(Collectors.toList());
            if(divisionList.size()==0){
                throw new Exception("Division name is not present in file imported");
            }
            IDivision  selectedDivision=divisionList.get(0);
            List<ITeam> teamList=selectedDivision.getTeams().stream().filter((ITeam team)->{return teamName==team.getTeamName();}).collect(Collectors.toList());
            if(teamList.size()!=0){
                throw new Exception("Team name entered is already present in file imported");
            }
        }
        return  true;
    }

    public ILeagueObjectModel loadTeam(ILeagueObjectModelData leagueDatabase,String leagueName,String conferenceName,String divisionName,String teamName){
        return leagueDatabase.loadLeagueModel(leagueName,conferenceName,divisionName,teamName);
    }


}
