package dhl.businessLogic.simulationStateMachine.states.seasonSimulation;


import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.interfaces.ISimulationSeasonState;
import dhl.businessLogic.trade.interfaces.ITradingEngine;
import dhl.inputOutput.ui.IUserInputOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExecuteTradesState implements ISimulationSeasonState {

    private static Logger log = LogManager.getLogger(ExecuteTradesState.class);

    SimulationContext simulationContext;
    IGameConfig gameConfig;
    ILeagueObjectModel leagueObjectModel;
    IUserInputOutput ioObject;
    ITradingEngine tradeEngine;
    private ITeam userTeam;

    public ExecuteTradesState(SimulationContext simulationContext) {

        this.simulationContext = simulationContext;
        leagueObjectModel = this.simulationContext.getInMemoryLeague();
        gameConfig = this.simulationContext.getGameConfig();
        userTeam = this.simulationContext.getUserTeam();
        ioObject = this.simulationContext.getIoObject();
        tradeEngine = simulationContext.getTradeEngine();
    }

    public SimulationContext getSimulationContext() {
        return simulationContext;
    }

    public void setSimulationContext(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
    }

    @Override
    public void seasonStateEntryProcess() {

    }

    @Override
    public void seasonStateProcess() {
        long configLossPoint = Long.parseLong(gameConfig.getValueFromOurObject(gameConfig.getTrading(), gameConfig.getLossPoint()));
        double configRandomTradeChance = Double.parseDouble(gameConfig.getValueFromOurObject(gameConfig.getTrading(), gameConfig.getRandomTradeOfferChance()));
        try {
            for (IConference conference : leagueObjectModel.getConferences()) {
                for (IDivision division : conference.getDivisions()) {
                    for (ITeam team : division.getTeams()) {
                        if (findLossPointOfTheTeam(team) > configLossPoint) {
                            double randomNumber = Math.random();
                            if (randomNumber > configRandomTradeChance) {
                                tradeEngine.startEngine();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error occured while trading"+ e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void seasonStateExitProcess() {
        simulationContext.setCurrentSimulation(simulationContext.getAging());
    }

    public ITeam getUserTeam() {
        return userTeam;
    }

    public int findLossPointOfTheTeam(ITeam team) {
        int teamLossPoint = team.getLossPoint();
        return teamLossPoint;
    }

}

