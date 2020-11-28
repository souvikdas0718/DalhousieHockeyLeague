package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;

import dhl.businessLogic.leagueModel.interfaceModel.IConference;
import dhl.businessLogic.leagueModel.interfaceModel.IDivision;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.factory.SimulationStateAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.interfaces.IGenerateDraftPlayers;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.interfaces.ISimulationSeasonState;
import dhl.businessLogic.simulationStateMachine.states.standings.interfaces.IStandings;

import java.util.ArrayList;
import java.util.List;

public class PlayerDraftState implements ISimulationSeasonState {
    private static final int DRAFTROUNDS = 7;
    private static final int NOOFTEAMS = 32;
    IGenerateDraftPlayers generateDraftPlayers;
    SimulationContext simulationContext;
    SimulationStateAbstractFactory simulationFactory;
    List<String> teamsInLeague;
    ITeam userTeam;
    String[][] draftPickSequence = new String[NOOFTEAMS][DRAFTROUNDS];

    public PlayerDraftState(SimulationContext simulationContext){
        this.simulationContext=simulationContext;
        simulationFactory = SimulationStateAbstractFactory.instance();
        generateDraftPlayers = simulationFactory.getGeneratePlayers() ;
        teamsInLeague = new ArrayList<>();
        userTeam = this.simulationContext.getUserTeam();
        initializePlayerDraftPick();
    }

    public void initializePlayerDraftPick(){
        getTeams();
        for (int i = 0; i < NOOFTEAMS; i++) {
            for (int j = 0; j < DRAFTROUNDS; j++) {
                draftPickSequence[i][j]=teamsInLeague.get(i);
            }
        }
    }

    public void seasonStateEntryProcess() {
// TODO: 28-11-2020 Remove method
    }

    public String[][] getDraftPickSequence() {
        return draftPickSequence;
    }

    public void setDraftPickSequence(String[][] draftPickSequence) {
        this.draftPickSequence = draftPickSequence;
    }

    public List<String> getTeamsInLeague() {
        return teamsInLeague;
    }

    public void seasonStateProcess() {
        List<IStandings> standings= simulationContext.getStandings();
        List<String> reversedStandings = reverseStandingOrder(standings);
        for(int i =0; i < DRAFTROUNDS; i++){
            for(String teamName : reversedStandings){

            }
        }


    }

    public List<String> fetchTeamsInRound(int draftRoundNo){
        List<String> teamsInRound = new ArrayList<>();
        for(int i=0; i <NOOFTEAMS; i++){
            String teamName= draftPickSequence[i][draftRoundNo];
            teamsInRound.add(teamName);
        }
        return teamsInRound;
    }

    public List<String> reverseStandingOrder(List<IStandings> standings){
        List<String> reversedStandings = new ArrayList<>();
        for (int i = standings.size() - 1; i >= 0; i--) {
            IStandings teamStanding = standings.get(i);
            ITeam team = teamStanding.getTeam();
            reversedStandings.add(team.getTeamName());
        }
        return reversedStandings;

    }



    public void getTeams(){
        ILeagueObjectModel leagueObjectModel = simulationContext.getInMemoryLeague();
        for(IConference conference:leagueObjectModel.getConferences()){
            for(IDivision division:conference.getDivisions()){
                for(ITeam team:division.getTeams()){
                    if(checkIfUserTeam(team.getTeamName())){
                        teamsInLeague.add(team.getTeamName());
                    }
                }
            }
        }
    }

    public boolean checkIfUserTeam(String teamName){
        return !teamName.equals(userTeam.getTeamName());

    }

    public void seasonStateExitProcess() {

    }
}
