package dhl.trade;

import dhl.InputOutput.UI.IUserInputOutput;
import dhl.InputOutput.UI.UserInputOutput;
import dhl.InputOutput.importJson.ConfigVariableNames;
import dhl.InputOutput.importJson.Interface.IGameConfig;
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
    IUserInputOutput ioObject;

    public TradeController(ITeam userTeam, ILeagueObjectModel leagueObjectModel, IGameConfig gameConfig){
        this.gameConfig = gameConfig;
        this.leagueObjectModel = leagueObjectModel;
        this.userTeam = userTeam;
        ioObject = new UserInputOutput();
    }

    @Override
    public void startTrading() {
        ConfigVariableNames configVariableNames = new ConfigVariableNames();
        long configLossPoint = Long.parseLong(gameConfig.getValueFromOurObject(configVariableNames.getTrading(),configVariableNames.getLossPoint()));
        double configRandomTradeChance = Double.parseDouble(gameConfig.getValueFromOurObject(configVariableNames.getTrading(),configVariableNames.getRandomTradeOfferChance()));
        ITradingEngine tradeEngine = new TradingEngine(gameConfig,leagueObjectModel,ioObject);

        try{
            for(IConference conference: leagueObjectModel.getConferences()){
                for(IDivision division : conference.getDivisions()){
                    for(ITeam team : division.getTeams()){
                        if(findLossPointOfTheTeam(team) > configLossPoint){
                            double randomNumber = Math.random();
                            if(randomNumber >configRandomTradeChance){
                                // TODO: 27-10-2020 Check how can i decouple this
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
            ioObject = new UserInputOutput();
            ioObject.printMessage("ERROR");
            ioObject.printMessage(e.getMessage());
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
