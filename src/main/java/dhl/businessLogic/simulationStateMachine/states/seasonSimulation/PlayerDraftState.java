package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;

import dhl.businessLogic.leagueModel.Team;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogic.teamRosterUpdater.RosterUpdaterAbstractFactory;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.teamRosterUpdater.interfaces.ITeamRosterUpdater;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.factory.SimulationStateAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.interfaces.IGenerateDraftPlayers;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.interfaces.ISimulationSeasonState;
import dhl.businessLogic.simulationStateMachine.states.standings.interfaces.IStandings;
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
    private static final int COUNTERSTARTVALUE=0;
    private static final Logger logger = LogManager.getLogger(PlayerDraftState.class);
    IGenerateDraftPlayers generateDraftPlayers;
    SimulationContext simulationContext;
    SimulationStateAbstractFactory simulationFactory;
    LeagueModelAbstractFactory leagueFactory;
    List<ITeam> teamsInLeague;
    ITeam userTeam;
    ITeam [][] draftPickSequence = new Team[NOOFTEAMS][DRAFTROUNDS];
    ILeagueObjectModel leagueObjectModel;
    IPlayerDraft playerDraft;

    public PlayerDraftState(SimulationContext simulationContext){
        logger.info("Player draft Constructor initialized");
        this.simulationContext=simulationContext;
        simulationFactory = SimulationStateAbstractFactory.instance();
        generateDraftPlayers = simulationFactory.getGeneratePlayers() ;
        teamsInLeague = new ArrayList<>();
        userTeam = this.simulationContext.getUserTeam();
        initializePlayerDraftPick();
        leagueFactory = LeagueModelAbstractFactory.instance();
        playerDraft = leagueFactory.createPlayerDraft();
        playerDraft.setDraftPickSequence(draftPickSequence);
    }

    public void seasonStateProcess() {
        logger.info("Player Draft season state process");
        draftPickSequence = playerDraft.getDraftPickSequence();
        List<IStandings> standings= simulationContext.getStandings();
        List<ITeam> reversedStandings = reverseStandingOrder(standings);
        int lastUpdatedIndex = 0;
        for(int draftRound =0; draftRound < DRAFTROUNDS; draftRound++){
            logger.debug("Draft Round");
            logger.debug(draftRound);
            for(int sequence =0; sequence < reversedStandings.size();sequence++){
                ITeam currentTeam = reversedStandings.get(sequence);
                logger.debug("Current Team"+currentTeam.getTeamName());
                Map<Integer,ITeam> teamsInCurrentRound = fetchTeamsInDraftRound(draftRound);
                List<Integer> currentTeamsPosition = getDraftPositionsCurrentTeam(teamsInCurrentRound,currentTeam);
                for(int i=0;i<currentTeamsPosition.size();i++){
                    ITeam teamAtIndex = draftPickSequence[lastUpdatedIndex][draftRound];
                    draftPickSequence[lastUpdatedIndex][draftRound] = currentTeam;
                    draftPickSequence[currentTeamsPosition.get(i)-1][draftRound] = teamAtIndex;
                    lastUpdatedIndex=i+1;
                    logger.debug("Last updated index in player draft pick");
                    logger.debug(lastUpdatedIndex);
                }
            }
        }
    }

    public void seasonStateExitProcess() {
        logger.info("Player Draft Season State Exit process");
        addDraftPlayersToTeam();
        RosterUpdaterAbstractFactory rosterFactory = RosterUpdaterAbstractFactory.instance();
        ITeamRosterUpdater rosterUpdater = rosterFactory.createAiTeamRosterUpdater();
        for(IConference conference:leagueObjectModel.getConferences()){
            for(IDivision division:conference.getDivisions()){
                for(ITeam team:division.getTeams()){
                    rosterUpdater.validateTeamRoster(team,leagueObjectModel);
                }
            }
        }
    }


    public void initializePlayerDraftPick(){
        logger.info("Initialize player draft pick");
        getTeams();
        if(teamsInLeague.size()>0){
            for (int i = 0; i < NOOFTEAMS; i++) {
                for (int j = 0; j < DRAFTROUNDS; j++) {
                    logger.debug("Initialize player draft pick for team");
                    draftPickSequence[i][j]=teamsInLeague.get(i);
                }
            }
        }

    }

    public ITeam[][] getDraftPickSequence() {
        return draftPickSequence;
    }

    public List<ITeam> getTeamsInLeague() {
        return teamsInLeague;
    }

    public Map<Integer,ITeam> fetchTeamsInDraftRound(int draftRoundNo){
        logger.info("Creating hash map to track sequence of picks in current draft round");
        Map<Integer,ITeam> currentDraftPickSequence = new HashMap<>();
        for(int i=0; i <NOOFTEAMS; i++){
            ITeam teamName= draftPickSequence[i][draftRoundNo];
            currentDraftPickSequence.put(i+1,teamName);
        }
        return currentDraftPickSequence;
    }

    public List<Integer> getDraftPositionsCurrentTeam(Map<Integer,ITeam> teamsInCurrentRound,ITeam currentTeam){
        logger.debug("Current Team"+currentTeam.getTeamName() +"at positions:");
        List<Integer> currentTeamsPosition = new ArrayList<>();
        for (Entry<Integer, ITeam> entry : teamsInCurrentRound.entrySet()) {
            ITeam team = entry.getValue();
            if (team.getTeamName().equals(currentTeam.getTeamName())) {
                currentTeamsPosition.add(entry.getKey());
            }
        }
        return currentTeamsPosition;
    }

    public List<ITeam> reverseStandingOrder(List<IStandings> standings){
        logger.info("Reverse Standing Order: Worst team picks first");
        List<ITeam> reversedStandings = new ArrayList<>();
        for (int i = standings.size() - 1; i >= 0; i--) {
            IStandings teamStanding = standings.get(i);
            ITeam team = teamStanding.getTeam();
            reversedStandings.add(team);
        }
        return reversedStandings;

    }

    public void getTeams(){
        logger.info("Fetching all teams in league");
        logger.debug("Fetching teams in league to initialize player draft pick 2D array");
        leagueObjectModel = simulationContext.getInMemoryLeague();
        if(isLeagueNotNull()){
            for(IConference conference:leagueObjectModel.getConferences()){
                for(IDivision division:conference.getDivisions()){
                    for(ITeam team:division.getTeams()){
                        if(checkIfUserTeam(team.getTeamName())){
                            teamsInLeague.add(team);
                        }
                    }
                }
            }
        }
    }

    public boolean isLeagueNotNull(){
        if(leagueObjectModel == null){
            return false;
        }
        return true;
    }

    public boolean checkIfUserTeam(String teamName){
        return !teamName.equals(userTeam.getTeamName());
    }

    public void addDraftPlayersToTeam(){
        List<IPlayer> draftPlayers = generateDraftPlayers.generateDraftPlayers();
        int counter =COUNTERSTARTVALUE;
        for(int i=0; i<DRAFTROUNDS; i++){
            for(int j=0; j<NOOFTEAMS; j++){
               ITeam team = draftPickSequence[j][i];
               List<IPlayer> players = team.getPlayers();
               players.add(draftPlayers.get(counter));
               counter=counter+1;
            }
        }
    }

}