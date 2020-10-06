package dhl.leagueModel;

import dhl.leagueModel.interfaceModel.IConference;
import dhl.leagueModel.interfaceModel.IDivision;
import dhl.leagueModel.interfaceModel.IValidation;

import java.util.ArrayList;

public class Conference implements IConference {
    private String conferenceName;
    private ArrayList<IDivision> divisions;

    public Conference(){
      setDefaults();
    }

    public void setDefaults(){
        conferenceName="";
        divisions=new ArrayList<>();
    }

    public Conference(String conferenceName,ArrayList<IDivision> divisions){
        setConferenceName(conferenceName);
        setDivisions(divisions);
    }

    public String getConferenceName() {
        return conferenceName;
    }

    public void setConferenceName(String conferenceName) {
        this.conferenceName = conferenceName;
    }

    public ArrayList<IDivision> getDivisions() {
        return divisions;
    }

    public void setDivisions(ArrayList<IDivision> divisionList) {
        this.divisions=divisionList;
    }
    public boolean checkIfConferenceValid(IValidation validation) throws Exception{
        validation.isStringEmpty(conferenceName,"Conference name");

        return true;
    }


}
