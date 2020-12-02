package dhl.businessLogic.leagueModel;

import dhl.businessLogic.leagueModel.interfaceModel.IDivision;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Division implements IDivision {
    private static final Logger logger = LogManager.getLogger(Division.class);
    private String divisionName;
    private List<ITeam> teams;

    public Division() {
        setDefault();
    }

    public void setDefault() {
        divisionName = "";
        teams = new ArrayList<>();
    }

    public Division(String divisionName, List<ITeam> teamsList) {
        setDefault();
        logger.info("Creating division with name" + divisionName);
        this.divisionName = divisionName;
        this.teams = teamsList;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public List<ITeam> getTeams() {
        return teams;
    }

    public boolean checkIfDivisionValid() {
        List<String> teamNames = new ArrayList<>();
        teams.stream().map(team -> team.getTeamName()).forEach(name -> teamNames.add(name));
        Set<String> teamsSet = new HashSet<>(teamNames);
        if (teamsSet.size() < teamNames.size()) {
            logger.error("Invalid team with duplicate team names: " + divisionName);
            return false;
        }
        return true;
    }
}
