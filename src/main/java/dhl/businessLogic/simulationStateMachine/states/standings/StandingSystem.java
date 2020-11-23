package dhl.businessLogic.simulationStateMachine.states.standings;


import dhl.businessLogic.leagueModel.interfaceModel.IConference;
import dhl.businessLogic.leagueModel.interfaceModel.IDivision;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.states.standings.interfaces.IStandingSystem;
import dhl.businessLogic.simulationStateMachine.states.standings.interfaces.IStandings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StandingSystem implements IStandingSystem {
    public List<IStandings> standingsList;

    public StandingSystem() {
        this.standingsList = new ArrayList<>();
    }

    public List<IStandings> getStandingsList() {
        return standingsList;
    }

    public void setStandingsList(List<IStandings> standingsList) {
        this.standingsList = standingsList;
    }

    public void updateWinningStandings(ITeam team) {

        for (IStandings standings : standingsList) {
            if (standings.getTeam().getTeamName().equals(team.getTeamName())) {
                standings.setPoints(standings.getPoints() + 2);
                standings.setWins(standings.getWins() + 1);
                standings.setGamesPlayed(standings.getGamesPlayed() + 1);
            }
        }
    }

    public void updateLosingStandings(ITeam team) {

        for (IStandings standings : standingsList) {
            if (standings.getTeam().getTeamName().equals(team.getTeamName())) {
                standings.setLoss(standings.getLoss() + 1);
                standings.setGamesPlayed(standings.getGamesPlayed() + 1);
            }
        }
    }

    public List<IStandings> divisionRanking(IDivision division, List<IStandings> standingsList) {
        List<IStandings> divisionStandingsList = new ArrayList<>();
        for (IStandings standings : standingsList) {
            if (standings.getTeamDivision().getDivisionName().equals(division.getDivisionName())) {
                divisionStandingsList.add(standings);
            }
        }
        rankGenerator(divisionStandingsList);
        return divisionStandingsList;
    }

    public List<IStandings> conferenceRanking(IConference conference, List<IStandings> standingsList) {
        List<IStandings> conferenceStandingsList = new ArrayList<>();
        for (IStandings standings : standingsList) {
            if (standings.getTeamConference().getConferenceName().equals(conference.getConferenceName())) {
                conferenceStandingsList.add(standings);
            }
        }
        rankGenerator(conferenceStandingsList);
        return conferenceStandingsList;
    }

    public List<IStandings> leagueRanking() {
        List<IStandings> standingsList;
        standingsList = this.standingsList;
        rankGenerator(standingsList);
        return standingsList;
    }

    public void rankGenerator(List<IStandings> rankList) {
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

}

