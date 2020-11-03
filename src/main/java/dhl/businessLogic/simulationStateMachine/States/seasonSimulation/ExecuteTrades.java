package dhl.businessLogic.simulationStateMachine.States.seasonSimulation;

import dhl.InputOutput.UI.IUserInputOutput;
import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.trade.Interface.ITradingEngine;

public class ExecuteTrades implements ISimulationSeasonState {

    SimulationContext simulationContextObject;
    IGameConfig gameConfig;
    private ITeam userTeam;
    ILeagueObjectModel leagueObjectModel;
    IUserInputOutput ioObject;
    ITradingEngine tradeEngine;

    public ExecuteTrades(SimulationContext simulationContext) {

        this.simulationContextObject = simulationContext;
        leagueObjectModel = simulationContextObject.getInMemoryLeague();
        gameConfig = simulationContextObject.getGameConfig();
        userTeam = simulationContextObject.getUserTeam();
        ioObject = simulationContextObject.getIoObject();
        tradeEngine = simulationContext.getTradeEngine();
    }

    @Override
    public void seasonStateEntryProcess() {

    }

    @Override
    public void seasonStateProcess() {
        tradeEngine.startEngine();
    }

    @Override
    public void seasonStateExitProcess() {
        simulationContextObject.setCurrentSimulation(simulationContextObject.getAging());
    }

    public int findLossPointOfTheTeam(ITeam team) {
        int teamLossPoint = team.getLossPoint();
        return teamLossPoint;
    }

}
