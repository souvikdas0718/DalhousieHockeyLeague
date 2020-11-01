package dhl.simulationStateMachine.States.seasonSimulation;

import dhl.InputOutput.UI.IUserInputOutput;
import dhl.InputOutput.UI.UserInputOutput;
import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.leagueModel.interfaceModel.IConference;
import dhl.leagueModel.interfaceModel.IDivision;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.simulationStateMachine.SimulationContext;
import dhl.trade.Interface.ITradingEngine;
import dhl.trade.TradingEngine;

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
        long configLossPoint = Long.parseLong(gameConfig.getValueFromOurObject(gameConfig.getTrading(),gameConfig.getLossPoint()));
        double configRandomTradeChance = Double.parseDouble(gameConfig.getValueFromOurObject(gameConfig.getTrading(),gameConfig.getRandomTradeOfferChance()));
        try{
            for(IConference conference: leagueObjectModel.getConferences()){
                for(IDivision division : conference.getDivisions()){
                    for(ITeam team : division.getTeams()){
                        if(findLossPointOfTheTeam(team) > configLossPoint){
                            double randomNumber = Math.random();
                            if(randomNumber >configRandomTradeChance){
                                tradeEngine.performTrade();
                            }
                        }
                    }
                }
            }
        }catch(Exception e){
            ioObject = new UserInputOutput();
            ioObject.printMessage("ERROR");
            ioObject.printMessage(e.getMessage());
        }
    }

    @Override
    public void seasonStateExitProcess() {
        simulationContextObject.setCurrentSimulation(simulationContextObject.getAging());
    }

    public ITeam getUserTeam() {
        return userTeam;
    }
    public int findLossPointOfTheTeam(ITeam team){
        int teamLossPoint = team.getLossPoint();
        return  teamLossPoint;
    }

}
