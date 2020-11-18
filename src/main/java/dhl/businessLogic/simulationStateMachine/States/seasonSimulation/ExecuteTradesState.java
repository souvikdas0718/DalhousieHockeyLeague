package dhl.businessLogic.simulationStateMachine.States.seasonSimulation;


import dhl.InputOutput.UI.IUserInputOutput;
import dhl.InputOutput.UI.UserInputOutput;
import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.IConference;
import dhl.businessLogic.leagueModel.interfaceModel.IDivision;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.trade.Interface.ITradingEngine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExecuteTradesState implements ISimulationSeasonState {

    private static Logger log = LogManager.getLogger(ExecuteTradesState.class);

    SimulationContext simulationContextObject;
    IGameConfig gameConfig;
    ILeagueObjectModel leagueObjectModel;
    IUserInputOutput ioObject;
    ITradingEngine tradeEngine;
    private ITeam userTeam;

    public ExecuteTradesState(SimulationContext simulationContext) {

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
        simulationContextObject.setCurrentSimulation(simulationContextObject.getAging());
    }

    public ITeam getUserTeam() {
        return userTeam;
    }

    public int findLossPointOfTheTeam(ITeam team) {
        int teamLossPoint = team.getLossPoint();
        return teamLossPoint;
    }

}

