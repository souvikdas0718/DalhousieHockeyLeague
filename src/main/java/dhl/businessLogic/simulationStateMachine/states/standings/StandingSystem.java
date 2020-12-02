package dhl.businessLogic.simulationStateMachine.states.standings;


import dhl.businessLogic.leagueModel.interfaceModel.IConference;
import dhl.businessLogic.leagueModel.interfaceModel.IDivision;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.states.standings.factory.StandingsAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.standings.interfaces.IStandingSystem;
import dhl.businessLogic.simulationStateMachine.states.standings.interfaces.IStandings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StandingSystem implements IStandingSystem {
    public static Logger logger = LogManager.getLogger(StandingSystem.class);
    public List<IStandings> standingsList;
    StandingsAbstractFactory standingsAbstractFactory;

    public StandingSystem() {
        this.standingsList = new ArrayList<>();
        standingsAbstractFactory = StandingsAbstractFactory.instance();
    }

    public List<IStandings> getStandingsList() {
        return standingsList;
    }

    public void setStandingsList(List<IStandings> standingsList) {
        this.standingsList = standingsList;
    }

    public void updateWinningStandings(ITeam team) {
        logger.debug("Into update winning standings method for team: " + team.getTeamName());
        for (IStandings standings : standingsList) {
            if (standings.getTeam().getTeamName().equals(team.getTeamName())) {
                standings.setPoints(standings.getPoints() + 2);
                standings.setWins(standings.getWins() + 1);
                standings.setGamesPlayed(standings.getGamesPlayed() + 1);
            }
        }
    }

    public void updateLosingStandings(ITeam team) {
        logger.debug("Into update losing standings method for team: " + team.getTeamName());
        for (IStandings standings : standingsList) {
            if (standings.getTeam().getTeamName().equals(team.getTeamName())) {
                standings.setLoss(standings.getLoss() + 1);
                standings.setGamesPlayed(standings.getGamesPlayed() + 1);
            }
        }
    }

    public List<IStandings> divisionRanking(IDivision division, List<IStandings> standingsList) {
        logger.debug("Into division ranking method");
        List<IStandings> divisionStandingsList = new ArrayList<>();
        for (IStandings standings : standingsList) {
            if (standings.getTeamDivision().getDivisionName().equals(division.getDivisionName())) {
                divisionStandingsList.add(standings);
            }
        }
        logger.debug("calling rank generator method for division");
        rankGenerator(divisionStandingsList);
        return divisionStandingsList;
    }

    public List<IStandings> conferenceRanking(IConference conference, List<IStandings> standingsList) {
        logger.debug("Into conference ranking method");
        List<IStandings> conferenceStandingsList = new ArrayList<>();
        for (IStandings standings : standingsList) {
            if (standings.getTeamConference().getConferenceName().equals(conference.getConferenceName())) {
                conferenceStandingsList.add(standings);
            }
        }
        logger.debug("calling rank generator method for conference");
        rankGenerator(conferenceStandingsList);
        return conferenceStandingsList;
    }

    public List<IStandings> leagueRanking() {
        logger.debug("Into league ranking method");
        List<IStandings> standingsList;
        standingsList = this.standingsList;
        rankGenerator(standingsList);
        return standingsList;
    }

    public void rankGenerator(List<IStandings> rankList) {
        logger.debug("Into rank generator method");
        Collections.sort(rankList, (IStandings objectOne, IStandings objectTwo) -> {
            if (objectOne.getPoints() == objectTwo.getPoints()) {
                if (objectOne.getWins() == objectTwo.getWins()) {
                    return Integer.compare(objectTwo.getLoss(), objectOne.getLoss());
                } else {
                    return Integer.compare(objectTwo.getWins(), objectOne.getWins());
                }
            } else {
                return Integer.compare(objectTwo.getPoints(), objectOne.getPoints());
            }
        });
    }

    public void createStandings(ILeagueObjectModel leagueObjectModel) {
        logger.debug("Into creaStandings method for generating regular sason default standings");
        for (IConference conference : leagueObjectModel.getConferences()) {
            for (IDivision division : conference.getDivisions()) {
                for (ITeam team : division.getTeams()) {
                    IStandings standings = standingsAbstractFactory.getStandings();
                    standings.setTeamConference(conference);
                    standings.setTeamDivision(division);
                    standings.setTeam(team);
                    standings.setPoints(0);
                    standings.setWins(0);
                    standings.setLoss(0);
                    standings.setGamesPlayed(0);
                    standingsList.add(standings);
                }
            }
        }
    }

}

