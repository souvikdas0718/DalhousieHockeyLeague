package dhl.simulationStateMachineTest.seasonScheduler;

import dhl.leagueModel.Conference;
import dhl.leagueModel.Division;
import dhl.leagueModel.LeagueObjectModel;
import dhl.leagueModel.Team;
import dhl.leagueModel.interfaceModel.IConference;
import dhl.leagueModel.interfaceModel.IDivision;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.simulationStateMachine.Interface.ISchedule;
import dhl.simulationStateMachine.Interface.IStandings;
import dhl.simulationStateMachine.States.seasonScheduler.Scheduler;
import dhl.simulationStateMachine.States.standings.Standings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class SchedulerTest {
//    @Test
//    public void getFullSeasonSchedule() {
//
//    }
//
//    @Test
//    public void setFullSeasonSchedule(List<ISchedule> fullSeasonSchedule) {
//
//    }
//
//    @Test
//    public List<ISchedule> getPlayOffSchedule() {
//        return null;
//    }
//
//    @Test
//    public void setPlayOffSchedule(List<ISchedule> playOffSchedule) {
//
//    }
//
//    @Test
//    public LocalDate getSeasonStartDate() {
//        return null;
//    }
//
//    @Test
//    public void setSeasonStartDate(LocalDate seasonStartDate) {
//
//    }
//
//    @Test
//    public LocalDate getSeasonEndDate() {
//        return null;
//    }
//
//    @Test
//    public void setSeasonEndDate(LocalDate seasonEndDate) {
//
//    }
//
//    @Test
//    public LocalDate getPlayOffStartDate() {
//        return null;
//    }
//
//    @Test
//    public void setPlayOffStartDate(LocalDate playOffStartDate) {
//
//    }
//
//    @Test
//    public LocalDate getFinalDay() {
//        return null;
//    }
//
//    @Test
//    public void setFinalDay(LocalDate finalDay) {
//
//    }
//
//    @Test
//    public List<ITeam> getTeamList() {
//        return null;
//    }
//
//    @Test
//    public void setTeamList(List<ITeam> teamList) {
//
//    }
//
//    @Test
//    public List<IConference> getConferences() {
//        return null;
//    }
//
//    @Test
//    public void setConferences(List<IConference> conferences) {
//
//    }
//
//    @Test
//    public List<IDivision> getDivisions() {
//        return null;
//    }
//
//    @Test
//    public void setDivisions(List<IDivision> divisions) {
//
//    }


//    Scheduler scheduler = new Scheduler();
//    LocalDate dt = LocalDate.now();
//    LocalDate localDate = LocalDate.of(2021,03,01);
//
//
//    @Test
//    public void gameScheduleDatesTest() {
//        scheduler.gameScheduleDates(dt, localDate);
//        long noOfDaysInRegularSeason = ChronoUnit.DAYS.between(dt, localDate);
//        System.out.println("Days difference: "+ noOfDaysInRegularSeason);
//    }

    @Test
    public void playOffsTest() {

        ArrayList<IStandings> standings = new ArrayList<>();

        ILeagueObjectModel league = new LeagueObjectModel();

        IConference conference1 = new Conference();
        conference1.setConferenceName("Eastern Conference");

        IConference conference2 = new Conference();
        conference2.setConferenceName("Western Conference");

        IDivision division1 = new Division();
        division1.setDivisionName("Atlantic Division");

        IDivision division2 = new Division();
        division2.setDivisionName("Metropolitan Division");

        IDivision division3 = new Division();
        division3.setDivisionName("Central Division");

        IDivision division4 = new Division();
        division4.setDivisionName("Pacific Division");

        ITeam team1 = new Team();
        team1.setTeamName("Bruins");

        IStandings standings1 = new Standings();
        standings1.setTeamConference(conference1);
        standings1.setTeamDivision(division1);
        standings1.setTeamName(team1);
        standings1.setPoints(94);
        standings1.setWins(47);
        standings1.setLoss(3);

        ITeam team2 = new Team();
        team2.setTeamName("Lightning");

        IStandings standings2 = new Standings();
        standings2.setTeamConference(conference1);
        standings2.setTeamDivision(division1);
        standings2.setTeamName(team2);
        standings2.setPoints(82);
        standings2.setWins(41);
        standings2.setLoss(9);

        ITeam team3 = new Team();
        team3.setTeamName("Maple");

        IStandings standings3 = new Standings();
        standings3.setTeamConference(conference1);
        standings3.setTeamDivision(division1);
        standings3.setTeamName(team3);
        standings3.setPoints(80);
        standings3.setWins(40);
        standings3.setLoss(10);

        ITeam team4 = new Team();
        team4.setTeamName("Panthers");

        IStandings standings4 = new Standings();
        standings4.setTeamConference(conference1);
        standings4.setTeamDivision(division1);
        standings4.setTeamName(team4);
        standings4.setPoints(76);
        standings4.setWins(38);
        standings4.setLoss(12);

        ITeam team5 = new Team();
        team5.setTeamName("Canadiens");

        IStandings standings5 = new Standings();
        standings5.setTeamConference(conference1);
        standings5.setTeamDivision(division1);
        standings5.setTeamName(team5);
        standings5.setPoints(70);
        standings5.setWins(35);
        standings5.setLoss(15);

        ITeam team6 = new Team();
        team6.setTeamName("Capitals");

        IStandings standings6 = new Standings();
        standings6.setTeamConference(conference1);
        standings6.setTeamDivision(division2);
        standings6.setTeamName(team6);
        standings6.setPoints(92);
        standings6.setWins(46);
        standings6.setLoss(4);

        ITeam team7 = new Team();
        team7.setTeamName("Flyers");

        IStandings standings7 = new Standings();
        standings7.setTeamConference(conference1);
        standings7.setTeamDivision(division2);
        standings7.setTeamName(team7);
        standings7.setPoints(88);
        standings7.setWins(44);
        standings7.setLoss(6);

        ITeam team8 = new Team();
        team8.setTeamName("Penguins");

        IStandings standings8 = new Standings();
        standings8.setTeamConference(conference1);
        standings8.setTeamDivision(division2);
        standings8.setTeamName(team8);
        standings8.setPoints(82);
        standings8.setWins(41);
        standings8.setLoss(9);

        ITeam team9 = new Team();
        team9.setTeamName("Hurricanes");

        IStandings standings9 = new Standings();
        standings9.setTeamConference(conference1);
        standings9.setTeamDivision(division2);
        standings9.setTeamName(team9);
        standings9.setPoints(80);
        standings9.setWins(40);
        standings9.setLoss(10);

        ITeam team10 = new Team();
        team10.setTeamName("BlueJackets");

        IStandings standings10 = new Standings();
        standings10.setTeamConference(conference1);
        standings10.setTeamDivision(division2);
        standings10.setTeamName(team10);
        standings10.setPoints(78);
        standings10.setWins(39);
        standings10.setLoss(11);

        ITeam team11 = new Team();
        team11.setTeamName("Blues");

        IStandings standings11 = new Standings();
        standings11.setTeamConference(conference2);
        standings11.setTeamDivision(division3);
        standings11.setTeamName(team11);
        standings11.setPoints(90);
        standings11.setWins(45);
        standings11.setLoss(5);

        ITeam team12 = new Team();
        team12.setTeamName("Avalanche");

        IStandings standings12 = new Standings();
        standings12.setTeamConference(conference2);
        standings12.setTeamDivision(division3);
        standings12.setTeamName(team12);
        standings12.setPoints(88);
        standings12.setWins(44);
        standings12.setLoss(6);

        ITeam team13 = new Team();
        team13.setTeamName("Stars");

        IStandings standings13 = new Standings();
        standings13.setTeamConference(conference2);
        standings13.setTeamDivision(division3);
        standings13.setTeamName(team13);
        standings13.setPoints(84);
        standings13.setWins(42);
        standings13.setLoss(8);

        ITeam team14 = new Team();
        team14.setTeamName("Jets");

        IStandings standings14 = new Standings();
        standings14.setTeamConference(conference2);
        standings14.setTeamDivision(division3);
        standings14.setTeamName(team14);
        standings14.setPoints(76);
        standings14.setWins(38);
        standings14.setLoss(12);

        ITeam team15 = new Team();
        team15.setTeamName("Predators");

        IStandings standings15 = new Standings();
        standings15.setTeamConference(conference2);
        standings15.setTeamDivision(division3);
        standings15.setTeamName(team15);
        standings15.setPoints(72);
        standings15.setWins(36);
        standings15.setLoss(14);

        ITeam team16 = new Team();
        team16.setTeamName("Golden");


        IStandings standings16 = new Standings();
        standings16.setTeamConference(conference2);
        standings16.setTeamDivision(division4);
        standings16.setTeamName(team16);
        standings16.setPoints(88);
        standings16.setWins(44);
        standings16.setLoss(6);

        ITeam team17 = new Team();
        team17.setTeamName("Oilers");

        IStandings standings17 = new Standings();
        standings17.setTeamConference(conference2);
        standings17.setTeamDivision(division4);
        standings17.setTeamName(team17);
        standings17.setPoints(86);
        standings17.setWins(43);
        standings17.setLoss(7);

        ITeam team18 = new Team();
        team18.setTeamName("Flames");

        IStandings standings18 = new Standings();
        standings18.setTeamConference(conference2);
        standings18.setTeamDivision(division4);
        standings18.setTeamName(team18);
        standings18.setPoints(82);
        standings18.setWins(41);
        standings18.setLoss(9);

        ITeam team19 = new Team();
        team19.setTeamName("Cancuks");

        IStandings standings19 = new Standings();
        standings19.setTeamConference(conference2);
        standings19.setTeamDivision(division4);
        standings19.setTeamName(team19);
        standings19.setPoints(78);
        standings19.setWins(39);
        standings19.setLoss(11);

        ITeam team20 = new Team();
        team20.setTeamName("Coyotes");

        IStandings standings20 = new Standings();
        standings20.setTeamConference(conference2);
        standings20.setTeamDivision(division4);
        standings20.setTeamName(team20);
        standings20.setPoints(74);
        standings20.setWins(37);
        standings20.setLoss(13);

        standings.add(standings1);
        standings.add(standings2);
        standings.add(standings3);
        standings.add(standings4);
        standings.add(standings5);
        standings.add(standings6);
        standings.add(standings7);
        standings.add(standings8);
        standings.add(standings9);
        standings.add(standings10);
        standings.add(standings11);
        standings.add(standings12);
        standings.add(standings13);
        standings.add(standings14);
        standings.add(standings15);
        standings.add(standings16);
        standings.add(standings17);
        standings.add(standings18);
        standings.add(standings19);
        standings.add(standings20);

        ArrayList<IConference> conferences = new ArrayList<>();
        ArrayList<IDivision> conference1Divisions = new ArrayList<>();
        ArrayList<IDivision> conference2Divisions = new ArrayList<>();

        ArrayList<ITeam> teamsListDivision1 = new ArrayList<>();
        ArrayList<ITeam> teamsListDivision2 = new ArrayList<>();
        ArrayList<ITeam> teamsListDivision3 = new ArrayList<>();
        ArrayList<ITeam> teamsListDivision4 = new ArrayList<>();

        teamsListDivision1.add(team1);
        teamsListDivision1.add(team2);
        teamsListDivision1.add(team3);
        teamsListDivision1.add(team4);
        teamsListDivision1.add(team5);
        division1.setTeams(teamsListDivision1);
        conference1Divisions.add(division1);

        teamsListDivision2.add(team6);
        teamsListDivision2.add(team7);
        teamsListDivision2.add(team8);
        teamsListDivision2.add(team9);
        teamsListDivision2.add(team10);
        division2.setTeams(teamsListDivision2);
        conference1Divisions.add(division2);

        teamsListDivision3.add(team11);
        teamsListDivision3.add(team12);
        teamsListDivision3.add(team13);
        teamsListDivision3.add(team14);
        teamsListDivision3.add(team15);
        division3.setTeams(teamsListDivision3);
        conference2Divisions.add(division3);

        teamsListDivision4.add(team16);
        teamsListDivision4.add(team17);
        teamsListDivision4.add(team18);
        teamsListDivision4.add(team19);
        teamsListDivision4.add(team20);
        division4.setTeams(teamsListDivision4);
        conference2Divisions.add(division4);

        conference1.setDivisions(conference1Divisions);
        conference2.setDivisions(conference2Divisions);

        conferences.add(conference1);
        conferences.add(conference2);

        league.setConferences(conferences);
        Scheduler scheduler = new Scheduler();
        scheduler.playOffs(standings, league);
        int matchNumber = 1;
        for (ISchedule schedules : scheduler.getPlayOffSchedule()) {
            System.out.println("Match Number "+ matchNumber + ":- team 1: "+schedules.getTeamOne().getTeamName() + " VS team 2: "+schedules.getTeamTwo().getTeamName() );
            if(matchNumber == 1){
                Assertions.assertTrue(schedules.getTeamOne().getTeamName().equals("Bruins"));
                Assertions.assertTrue(schedules.getTeamTwo().getTeamName().equals("BlueJackets"));
            }
            else if(matchNumber == 5){
                Assertions.assertTrue(schedules.getTeamOne().getTeamName().equals("Blues"));
                Assertions.assertTrue(schedules.getTeamTwo().getTeamName().equals("Jets"));
            }
            matchNumber = matchNumber + 1;
        }
    }
}
