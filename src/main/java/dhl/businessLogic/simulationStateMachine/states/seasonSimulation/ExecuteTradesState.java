package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;


import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.interfaces.ISimulationSeasonState;
import dhl.businessLogic.trade.TradeEngineAbstract;
import dhl.businessLogic.trade.TradingEngine;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExecuteTradesState implements ISimulationSeasonState {
    public static Logger logger = LogManager.getLogger(ExecuteTradesState.class);
    SimulationContext simulationContext;
    IGameConfig gameConfig;
    ILeagueObjectModel leagueObjectModel;
    IUserInputOutput ioObject;
    TradeEngineAbstract tradeEngine;
    IUserInputOutput userInputOutput;

    public ExecuteTradesState(SimulationContext simulationContext) {
        logger.info("Into the execute trade constructor");
        this.simulationContext = simulationContext;
        leagueObjectModel = simulationContext.getInMemoryLeague();
        gameConfig = simulationContext.getGameConfig();
        ioObject = simulationContext.getIoObject();

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
        logger.info("Into the state process of Execute Trade season");
        tradeEngine = (TradingEngine) TradeEngineAbstract.instance(simulationContext.getGameConfig(), simulationContext.getInMemoryLeague(), simulationContext.getUserTeam());
        tradeEngine.startEngine();
    }

    @Override
    public void seasonStateExitProcess() {
        logger.info("Into the exit process of Execute Trade season");
        simulationContext.setCurrentSimulation(simulationContext.getAging());
    }

}

