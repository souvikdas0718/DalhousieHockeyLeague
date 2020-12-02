package dhl.inputOutput.importJson;

import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.inputOutput.importJson.interfaces.ICreatedLeagueValidation;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CreatedLeagueValidation implements ICreatedLeagueValidation {
    private static final Logger logger = LogManager.getLogger(CreatedLeagueValidation.class);
    IUserInputOutput inputOutput;
    IValidation validation;

    public CreatedLeagueValidation(IValidation validation) {
        logger.debug("Created League Validation Constructor");
        ImportJsonAbstractFactory factory = ImportJsonAbstractFactory.instance();
        inputOutput = factory.createUserInputOutput();
        this.validation = validation;
    }

    public boolean checkCreatedLeagueObjectModel(ILeagueObjectModel leagueObjectModel) {
        boolean validLeagueModel = true;
        outerloop:
        for (IConference conference : leagueObjectModel.getConferences()) {
            for (IDivision division : conference.getDivisions()) {
                for (ITeam team : division.getTeams()) {
                    if (checkIfConferenceValid(conference) == false || checkIfDivisionValid(division) == false || checkIfTeamValid(team) == false || validatePlayers(team.getPlayers()) == false) {
                        validLeagueModel = false;
                        break outerloop;
                    }
                }
            }
        }
        if (validateFreeAgents(leagueObjectModel.getFreeAgents()) == false) {
            inputOutput.printMessage("Free Agents list is invalid");
            validLeagueModel = false;
        }
        return validLeagueModel;
    }

    public boolean checkIfConferenceValid(IConference conference) {
        if (conference.checkIfConferenceHasEvenDivisions() == false) {
            logger.debug("Conference: " + conference.getConferenceName() + " have odd divisions");
            inputOutput.printMessage("A conference must contain even number of divisions");
            return false;
        }
        if (conference.checkIfConferenceHasUniqueDivisions() == false) {
            inputOutput.printMessage("The names of divisions inside a conference must be unique");
            return false;
        }
        return true;
    }

    public boolean checkIfDivisionValid(IDivision division) {
        if (validation.isStringEmpty(division.getDivisionName(), "Division name")) {
            logger.debug("Created Division is empty");
            inputOutput.printMessage("Division name cannot be empty");
            return false;
        }
        if (division.checkIfDivisionValid() == false) {
            inputOutput.printMessage("Division names must be unique");
            return false;
        }
        return true;
    }

    public boolean checkIfTeamValid(ITeam team) {
        if (validation.isStringEmpty(team.getTeamName(), "Team name")) {
            inputOutput.printMessage("Team name cannot be empty");
            return false;
        }
        if (team.checkIfOneCaptainPerTeam(team.getPlayers()) == false) {
            inputOutput.printMessage("There must be one captain for a team,Please select one captain for team" + team.getTeamName());
            return false;
        }
        if (team.checkIfSizeOfTeamValid(team.getPlayers()) == false) {
            inputOutput.printMessage("Each team must have 30 players: 16 forwards, 4 goalies, 10 defense");
            return false;
        }
        return checkIfCoachValid(team.getHeadCoach());
    }

    public boolean checkIfCoachValid(ICoach coach) {
        if (validation.isStringEmpty(coach.getCoachName(), "Coach name")) {
            inputOutput.printMessage("Coach name cannot be empty");
            return false;
        }
        if (checkCoachStatisticsValid(coach) == false) {
            inputOutput.printMessage("Coach Statistics are incorrect");
            return false;
        }
        return true;
    }

    public boolean checkCoachStatisticsValid(ICoach coach) {
        return !(coach.isCoachStatInvalid(coach.getSaving()) || coach.isCoachStatInvalid(coach.getChecking()) || coach.isCoachStatInvalid(coach.getShooting()) || coach.isCoachStatInvalid(coach.getSkating()));
    }


    public boolean validatePlayers(List<IPlayer> players) {
        for (IPlayer player : players) {
            if (player.isCaptainValueBoolean()) {
                inputOutput.printMessage("Captain value must be true or false for player" + player.getPlayerName());
                return false;
            }
            if (checkPlayerValid(player) == false) {
                logger.info("Player created is invalid:" + player.getPlayerName());
                return false;
            }
        }
        return true;
    }

    public boolean checkPlayerValid(IPlayer player) {
        logger.info("Checking player object created");
        if (player.isPlayerNameEmpty()) {
            inputOutput.printMessage("Player name cannot be empty");
            return false;
        }
        if (player.isPlayerPositionInvalid()) {
            inputOutput.printMessage("Player position must be goalie or forward or defense");
            return false;
        }

        IPlayerStatistics playerStats = player.getPlayerStats();
        if (checkPlayerStatisticsValid(playerStats)) {
            return true;
        } else {
            inputOutput.printMessage("Player Stats invalid ");
        }
        logger.info("Player created is invalid:" + player.getPlayerName());
        return false;
    }

    public boolean checkPlayerStatisticsValid(IPlayerStatistics playerStats) {
        return !(playerStats.isStatValueInvalid(playerStats.getSaving()) || playerStats.isStatValueInvalid(playerStats.getChecking()) || playerStats.isStatValueInvalid(playerStats.getShooting()) || playerStats.isStatValueInvalid(playerStats.getSkating()));

    }

    public boolean validateFreeAgents(List<IPlayer> freeAgents) {
        for (IPlayer player : freeAgents) {
            if (checkPlayerValid(player) == false) {
                logger.info("Free agent created is invalid:" + player.getPlayerName());
                return false;
            }
        }
        return true;
    }

}
