package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;

import dhl.businessLogic.leagueModel.PlayerDraftAbstract;
import dhl.businessLogic.leagueModel.Team;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.factory.SimulationStateAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.interfaces.ISimulationSeasonState;
import dhl.businessLogic.simulationStateMachine.states.standings.factory.StandingsAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.standings.interfaces.IStandingSystem;
import dhl.businessLogic.simulationStateMachine.states.standings.interfaces.IStandings;
import dhl.businessLogic.teamRosterUpdater.RosterUpdaterAbstractFactory;
import dhl.businessLogic.teamRosterUpdater.interfaces.ITeamRosterUpdater;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class PlayerDraftState implements ISimulationSeasonState {
    private static final int DRAFTROUNDS = 7;
    private static final int NOOFTEAMS = 32;
    private static final int COUNTERSTARTVALUE = 0;
    private static final Logger logger = LogManager.getLogger(PlayerDraftState.class);
    IGenerateDraftPlayers generateDraftPlayers;
    SimulationContext simulationContext;
    SimulationStateAbstractFactory simulationFactory;
    LeagueModelAbstractFactory leagueFactory;
    ITeam[][] draftPickSequence = new Team[NOOFTEAMS][DRAFTROUNDS];
    ILeagueObjectModel leagueObjectModel;
    PlayerDraftAbstract playerDraft;
    IStandingSystem standingSystem;
    StandingsAbstractFactory standingsAbstractFactory;

    public PlayerDraftState(SimulationContext simulationContext) {
        logger.info("Player draft Constructor initialized");
        this.simulationContext = simulationContext;
        simulationFactory = SimulationStateAbstractFactory.instance();
        generateDraftPlayers = simulationFactory.getGeneratePlayers();
        leagueFactory = LeagueModelAbstractFactory.instance();
        playerDraft = leagueFactory.createPlayerDraft();
        playerDraft.setDraftPickSequence(draftPickSequence);
        standingsAbstractFactory = StandingsAbstractFactory.instance();
        standingSystem = standingsAbstractFactory.getStandingSystem();
    }

    public void seasonStateProcess() {
        logger.info("Player Draft season state process");
        draftPickSequence = playerDraft.getDraftPickSequence();
        leagueObjectModel = simulationContext.getInMemoryLeague();
        List<IStandings> standings = standingSystem.getStandingsList();
        List<ITeam> reversedStandings = reverseStandingOrder(standings);
        int lastUpdatedIndex = 0;
        for (int draftRound = 0; draftRound < DRAFTROUNDS; draftRound++) {
            logger.debug("Draft Round");
            logger.debug(draftRound);
            for (int sequence = 0; sequence < reversedStandings.size(); sequence++) {
                ITeam currentTeam = reversedStandings.get(sequence);
                logger.debug("Current Team" + currentTeam.getTeamName());
                Map<Integer, ITeam> teamsInCurrentRound = fetchTeamsInDraftRound(draftRound);
                List<Integer> currentTeamsPosition = getDraftPositionsCurrentTeam(teamsInCurrentRound, currentTeam);
                for (int i = 0; i < currentTeamsPosition.size(); i++) {
                    ITeam teamAtIndex = draftPickSequence[lastUpdatedIndex][draftRound];
                    draftPickSequence[lastUpdatedIndex][draftRound] = currentTeam;
                    draftPickSequence[currentTeamsPosition.get(i) - 1][draftRound] = teamAtIndex;
                    lastUpdatedIndex = i + 1;
                    logger.debug("Last updated index in player draft pick");
                    logger.debug(lastUpdatedIndex);
                }
            }
        }
    }

    public void setLeagueObjectModel(ILeagueObjectModel leagueObjectModel) {
        this.leagueObjectModel = leagueObjectModel;
    }

    public void seasonStateExitProcess() {
        logger.info("Player Draft Season State Exit process");
        addDraftPlayersToTeam();
        RosterUpdaterAbstractFactory rosterFactory = RosterUpdaterAbstractFactory.instance();
        ITeamRosterUpdater rosterUpdater = rosterFactory.createAiTeamRosterUpdater();
        for (IConference conference : leagueObjectModel.getConferences()) {
            for (IDivision division : conference.getDivisions()) {
                for (ITeam team : division.getTeams()) {
                    rosterUpdater.validateTeamRoster(team, leagueObjectModel);
                }
            }
        }
        logger.debug("Draft state completed. So, advancing to next Season");
        simulationContext.setCurrentSimulation(simulationContext.getAdvanceToNextSeason());
    }

    public ITeam[][] getDraftPickSequence() {
        return draftPickSequence;
    }

    public void setDraftPickSequence(ITeam[][] draftPickSequence) {
        this.draftPickSequence = draftPickSequence;
    }

    public Map<Integer, ITeam> fetchTeamsInDraftRound(int draftRoundNo) {
        logger.info("Creating hash map to track sequence of picks in current draft round");
        Map<Integer, ITeam> currentDraftPickSequence = new HashMap<>();
        for (int i = 0; i < NOOFTEAMS; i++) {
            ITeam team = draftPickSequence[i][draftRoundNo];
            currentDraftPickSequence.put(i + 1, team);
        }
        return currentDraftPickSequence;
    }

    public List<Integer> getDraftPositionsCurrentTeam(Map<Integer, ITeam> teamsInCurrentRound, ITeam currentTeam) {
        logger.debug("Current Team" + currentTeam.getTeamName() + "at positions:");
        List<Integer> currentTeamsPosition = new ArrayList<>();
        for (Entry<Integer, ITeam> entry : teamsInCurrentRound.entrySet()) {
            ITeam team = entry.getValue();
            if (isValuePresent(team)) {
                if (team.getTeamName().equals(currentTeam.getTeamName())) {
                    currentTeamsPosition.add(entry.getKey());
                }
            }

        }
        return currentTeamsPosition;
    }

    public boolean isValuePresent(ITeam team) {
        if (team == null) {
            return false;
        }
        return true;
    }

    public List<ITeam> reverseStandingOrder(List<IStandings> standings) {
        logger.info("Reverse Standing Order: Worst team picks first");
        List<ITeam> reversedStandings = new ArrayList<>();
        for (int i = standings.size() - 1; i >= 0; i--) {
            IStandings teamStanding = standings.get(i);
            ITeam team = teamStanding.getTeam();
            reversedStandings.add(team);
        }
        return reversedStandings;

    }

    public void addDraftPlayersToTeam() {
        List<IPlayer> draftPlayers = generateDraftPlayers.generateDraftPlayers();
        int counter = COUNTERSTARTVALUE;
        for (int i = 0; i < DRAFTROUNDS; i++) {
            for (int j = 0; j < NOOFTEAMS; j++) {
                ITeam team = draftPickSequence[j][i];
                List<IPlayer> players = team.getPlayers();
                players.add(draftPlayers.get(counter));
                counter = counter + 1;
            }
        }
    }

}