package dhl.trade;

import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.leagueModel.interfaceModel.*;
import dhl.trade.Interface.ITradeOffer;
import dhl.trade.Interface.ITradeType;
import dhl.trade.Interface.ITradingEngine;

import java.util.ArrayList;
import java.util.List;

public class PlayerSwappingTradeEngine implements ITradingEngine {

    private ITradeOffer currentTrade;
    private IGameConfig gameConfig;
    private TradeConfigVariableNames tradingConfigNames;
    private ILeagueObjectModel leagueObjectModel;

    public PlayerSwappingTradeEngine(IGameConfig gameConfig, ILeagueObjectModel leagueObjectModel){
        this.gameConfig = gameConfig;
        tradingConfigNames = new TradeConfigVariableNames();
        this.leagueObjectModel = leagueObjectModel;
    }

    @Override
    public void makeOffer(ITeam tradingTeam ) throws Exception {
        for(IConference conference : leagueObjectModel.getConferences()){
            for(IDivision division : conference.getDivisions()){
                for(ITeam team : division.getTeams()){
                    if(isTeamDifferent(team , tradingTeam)){
                        currentTrade = ifTradePossibleMakeOffer(tradingTeam , team);
                        if( isObjectInitiated(currentTrade) ){
                            tradingTeam.setLossPoint(0);
                            break;
                        }
                    }

                }
            }
        }
    }

    public boolean isObjectInitiated(Object obj){
        if (obj == null){
            return false;
        }else{
            return true;
        }
    }

    public boolean isTeamDifferent(ITeam teamA , ITeam teamB){
        if (teamA.getTeamName().equals(teamB.getTeamName())){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void sendTradeToRecevingTeam(ITeam userTeam) {
        ITradeType tradeType;
        // TODO: 29-10-2020 check how to handle this  LSC
        if(currentTrade.getReceivingTeam() == userTeam){
            tradeType = new AiUserTrade();
        }else{
            tradeType = new AiAiTrade(currentTrade,gameConfig);
        }

        if(tradeType.isTradeAccepted()){
            currentTrade.performTrade();
        }

    }

    @Override
    public ITradeOffer getCurrentTrade() {
        return currentTrade;
    }

    @Override
    public void checkPlayersAfterTrade() {
        // TODO: 29-10-2020 useless method
        if(isObjectInitiated(currentTrade)){
            ITeam offeringTeam = currentTrade.getOfferingTeam();
            ITeam recevingTeam = currentTrade.getReceivingTeam();
            List<IPlayer> offeringTeamPlayers = offeringTeam.getPlayers();
            List<IPlayer> recevingTeamPlayers = recevingTeam.getPlayers();
            if(offeringTeamPlayers.size() > 20){
                
            }
        }

    }

    public ITradeOffer ifTradePossibleMakeOffer(ITeam teamOffering, ITeam  teamGettingOffer) throws Exception {
        ArrayList<IPlayer> offeringTeamPayers = sortPlayerList(teamOffering);
        ArrayList<IPlayer> secondTeamPlayers = sortPlayerList(teamGettingOffer);
        ArrayList<IPlayer> playersOffered = new ArrayList<>();
        ArrayList<IPlayer> playersWanted = new ArrayList<>();
        int congifMaxPlayerPerTrade = Integer.parseInt(gameConfig.getValueFromOurObject(tradingConfigNames.getMaxPlayersPerTrade()));
        int maxPlayersInTrade = 0;
        for (IPlayer playerToBeOffered: offeringTeamPayers){
            for (IPlayer playerToGetInExchange: secondTeamPlayers){
                if(maxPlayersInTrade + 2 >= congifMaxPlayerPerTrade){
                    if (playerToGetInExchange.getPosition() == playerToBeOffered.getPosition()){
                        if (playerToGetInExchange.getPlayerStrength() > playerToBeOffered.getPlayerStrength()){
                            playersOffered.add(playerToBeOffered);
                            playersWanted.add(playerToGetInExchange);
                            maxPlayersInTrade += 2;
                            break;
                        }
                    }
                }
            }
        }
        if (maxPlayersInTrade > 0){
            // TODO: 27-10-2020 is this decoupled ?
            ExchangingPlayerTradeOffer newOffer = new ExchangingPlayerTradeOffer(teamOffering , teamGettingOffer , playersOffered , playersWanted );
            return newOffer;
        }
        return null;
    }

    public ArrayList<IPlayer> sortPlayerList(ITeam tradingTeam) throws Exception {
        ArrayList<IPlayer> sortedPlayerList = new ArrayList<IPlayer>();
        List<IPlayer> playerList = tradingTeam.getPlayers();
        if(playerList.size() > 0) {
            sortedPlayerList.add(playerList.get(0));
            for (int j = 1 ; j < playerList.size() ; j++) {
                for(int i = 0 ; i < sortedPlayerList.size() ; i++){
                    if(sortedPlayerList.get(i).getPlayerStrength() >= playerList.get(j).getPlayerStrength()){
                        sortedPlayerList.add(i , playerList.get(j));
                        break;
                    }
                }
            }
        }else{
            throw new Exception(tradingTeam.getTeamName() + " Team have no players");
        }
        return sortedPlayerList;
    }

}
