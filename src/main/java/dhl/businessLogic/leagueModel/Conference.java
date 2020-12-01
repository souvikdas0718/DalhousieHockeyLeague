package dhl.businessLogic.leagueModel;

import dhl.businessLogic.leagueModel.interfaceModel.IConference;
import dhl.businessLogic.leagueModel.interfaceModel.IDivision;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Conference implements IConference {
    private static final Logger logger = LogManager.getLogger(Conference.class);
    private String conferenceName;
    private List<IDivision> divisions;

    public Conference() {
        setDefaults();
    }

    public void setDefaults() {
        conferenceName = "";
        divisions = new ArrayList<>();
    }

    public Conference(String conferenceName, List<IDivision> divisions) {
        logger.info("Creating conference object");
        this.conferenceName = conferenceName;
        this.divisions = divisions;
    }

    public String getConferenceName() {
        return conferenceName;
    }

    public List<IDivision> getDivisions() {
        return divisions;
    }

    public boolean checkIfConferenceHasEvenDivisions() {
        if (divisions.size() % 2 == 0) {
            logger.debug("Conference: " + conferenceName + " has even divisions names");
            return true;
        }
        logger.debug("Conference: " + conferenceName + " has odd divisions names");
        return false;
    }

    public boolean checkIfConferenceHasUniqueDivisions() {
        List<String> divisionNames = new ArrayList<>();
        divisions.stream().map(division -> division.getDivisionName()).forEach(divName -> divisionNames.add(divName));
        Set<String> divisionsSet = new HashSet<>(divisionNames);
        if (divisionsSet.size() < divisions.size()) {
            logger.debug("Conference: " + conferenceName + " has duplicate names");
            return false;
        }
        logger.debug("Conference: " + conferenceName + " has unique names");
        return true;
    }

}
