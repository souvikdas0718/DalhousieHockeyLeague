package dhl.trading;

import dhl.importJson.Interface.IGameConfig;
import dhl.leagueModel.interfaceModel.IConference;
import dhl.leagueModel.interfaceModel.IDivision;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.simulationStateMachine.SimulationContext;
import dhl.trading.Interface.ITradeConfig;

public class Trading {

    IGameConfig ourGameConfig;
    ITradeConfig tradeConfig;
    ILeagueObjectModel leagueObjectModel;

    public Trading(SimulationContext simulationObject){
        ourGameConfig = simulationObject.getGameConfig();
        leagueObjectModel = simulationObject.getInMemoryLeague();
        // TODO: 27-10-2020 Check new Tradeconfig part 
        tradeConfig = new TradeConfig(ourGameConfig);
    }

    public void startTrading() throws Exception {
        // TODO: 27-10-2020 iterate teams and check if they can init a trade offer
        for(IConference conference: leagueObjectModel.getConferences()){
            for(IDivision division : conference.getDivisions()){
                for(ITeam team : division.getTeams()){
                    if(findLossPointOfTheTeam(team) > tradeConfig.getLossPoint()){
                        if(Math.random() > tradeConfig.getRandomTradeOfferChance()){
                            // TODO: 27-10-2020 Check how can i decouple this
                            GenerateTradeOffer offerGenerator = new GenerateTradeOffer(tradeConfig);
                            offerGenerator.makeTrade(team , leagueObjectModel);
                        }
                    }
                }
            }
        }
    }

    public int findLossPointOfTheTeam(ITeam team){

        // TODO: 27-10-2020 Talk to team about getting loss points
        return 0;
    }


}
