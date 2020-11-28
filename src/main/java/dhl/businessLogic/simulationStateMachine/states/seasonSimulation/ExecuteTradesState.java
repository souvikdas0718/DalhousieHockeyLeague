package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;


import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.interfaces.ISimulationSeasonState;
import dhl.businessLogic.trade.TradingEngine;
import dhl.businessLogic.trade.interfaces.ITradingEngine;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;

public class ExecuteTradesState implements ISimulationSeasonState {

    SimulationContext simulationContext;
    IGameConfig gameConfig;
    ILeagueObjectModel leagueObjectModel;
    IUserInputOutput ioObject;
    ITradingEngine tradeEngine;
    IUserInputOutput userInputOutput;
//    private ITeam userTeam;

    public ExecuteTradesState(SimulationContext simulationContext) {

        this.simulationContext = simulationContext;
        leagueObjectModel = simulationContext.getInMemoryLeague();
        gameConfig = simulationContext.getGameConfig();
//        userTeam = simulationContext.getUserTeam();
        ioObject = simulationContext.getIoObject();
//        tradeEngine = simulationContext.getTradeEngine();

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
        tradeEngine =(TradingEngine) ITradingEngine.instance(simulationContext.getGameConfig(), simulationContext.getInMemoryLeague(), simulationContext.getUserTeam());
        userInputOutput.printMessage("Into the state process of Execute Trade season");
        tradeEngine.startEngine();
    }

    @Override
    public void seasonStateExitProcess() {
        userInputOutput.printMessage("Into the exit process of Execute Trade season");
        simulationContext.setCurrentSimulation(simulationContext.getAging());
    }

}

