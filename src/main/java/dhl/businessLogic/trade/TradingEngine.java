package dhl.businessLogic.trade;

import dhl.businessLogic.trade.factory.TradeAbstractFactory;
import dhl.businessLogic.trade.factory.TradeConcreteFactory;
import dhl.businessLogic.trade.interfaces.IScout;
import dhl.businessLogic.trade.interfaces.ITradeType;
import dhl.inputOutput.ui.IUserInputOutput;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogic.simulationStateMachine.interfaces.IUpdateUserTeamRoster;
import dhl.businessLogic.trade.interfaces.ITradeOffer;
import dhl.businessLogic.trade.interfaces.ITradingEngine;
import dhl.inputOutput.ui.UserInputOutput;

public class TradingEngine extends ITradingEngine {

    private ITradeOffer currentTrade;
    private IGameConfig gameConfig;
    private ILeagueObjectModel leagueObjectModel;
    IUserInputOutput ioObject;
    ITeam userTeam;
    IUpdateUserTeamRoster updateUserTeamRoster;
    TradeAbstractFactory factory;

    public TradingEngine(IGameConfig gameConfig, ILeagueObjectModel leagueObjectModel, ITeam userTeam) {
        factory = new TradeConcreteFactory();

        // TODO: 20-11-2020 remove these new when team make factory
        this.ioObject = new UserInputOutput();

        this.updateUserTeamRoster =  IUpdateUserTeamRoster.instance(ioObject);
        this.gameConfig = gameConfig;
        this.leagueObjectModel = leagueObjectModel;
        this.userTeam = userTeam;
    }

    public void startEngine() {
        try {
            long configLossPoint = Long.parseLong(gameConfig.getValueFromOurObject(gameConfig.getTrading(), gameConfig.getLossPoint()));
            double configRandomTradeChance = Double.parseDouble(gameConfig.getValueFromOurObject(gameConfig.getTrading(), gameConfig.getRandomTradeOfferChance()));
            for (IConference conference : leagueObjectModel.getConferences()) {
                for (IDivision division : conference.getDivisions()) {
                    for (ITeam team : division.getTeams()) {
                        if (findLossPointOfTheTeam(team) > configLossPoint) {
                            double randomNumber = Math.random();
                            if (randomNumber > configRandomTradeChance) {
                                performTrade(team);
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            ioObject.printMessage(e.getMessage());
        }
    }

    public void performTrade(ITeam tradingTeam){
        IScout teamScout = factory.createScout(tradingTeam, leagueObjectModel, gameConfig);
        int congifMaxPlayerPerTrade = Integer.parseInt(gameConfig.getValueFromOurObject(gameConfig.getTrading(), gameConfig.getMaxPlayersPerTrade()));
        currentTrade = teamScout.findTrade(congifMaxPlayerPerTrade);
        if (currentTrade == null){
            // TODO: 26-11-2020 log trade not possible
        }
        else{
            tradingTeam.setLossPoint(0);
            sendTradeToRecevingTeam(currentTrade, userTeam);
        }
    }

    public void sendTradeToRecevingTeam(ITradeOffer currentTrade, ITeam userTeam) {
        ITradeType tradeType;
        if(currentTrade.getReceivingTeam() == userTeam){
            tradeType = factory.createAiUserTrade(currentTrade, ioObject, updateUserTeamRoster);
        }else{
            tradeType = factory.createAiAiTrade(currentTrade, gameConfig);
        }
        if (tradeType.isTradeAccepted()) {
            ioObject.printMessage("Trade done between: " + currentTrade.getOfferingTeam().getTeamName() + " And " + currentTrade.getReceivingTeam().getTeamName());
            currentTrade.implementTrade();
            tradeType.validateTeamRosterAfterTrade(currentTrade.getReceivingTeam(), leagueObjectModel);
        }
    }

    public int findLossPointOfTheTeam(ITeam team) {
        int teamLossPoint = team.getLossPoint();
        return teamLossPoint;
    }

    public void setIoObject(IUserInputOutput ioObject) {
        this.ioObject = ioObject;
    }

    public ITradeOffer getCurrentTrade() {
        return currentTrade;
    }

}
