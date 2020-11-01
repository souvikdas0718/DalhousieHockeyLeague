package dhl.leagueModel;

import dhl.leagueModel.interfaceModel.IDivision;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.leagueModel.interfaceModel.IValidation;

import java.util.*;
import java.util.List;

public class Division implements IDivision {
    private String divisionName;
    private List<ITeam> teams;

    public Division(){
        setDefault();
    }

    public void setDefault(){
        divisionName="";
        teams= new ArrayList<>();
    }

    public Division(String  divisionName,List<ITeam> teamsList){
       this.divisionName=divisionName;
       this.teams=teamsList;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public List<ITeam> getTeams() {
        return teams;
    }

    public boolean checkIfDivisionValid(IValidation validation) throws Exception{
        validation.isStringEmpty(divisionName,"Division name");
        List<String> teamNames=new ArrayList<>();
        teams.stream().map(team-> team.getTeamName()).forEach(name->teamNames.add(name));
        Set<String> teamsSet = new HashSet<>(teamNames);
        if (teamsSet.size() < teamNames.size()) {
            throw new Exception("The names of teams inside a division must be unique");
        }

        return true;
    }

}
