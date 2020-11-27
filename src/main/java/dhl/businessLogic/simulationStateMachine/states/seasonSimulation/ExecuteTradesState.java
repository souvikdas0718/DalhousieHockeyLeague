package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;


import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.interfaces.ISimulationSeasonState;
import dhl.businessLogic.trade.interfaces.ITradingEngine;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;

public class ExecuteTradesState implements ISimulationSeasonState {

    SimulationContext simulationContext;
    IGameConfig gameConfig;
    ILeagueObjectModel leagueObjectModel;
    IUserInputOutput ioObject;
    ITradingEngine tradeEngine;
//    private ITeam userTeam;

    public ExecuteTradesState(SimulationContext simulationContext) {

        this.simulationContext = simulationContext;
        leagueObjectModel = simulationContext.getInMemoryLeague();
        gameConfig = simulationContext.getGameConfig();
//        userTeam = simulationContext.getUserTeam();
        ioObject = simulationContext.getIoObject();
        tradeEngine = simulationContext.getTradeEngine();
    }

    public SimulationContext getSimulationContext() {
        return simulationContext;
    }

    public void setSimulationContext(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
    }

    @Override
    public void seasonStateProcess() {
        tradeEngine.startEngine();
    }

    @Override
    public void seasonStateExitProcess() {
        simulationContext.setCurrentSimulation(simulationContext.getAging());
    }

}

