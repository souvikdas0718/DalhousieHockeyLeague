package dhl.trading;

import dhl.importJson.Interface.IGameConfig;
import dhl.leagueModel.interfaceModel.IConference;
import dhl.leagueModel.interfaceModel.IDivision;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.simulationStateMachine.Interface.ISimulationSeasonState;
import dhl.simulationStateMachine.SimulationContext;
import dhl.trading.Interface.ITradeConfig;

public class TradingController {

    IGameConfig ourGameConfig;
    ITradeConfig tradeConfig;
    ILeagueObjectModel leagueObjectModel;

    public TradingController(ILeagueObjectModel leagueObjectModel , IGameConfig gameConfig){
        this.ourGameConfig = gameConfig;
        this.leagueObjectModel = leagueObjectModel;
        // TODO: 27-10-2020 Check how can i decouple this
        this.tradeConfig = new TradeConfig(ourGameConfig);
    }

    public void startTradingProcess()  {
        try{
            for(IConference conference: leagueObjectModel.getConferences()){
                for(IDivision division : conference.getDivisions()){
                    for(ITeam team : division.getTeams()){
                        if(findLossPointOfTheTeam(team) > tradeConfig.getLossPoint()){
                            double randomNumber = Math.random();
                            if(randomNumber > tradeConfig.getRandomTradeOfferChance()){
                                // TODO: 27-10-2020 Check how can i decouple this
                                GenerateTradeOffer offerGenerator = new GenerateTradeOffer(tradeConfig);
                                offerGenerator.makeTrade(team , leagueObjectModel);
                            }
                        }
                    }
                }
            }
        }catch(Exception e){
            // TODO: 28-10-2020 check rajni class and sysout e.getmessgae();
            System.out.println(e.getMessage());
        }
    }

    public int findLossPointOfTheTeam(ITeam team){
        int teamLossPoint = team.getLossPoint();
        return  teamLossPoint;
    }

}
