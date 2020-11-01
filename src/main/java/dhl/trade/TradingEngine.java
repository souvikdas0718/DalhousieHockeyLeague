package dhl.trade;

import dhl.InputOutput.UI.IUserInputOutput;
import dhl.InputOutput.UI.UserInputOutput;
import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.leagueModel.interfaceModel.*;
import dhl.trade.Interface.ITradeOffer;
import dhl.trade.Interface.ITradeType;
import dhl.trade.Interface.ITradingEngine;

import java.util.ArrayList;
import java.util.List;

public class TradingEngine implements ITradingEngine {

    private ITradeOffer currentTrade;
    private IGameConfig gameConfig;
    private ILeagueObjectModel leagueObjectModel;
    IUserInputOutput ioObject;

    public TradingEngine(IGameConfig gameConfig, ILeagueObjectModel leagueObjectModel,IUserInputOutput ioObject){
        this.gameConfig = gameConfig;
        this.leagueObjectModel = leagueObjectModel;
        this.ioObject = ioObject;
    }

    @Override
    public void makeOffer(ITeam tradingTeam ) throws Exception {

        try {
            ITeam teamToTradeWith = findTeamToTradeWith(tradingTeam);
            currentTrade = generateTradeOffer(tradingTeam , teamToTradeWith);
            tradingTeam.setLossPoint(0);
        }catch (Exception e){
            ioObject.printMessage(e.getMessage());
        }
    }

    @Override
    public void sendTradeToRecevingTeam(ITeam userTeam) {
        ITradeType tradeType;
        // TODO: 29-10-2020 check how to handle this  LSC
        if(currentTrade.getReceivingTeam() == userTeam){
            tradeType = new AiUserTrade(currentTrade , new UserInputOutput());
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
        // TODO: 01-11-2020 implement this
    }

    public ITeam findTeamToTradeWith(ITeam tradingTeam) throws Exception {
        for(IConference conference : leagueObjectModel.getConferences()){
            for(IDivision division : conference.getDivisions()){
                for(ITeam team : division.getTeams()){
                    if(isTeamDifferent(team , tradingTeam)){
                        if(isTeamGoodForTrading( tradingTeam , team)){
                            return team;
                        }
                    }
                }
            }
        }
        throw new Exception(" No Good Player availabe to swap for Team: " + tradingTeam.getTeamName());
    }

    public ITradeOffer generateTradeOffer(ITeam teamOffering, ITeam  teamGettingOffer) throws Exception {
        ArrayList<IPlayer> offeringTeamPayers = sortPlayerList(teamOffering);
        ArrayList<IPlayer> secondTeamPlayers = sortPlayerList(teamGettingOffer);
        ArrayList<IPlayer> playersOffered = new ArrayList<>();
        ArrayList<IPlayer> playersWanted = new ArrayList<>();
        int congifMaxPlayerPerTrade = Integer.parseInt(gameConfig.getValueFromOurObject( gameConfig.getTrading(), gameConfig.getMaxPlayersPerTrade()));
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
        // TODO: 27-10-2020 voilation of dependency inversion
        ITradeOffer newOffer = new ExchangingPlayerTradeOffer(teamOffering , teamGettingOffer , playersOffered , playersWanted );
        return newOffer;
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
    
    public boolean isTeamDifferent(ITeam teamA , ITeam teamB){
        if (teamA.getTeamName().equals(teamB.getTeamName())){
            return false;
        }else{
            return true;
        }
    }

    public boolean isTeamGoodForTrading(ITeam teamOffering , ITeam teamGettingOffer) throws Exception {

        ArrayList<IPlayer> offeringTeamPayers = sortPlayerList(teamOffering);
        ArrayList<IPlayer> secondTeamPlayers = sortPlayerList(teamGettingOffer);
        int congifMaxPlayerPerTrade = Integer.parseInt(gameConfig.getValueFromOurObject( gameConfig.getTrading(), gameConfig.getMaxPlayersPerTrade()));
        int maxPlayersInTrade = 0;

        for (IPlayer playerToBeOffered: offeringTeamPayers){
            for (IPlayer playerToGetInExchange: secondTeamPlayers){
                if(maxPlayersInTrade + 2 >= congifMaxPlayerPerTrade){
                    if (playerToGetInExchange.getPosition() == playerToBeOffered.getPosition()){
                        if (playerToGetInExchange.getPlayerStrength() > playerToBeOffered.getPlayerStrength()){
                            maxPlayersInTrade += 2;
                            break;
                        }
                    }
                }
            }
        }

        if (maxPlayersInTrade > 0){
            return true;
        }else {
            return false;
        }
    }
}
