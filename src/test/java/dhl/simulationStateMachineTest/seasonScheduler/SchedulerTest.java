package dhl.simulationStateMachineTest.seasonScheduler;

import dhl.InputOutput.importJson.GameConfig;
import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.Mocks.GameConfigMock;
import dhl.leagueModel.*;
import dhl.leagueModel.interfaceModel.*;
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

        String generalManager = "generalManager";

        ICoach headCoach = new Coach("Coach", 0.1, 0.2, .5, .9);
        ICoach headCoach2 = new Coach("Coach2", 0.1, 0.2, .5, .8);
        List<ICoach> coaches = new ArrayList<>();
        coaches.add(headCoach);
        coaches.add(headCoach2);
        IPlayerStatistics playerStats = new PlayerStatistics(20, 5, 5, 8 , 9);
        IPlayer playersList1 = new Player("playerName", "position",playerStats);
        IPlayer playersList2 = new Player("playerName", "position",playerStats);
        List<IPlayer> playersList = new ArrayList<>();
        playersList.add(playersList1);
        playersList.add(playersList2);

        ITeam team1 = new Team("Bruins", generalManager , headCoach , playersList);
        ITeam team2 = new Team("Lightning", generalManager , headCoach , playersList);
        ITeam team3 = new Team("Maple", generalManager , headCoach , playersList);
        ITeam team4 = new Team("Panthers", generalManager , headCoach , playersList);
        ITeam team5 = new Team("Canadiens", generalManager , headCoach , playersList);

        ITeam team6 = new Team("Capitals", generalManager , headCoach , playersList);
        ITeam team7 = new Team("Flyers", generalManager , headCoach , playersList);
        ITeam team8 = new Team("Penguins", generalManager , headCoach , playersList);
        ITeam team9 = new Team("Hurricanes", generalManager , headCoach , playersList);
        ITeam team10 = new Team("BlueJackets", generalManager , headCoach , playersList);

        ITeam team11 = new Team("Blues", generalManager , headCoach , playersList);
        ITeam team12 = new Team("Avalanche", generalManager , headCoach , playersList);
        ITeam team13 = new Team("Stars", generalManager , headCoach , playersList);
        ITeam team14 = new Team("Jets", generalManager , headCoach , playersList);
        ITeam team15 = new Team("Predators", generalManager , headCoach , playersList);

        ITeam team16 = new Team("Golden", generalManager , headCoach , playersList);
        ITeam team17 = new Team("Oilers", generalManager , headCoach , playersList);
        ITeam team18 = new Team("Flames", generalManager , headCoach , playersList);
        ITeam team19 = new Team("Cancuks", generalManager , headCoach , playersList);
        ITeam team20 = new Team("Coyotes", generalManager , headCoach , playersList);


        ArrayList<ITeam> teamsListDivision1 = new ArrayList<>();
        ArrayList<ITeam> teamsListDivision2 = new ArrayList<>();
        ArrayList<ITeam> teamsListDivision3 = new ArrayList<>();
        ArrayList<ITeam> teamsListDivision4 = new ArrayList<>();

        teamsListDivision1.add(team1);
        teamsListDivision1.add(team2);
        teamsListDivision1.add(team3);
        teamsListDivision1.add(team4);
        teamsListDivision1.add(team5);

        teamsListDivision2.add(team6);
        teamsListDivision2.add(team7);
        teamsListDivision2.add(team8);
        teamsListDivision2.add(team9);
        teamsListDivision2.add(team10);

        teamsListDivision3.add(team11);
        teamsListDivision3.add(team12);
        teamsListDivision3.add(team13);
        teamsListDivision3.add(team14);
        teamsListDivision3.add(team15);

        teamsListDivision4.add(team16);
        teamsListDivision4.add(team17);
        teamsListDivision4.add(team18);
        teamsListDivision4.add(team19);
        teamsListDivision4.add(team20);

        IDivision division1 = new Division("Atlantic Division", teamsListDivision1);
        IDivision division2 = new Division("Metropolitan Division", teamsListDivision2);
        IDivision division3 = new Division("Central Division", teamsListDivision3);
        IDivision division4 = new Division("Pacific Division", teamsListDivision4);

        ArrayList<IDivision> conference1Divisions = new ArrayList<>();
        ArrayList<IDivision> conference2Divisions = new ArrayList<>();

        conference1Divisions.add(division1);
        conference1Divisions.add(division2);
        conference2Divisions.add(division3);
        conference2Divisions.add(division4);

        IConference conference1 = new Conference("Eastern Conference",conference1Divisions);
        IConference conference2 = new Conference("Western Conference",conference2Divisions);

        List<IConference> conferences = new ArrayList<>();
        conferences.add(conference1);
        conferences.add(conference2);

        IStandings standings1 = new Standings();
        IStandings standings2 = new Standings();
        IStandings standings3 = new Standings();
        IStandings standings4 = new Standings();
        IStandings standings5 = new Standings();
        IStandings standings6 = new Standings();
        IStandings standings7 = new Standings();
        IStandings standings8 = new Standings();
        IStandings standings9 = new Standings();
        IStandings standings10 = new Standings();
        IStandings standings11 = new Standings();
        IStandings standings12 = new Standings();
        IStandings standings13 = new Standings();
        IStandings standings14 = new Standings();
        IStandings standings15 = new Standings();
        IStandings standings16 = new Standings();
        IStandings standings17 = new Standings();
        IStandings standings18 = new Standings();
        IStandings standings19 = new Standings();
        IStandings standings20 = new Standings();

        standings1.setTeamConference(conference1);
        standings1.setTeamDivision(division1);
        standings1.setTeamName(team1);
        standings1.setPoints(94);
        standings1.setWins(47);
        standings1.setLoss(3);




        standings2.setTeamConference(conference1);
        standings2.setTeamDivision(division1);
        standings2.setTeamName(team2);
        standings2.setPoints(82);
        standings2.setWins(41);
        standings2.setLoss(9);



        standings3.setTeamConference(conference1);
        standings3.setTeamDivision(division1);
        standings3.setTeamName(team3);
        standings3.setPoints(80);
        standings3.setWins(40);
        standings3.setLoss(10);


        standings4.setTeamConference(conference1);
        standings4.setTeamDivision(division1);
        standings4.setTeamName(team4);
        standings4.setPoints(76);
        standings4.setWins(38);
        standings4.setLoss(12);


        standings5.setTeamConference(conference1);
        standings5.setTeamDivision(division1);
        standings5.setTeamName(team5);
        standings5.setPoints(70);
        standings5.setWins(35);
        standings5.setLoss(15);


        standings6.setTeamConference(conference1);
        standings6.setTeamDivision(division2);
        standings6.setTeamName(team6);
        standings6.setPoints(92);
        standings6.setWins(46);
        standings6.setLoss(4);


        standings7.setTeamConference(conference1);
        standings7.setTeamDivision(division2);
        standings7.setTeamName(team7);
        standings7.setPoints(88);
        standings7.setWins(44);
        standings7.setLoss(6);



        standings8.setTeamConference(conference1);
        standings8.setTeamDivision(division2);
        standings8.setTeamName(team8);
        standings8.setPoints(82);
        standings8.setWins(41);
        standings8.setLoss(9);


        standings9.setTeamConference(conference1);
        standings9.setTeamDivision(division2);
        standings9.setTeamName(team9);
        standings9.setPoints(80);
        standings9.setWins(40);
        standings9.setLoss(10);


        standings10.setTeamConference(conference1);
        standings10.setTeamDivision(division2);
        standings10.setTeamName(team10);
        standings10.setPoints(78);
        standings10.setWins(39);
        standings10.setLoss(11);


        standings11.setTeamConference(conference2);
        standings11.setTeamDivision(division3);
        standings11.setTeamName(team11);
        standings11.setPoints(90);
        standings11.setWins(45);
        standings11.setLoss(5);


        standings12.setTeamConference(conference2);
        standings12.setTeamDivision(division3);
        standings12.setTeamName(team12);
        standings12.setPoints(88);
        standings12.setWins(44);
        standings12.setLoss(6);


        standings13.setTeamConference(conference2);
        standings13.setTeamDivision(division3);
        standings13.setTeamName(team13);
        standings13.setPoints(84);
        standings13.setWins(42);
        standings13.setLoss(8);



        standings14.setTeamConference(conference2);
        standings14.setTeamDivision(division3);
        standings14.setTeamName(team14);
        standings14.setPoints(76);
        standings14.setWins(38);
        standings14.setLoss(12);


        standings15.setTeamConference(conference2);
        standings15.setTeamDivision(division3);
        standings15.setTeamName(team15);
        standings15.setPoints(72);
        standings15.setWins(36);
        standings15.setLoss(14);


        standings16.setTeamConference(conference2);
        standings16.setTeamDivision(division4);
        standings16.setTeamName(team16);
        standings16.setPoints(88);
        standings16.setWins(44);
        standings16.setLoss(6);



        standings17.setTeamConference(conference2);
        standings17.setTeamDivision(division4);
        standings17.setTeamName(team17);
        standings17.setPoints(86);
        standings17.setWins(43);
        standings17.setLoss(7);



        standings18.setTeamConference(conference2);
        standings18.setTeamDivision(division4);
        standings18.setTeamName(team18);
        standings18.setPoints(82);
        standings18.setWins(41);
        standings18.setLoss(9);


        standings19.setTeamConference(conference2);
        standings19.setTeamDivision(division4);
        standings19.setTeamName(team19);
        standings19.setPoints(78);
        standings19.setWins(39);
        standings19.setLoss(11);


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

        GameConfigMock gameConfig = new GameConfigMock();

        IGeneralManager iGeneralManager = new GeneralManager("manager1");
        IGeneralManager iGeneralManager2 = new GeneralManager("manager2");
        List<IGeneralManager>  iGeneralManagers = new ArrayList<>();
        iGeneralManagers.add(iGeneralManager);
        iGeneralManagers.add(iGeneralManager2);

        ILeagueObjectModel league = new LeagueObjectModel("Dalhousie Hockey League", conferences, playersList, coaches,  iGeneralManagers, gameConfig.getGameConfigMock());

        Scheduler scheduler = new Scheduler();
        scheduler.playOffs(standings, league);
        int matchNumber = 1;
        for (ISchedule schedules : scheduler.getPlayOffScheduleRound1()) {
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
