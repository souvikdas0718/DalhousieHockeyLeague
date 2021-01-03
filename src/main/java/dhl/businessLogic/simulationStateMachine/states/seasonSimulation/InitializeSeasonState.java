package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;


import dhl.businessLogic.leagueModel.PlayerDraftAbstract;
import dhl.businessLogic.leagueModel.Team;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.IConference;
import dhl.businessLogic.leagueModel.interfaceModel.IDivision;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.factory.SchedulerAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.interfaces.ISimulationSeasonState;
import dhl.businessLogic.simulationStateMachine.states.standings.factory.StandingsAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.standings.interfaces.IStandingSystem;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.TemporalAdjusters.firstDayOfNextMonth;
import static java.time.temporal.TemporalAdjusters.nextOrSame;

public class InitializeSeasonState implements ISimulationSeasonState {
    private static final int DRAFTROUNDS = 7;
    private static final int NOOFTEAMS = 32;
    private static final int SIMULATESTARTMONTH = 9;
    private static final int SIMULATESTARTDAY = 30;
    private static final int REGULARSEASONENDMONTH = 3;
    private static final int REGULARSEASONENDDAY = 1;
    private static final int DAY = 1;
    private static final int YEAR = 1;
    public static Logger logger = LogManager.getLogger(InitializeSeasonState.class);
    ITeam[][] draftPickSequence = new Team[NOOFTEAMS][DRAFTROUNDS];
    SimulationContext simulationContext;
    IScheduler scheduler;
    SchedulerAbstractFactory schedulerAbstractFactory;
    StandingsAbstractFactory standingsAbstractFactory;
    IUserInputOutput userInputOutput;
    IStandingSystem standingSystem;
    ILeagueObjectModel leagueObjectModel;
    LeagueModelAbstractFactory leagueFactory;
    PlayerDraftAbstract playerDraft;

    public InitializeSeasonState(SimulationContext simulationContext) {
        logger.info("Into the Initialize season constructor");
        this.simulationContext = simulationContext;
        schedulerAbstractFactory = SchedulerAbstractFactory.instance();
        scheduler = schedulerAbstractFactory.getScheduler();
        standingsAbstractFactory = StandingsAbstractFactory.instance();
        standingSystem = standingsAbstractFactory.getStandingSystem();
        userInputOutput = IUserInputOutput.getInstance();
    }

    public SimulationContext getSimulationContext() {
        return simulationContext;
    }

    public void setSimulationContext(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
    }

    @Override
    public void seasonStateProcess() {
        logger.info("Into the state process of Initialize season");
        initializePlayerDraftPick();
        leagueFactory = LeagueModelAbstractFactory.instance();
        playerDraft = leagueFactory.createPlayerDraft();
        playerDraft.setDraftPickSequence(draftPickSequence);
        LocalDate simulationStartDate = LocalDate.of(simulationContext.getYear(), SIMULATESTARTMONTH, SIMULATESTARTDAY);
        simulationContext.setStartOfSimulation(simulationStartDate);
        simulationContext.setSeasonStartDate(simulationStartDate);
        scheduler.generateTeamList(simulationContext.getInMemoryLeague());
        scheduler.generateTeamSchedule(simulationContext.getInMemoryLeague());
        LocalDate localDate = LocalDate.of(simulationContext.getYear() + YEAR, REGULARSEASONENDMONTH, REGULARSEASONENDDAY);
        LocalDate regularSeasonEndDate = localDate.with(firstDayOfNextMonth())
                .with(nextOrSame(DayOfWeek.SATURDAY));
        scheduler.gameScheduleDates(simulationContext.getStartOfSimulation().plusDays(DAY), regularSeasonEndDate);
        simulationContext.setRegularScheduler(scheduler);
        standingSystem.createStandings(simulationContext.getInMemoryLeague());
    }

    @Override
    public void seasonStateExitProcess() {
        logger.info("Into the exit process of Initialize season");
        simulationContext.setCurrentSimulation(simulationContext.getAdvanceTime());
    }

    public void initializePlayerDraftPick() {
        logger.info("Initialize player draft pick");
        List<ITeam> teamsInLeague = getTeams();
        if (teamsInLeague.size() > 0) {
            for (int i = 0; i < NOOFTEAMS; i++) {
                for (int j = 0; j < DRAFTROUNDS; j++) {
                    logger.debug("Initialize player draft pick for team");
                    draftPickSequence[i][j] = teamsInLeague.get(i);
                }
            }
        }

    }

    public List<ITeam> getTeams() {
        List<ITeam> teamsInLeague = new ArrayList<>();
        logger.info("Fetching all teams in league");
        logger.debug("Fetching teams in league to initialize player draft pick 2D array");
        leagueObjectModel = simulationContext.getInMemoryLeague();
        for (IConference conference : leagueObjectModel.getConferences()) {
            for (IDivision division : conference.getDivisions()) {
                for (ITeam team : division.getTeams()) {
                    if (checkIfUserTeam(team.getTeamName())) {
                        teamsInLeague.add(team);
                    }
                }
            }
        }
        return teamsInLeague;
    }

    public boolean checkIfUserTeam(String teamName) {
        ITeam userTeam = this.simulationContext.getUserTeam();
        return !teamName.equals(userTeam.getTeamName());
    }

}