package dhl.businessLogic.leagueModel;

import dhl.businessLogic.leagueModel.interfaceModel.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LeagueObjectModelValidation implements ILeagueObjectModelValidation {
    private static final Logger logger = LogManager.getLogger(LeagueObjectModelValidation.class);

    public boolean checkIfLeagueObjectModelValid(IValidation validation, ILeagueObjectModel leagueObjectModel) {
        logger.info("Checking League Object Model");
        boolean leagueNameEmpty = validation.isStringEmpty(leagueObjectModel.getLeagueName(), "League");
        return leagueNameEmpty == false && this.checkIfLeagueDetailsValid(leagueObjectModel.getConferences());
    }

    public boolean isConferenceSizeOdd(List<IConference> conferences) {
        logger.info("Checking Size of conference");
        if (conferences.size() % 2 == 0) {
            logger.debug("Conferences List in League Object Model is empty");
            return false;
        }
        return true;
    }

    public boolean isLeagueNameDifferent(String leagueName, String userInputLeagueName) {
        if (leagueName.equals(userInputLeagueName)) {
            logger.debug("Entered League Name does not match available options" + leagueName);
            return false;
        }
        return true;
    }

    public boolean checkIfLeagueDetailsValid(List<IConference> conferences) {
        logger.info("Checking League Conference Details");
        if (isConferenceSizeOdd(conferences)) {
            logger.debug("League does not contain contain even number of conferences");
            return false;
        }
        List<String> conferenceNames = new ArrayList<>();
        conferences.stream().map(conference -> conference.getConferenceName()).forEach(confName -> conferenceNames.add(confName));
        Set<String> conferenceSet = new HashSet<>(conferenceNames);
        if (conferenceSet.size() < conferences.size()) {
            logger.debug("Names of conferences inside a league is not unique");
            return false;
        }
        return true;
    }

    public boolean checkUserInputForLeague(ILeagueObjectModel leagueObjectModel, ILeagueObjectModelInput leagueObjectModelInput) {
        if (isLeagueNameDifferent(leagueObjectModel.getLeagueName(), leagueObjectModelInput.getLeagueName())) {
            logger.debug("League name is not present in file imported.");
            return false;
        }

        List<IConference> conferenceList = leagueObjectModel.getConferences().stream().filter((IConference conference) -> leagueObjectModelInput.getConferenceName().equals(conference.getConferenceName())).collect(Collectors.toList());
        if (conferenceList.size() == 0) {
            logger.debug("Conference name is not present in file imported");
            return false;
        }
        IConference selectedConference = conferenceList.get(0);
        List<IDivision> divisionList = selectedConference.getDivisions().stream().filter((IDivision division) -> leagueObjectModelInput.getDivisionName().equals(division.getDivisionName())).collect(Collectors.toList());
        if (divisionList.size() == 0) {
            logger.debug("Division name is not present in file imported");
            return false;
        }
        IDivision selectedDivision = divisionList.get(0);
        ITeam newTeam = leagueObjectModelInput.getNewlyCreatedTeam();
        List<ITeam> teamList = selectedDivision.getTeams().stream().filter((ITeam team) -> newTeam.getTeamName() == team.getTeamName()).collect(Collectors.toList());
        if (teamList.size() > 0) {
            logger.debug("Team name entered is already present in file imported");
            return false;
        }
        return true;
    }


}
