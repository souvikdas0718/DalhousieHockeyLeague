package dhl.businessLogic.leagueModel;

import dhl.businessLogic.leagueModel.interfaceModel.IDivision;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.leagueModel.interfaceModel.IValidation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Division implements IDivision {
    private String divisionName;
    private List<ITeam> teams;
    private static final Logger logger = LogManager.getLogger(Division.class);

    public Division() {
        setDefault();
    }

    public void setDefault() {
        divisionName = "";
        teams = new ArrayList<>();
    }

    public Division(String divisionName, List<ITeam> teamsList) {
        this.divisionName = divisionName;
        this.teams = teamsList;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public List<ITeam> getTeams() {
        return teams;
    }

    public boolean checkIfDivisionValid(IValidation validation) throws Exception {
        validation.isStringEmpty(divisionName, "Division name");
        List<String> teamNames = new ArrayList<>();
        teams.stream().map(team -> team.getTeamName()).forEach(name -> teamNames.add(name));
        Set<String> teamsSet = new HashSet<>(teamNames);
        if (teamsSet.size() < teamNames.size()) {
            logger.error("Invalid division: "+ divisionName);
            throw new Exception("The names of teams inside a division must be unique");
        }

        return true;
    }

}
