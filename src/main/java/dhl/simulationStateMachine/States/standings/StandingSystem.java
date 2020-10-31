package dhl.simulationStateMachine.States.standings;

import dhl.leagueModel.interfaceModel.IConference;
import dhl.leagueModel.interfaceModel.IDivision;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.simulationStateMachine.Interface.IStandingSystem;
import dhl.simulationStateMachine.Interface.IStandings;

import java.util.Collections;
import java.util.List;

public class StandingSystem implements IStandingSystem {
    private List<IStandings> standingsList;

    public StandingSystem(List<IStandings> standingsList) {
        this.standingsList = standingsList;
    }

    public List<IStandings> getStandingsList() {
        return standingsList;
    }

    public void setStandingsList(List<IStandings> standingsList) {
        this.standingsList = standingsList;
    }

    public void updateWinningStandings(ITeam team) {

        for (IStandings standings : standingsList) {
            if (standings.getTeamName().equals(team.getTeamName())) {
                standings.setPoints(standings.getPoints() + 2);
                standings.setWins(standings.getWins() + 1);
                standings.setGamesPlayed(standings.getGamesPlayed() + 1);
            }
        }
    }

    public void updateLosingStandings(ITeam team) {

        for (IStandings standings : standingsList) {
            if (standings.getTeamName().equals(team.getTeamName())) {
                standings.setLoss(standings.getLoss() + 1);
                standings.setGamesPlayed(standings.getGamesPlayed() + 1);
            }
        }
    }

    public List<IStandings> divisionRanking(IDivision division) {
        List<IStandings> divisionStandingsList = null;
        List<IStandings> updatedDivisionStandingsList = null;
        for (IStandings standings : standingsList) {
            if (standings.getTeamDivision().equals(division.getDivisionName())) {
                divisionStandingsList.add(standings);
            }
        }
        updatedDivisionStandingsList = rankGenerator(divisionStandingsList);
        return updatedDivisionStandingsList;

    }

    public List<IStandings> conferenceRanking(IConference conference) {
        List<IStandings> conferenceStandingsList = null;
        List<IStandings> updatedConferenceStandingsList = null;
        for (IStandings standings : standingsList) {
            if (standings.getTeamConference().equals(conference.getConferenceName())) {
                conferenceStandingsList.add(standings);
            }
        }
        updatedConferenceStandingsList = rankGenerator(conferenceStandingsList);
        return updatedConferenceStandingsList;
    }

    public List<IStandings> leagueRanking() {
        List<IStandings> leagueStandingsList = null;
        leagueStandingsList = rankGenerator(standingsList);
        return leagueStandingsList;
    }

    public List<IStandings> rankGenerator(List<IStandings> rankList) {
        Collections.sort(rankList, (IStandings objectOne, IStandings objectTwo) -> {
            if (Integer.compare(objectOne.getPoints(), objectTwo.getPoints()) != 0) {
                return Integer.compare(objectOne.getPoints(), objectTwo.getPoints());
            } else {
                if (Integer.compare(objectOne.getWins(), objectTwo.getWins()) != 0) {
                    return Integer.compare(objectOne.getWins(), objectTwo.getWins());
                } else {
                        return Integer.compare(objectOne.getLoss(), objectTwo.getLoss());
                }
            }
        });
        Collections.sort(rankList, Collections.reverseOrder());
        return rankList;
    }
}
