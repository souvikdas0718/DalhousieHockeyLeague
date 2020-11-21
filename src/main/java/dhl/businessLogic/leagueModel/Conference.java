package dhl.businessLogic.leagueModel;

import dhl.businessLogic.leagueModel.interfaceModel.IConference;
import dhl.businessLogic.leagueModel.interfaceModel.IDivision;
import dhl.businessLogic.leagueModel.interfaceModel.IValidation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Conference implements IConference {

    private String conferenceName;
    private List<IDivision> divisions;
    private static final Logger logger = LogManager.getLogger(Conference.class);

    public Conference() {
        setDefaults();
    }

    public void setDefaults() {
        conferenceName = "";
        divisions = new ArrayList<>();
    }

    public Conference(String conferenceName, List<IDivision> divisions) {
        this.conferenceName = conferenceName;
        this.divisions = divisions;
    }

    public String getConferenceName() {
        return conferenceName;
    }

    public List<IDivision> getDivisions() {
        return divisions;
    }

    public boolean checkIfConferenceValid(IValidation validation) throws Exception {
        validation.isStringEmpty(conferenceName, "Conference name");
        checkIfConferenceHasEvenDivisions();
        return true;
    }

    public boolean isDivisionSizeOdd(){
        if(divisions.size() % 2 == 0){
            return false;
        }
        return true;
    }

    public void checkIfConferenceHasEvenDivisions() throws Exception {
        if (isDivisionSizeOdd()) {
            logger.error( "Conference: " + conferenceName + " have odd divisions" );
            throw new Exception("A conference must contain even number of divisions");
        }
        List<String> divisionNames = new ArrayList<>();
        divisions.stream().map(division -> division.getDivisionName()).forEach(divName -> divisionNames.add(divName));
        Set<String> divisionsSet = new HashSet<>(divisionNames);
        if (divisionsSet.size() < divisions.size()) {
            throw new Exception("The names of divisions inside a conference must be unique");
        }
    }

}
