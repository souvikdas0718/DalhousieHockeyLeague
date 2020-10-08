package dhl.leagueModel;

import dhl.leagueModel.interfaceModel.IDivision;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.leagueModel.interfaceModel.IValidation;

import java.util.ArrayList;
import java.util.List;

public class Division implements IDivision {
    private int divisionId;
    private String divisionName;
    private ArrayList<ITeam> teams;

    public Division(){
        setDefault();
    }

    public void setDefault(){
        divisionName="";
        teams= new ArrayList<>();
    }

    public Division(String  divisionName,ArrayList<ITeam> teamsList){
       this.divisionName=divisionName;
       this.teams=teamsList;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName=divisionName;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setTeams(ArrayList<ITeam> teamsList) {
        this.teams=teamsList;
    }

    public ArrayList<ITeam> getTeams() {
        return teams;
    }

    public boolean checkIfDivisionValid(IValidation validation) throws Exception{
        validation.isStringEmpty(divisionName,"Division name");
        return true;
    }

}
