package dhl.leagueModel;

import dhl.leagueModel.interfaceModel.IConference;
import dhl.leagueModel.interfaceModel.IDivision;
import dhl.leagueModel.interfaceModel.IValidation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        checkIfConferenceHasEvenDivisions();
        return true;
    }

    public void checkIfConferenceHasEvenDivisions() throws Exception{
        if(divisions!=null && (divisions.size()%2!=0)){
            throw new Exception("A conference must contain even number of divisions");
        }
        List<String> divisionNames=new ArrayList<>();
        divisions.stream().map(division-> division.getDivisionName()).forEach(divName->divisionNames.add(divName));
        Set<String> divisionsSet = new HashSet<>(divisionNames);
        if (divisionsSet.size() < divisions.size()) {
            throw new Exception("The names of divisions inside a conference must be unique");
        }
    }

}
