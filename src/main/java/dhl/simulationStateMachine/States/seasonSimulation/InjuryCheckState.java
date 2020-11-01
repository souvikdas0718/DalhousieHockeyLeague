package dhl.simulationStateMachine.States.seasonSimulation;

import dhl.leagueModel.interfaceModel.IInjurySystem;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.simulationStateMachine.SimulationContext;

import java.util.ArrayList;

public class InjuryCheckState implements ISimulationSeasonState {
    SimulationContext simulationContext;
    public InjuryCheckState(SimulationContext simulationContext) {
    }

    @Override
    public void seasonStateEntryProcess() {

    }

    @Override
    public void seasonStateProcess()  {
        // check if players are injured
        //each team we have to check check injury method
        for(ITeam team : simulationContext.getTeamsPlayingInGame()){
            IInjurySystem injurySystem=simulationContext.getInjurySystem();
            injurySystem.checkTeamInjury(simulationContext.getGameConfig(),team);
            simulationContext.setTeamsPlayingInGame(new ArrayList<>());
        }
    }

    @Override
    public void seasonStateExitProcess() {
//        TODO ADD SAME CONDITION AS TRAINING STATE TO MOVE TO AGEING/SIMULATE GAME STATE
        // returns to the state where it checks unplayed scheduled games
    }
}
