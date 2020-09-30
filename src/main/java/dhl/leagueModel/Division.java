package dhl.leagueModel;

import dhl.leagueModel.interfaceModel.IDivision;

public class Division implements IDivision {
    private int divisionId;
    private String divisionName;

    public Division(){
        setDefault();
    }
    public Division(String  divisionName){
       this.divisionName=divisionName;
    }

    public void setDefault(){
        divisionName="";
    }

    public void setDivisionName(String divisionName) {
        this.divisionName=divisionName;
    }

    public String getDivisionName() {
        return divisionName;
    }
}
