package dhl.trade;

import dhl.importJson.Interface.IGameConfig;
import dhl.leagueModel.interfaceModel.IConference;
import dhl.leagueModel.interfaceModel.IDivision;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.trade.Interface.ITradeController;
import dhl.trade.Interface.ITradingEngine;


public class TradeController implements ITradeController {


    private ITeam userTeam;
    ILeagueObjectModel leagueObjectModel;
    IGameConfig gameConfig;
    public TradeController(ITeam userTeam, ILeagueObjectModel leagueObjectModel, IGameConfig gameConfig){
        this.gameConfig = gameConfig;
        gameConfig.setRequiredObjectFromConfig("trading");
        this.leagueObjectModel = leagueObjectModel;
        this.userTeam = userTeam;
    }

    @Override
    public void startTrading() {
        TradeConfigVariableNames tradeConfigVariableNames = new TradeConfigVariableNames();
        long configLossPoint = Long.parseLong(gameConfig.getValueFromOurObject(tradeConfigVariableNames.getLossPoint()));
        double configRandomTradeChance = Double.parseDouble(gameConfig.getValueFromOurObject(tradeConfigVariableNames.getRandomTradeOfferChance()));
        try{
            for(IConference conference: leagueObjectModel.getConferences()){
                for(IDivision division : conference.getDivisions()){
                    for(ITeam team : division.getTeams()){
                        if(findLossPointOfTheTeam(team) > configLossPoint){
                            double randomNumber = Math.random();
                            if(randomNumber >configRandomTradeChance){
                                // TODO: 27-10-2020 Check how can i decouple this
                                ITradingEngine tradeEngine = new PlayerSwappingTradeEngine(gameConfig,leagueObjectModel);
                                tradeEngine.makeOffer(team);
                                if(isTradeGenerated(tradeEngine)){
                                    tradeEngine.sendTradeToRecevingTeam(userTeam);
                                    tradeEngine.checkPlayersAfterTrade();
                                }
                            }
                        }
                    }
                }
            }
        }catch(Exception e){
            // TODO: 28-10-2020 check rajni class and sysout e.getmessgae();
            System.out.println("error");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ITeam getUserTeam() {
        return userTeam;
    }

    public int findLossPointOfTheTeam(ITeam team){
        int teamLossPoint = team.getLossPoint();
        return  teamLossPoint;
    }
    public boolean isTradeGenerated(ITradingEngine tradeEngine){
        if(tradeEngine.getCurrentTrade() == null){
            return false;
        }else{
            return true;
        }
    }
}
