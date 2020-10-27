package dhl.trading;

import dhl.leagueModel.interfaceModel.*;
import dhl.trading.Interface.ITradeConfig;
import dhl.trading.Interface.ITradeOffer;

import java.util.ArrayList;

public class GenerateTradeOffer {
    ITradeConfig tradeConfig;
    public GenerateTradeOffer(ITradeConfig tradeConfig){
        this.tradeConfig = tradeConfig;
    }
    public void makeTrade(ITeam tradingTeam , ILeagueObjectModel leagueObjectModel) throws Exception {
        for(IConference conference : leagueObjectModel.getConferences()){
            for(IDivision division : conference.getDivisions()){
                for(ITeam team : division.getTeams()){
                    if(makeSwapTradeOffer(tradingTeam , team) != null){
                        // TODO: 27-10-2020 set loss point to 0
                        //tradingTeam.setLossPoint(0);
                    }
                }
            }
        }
    }
    public ArrayList<IPlayer> sortPlayerList(ITeam tradingTeam) throws Exception {
        ArrayList<IPlayer> sortedPlayerList = new ArrayList<IPlayer>();
        ArrayList<IPlayer> playerList = tradingTeam.getPlayers();
        if(playerList.size() > 0) {
            sortedPlayerList.add(playerList.get(0));
            for (int j = 1 ; j < playerList.size() ; j++) {
                for(int i = 0 ; i < sortedPlayerList.size() ; i++){
                    if(sortedPlayerList.get(i).getPlayerStrength() > playerList.get(j).getPlayerStrength()){
                        sortedPlayerList.add(i , playerList.get(j));
                    }
                }
                sortedPlayerList.add( sortedPlayerList.size() - 1 , playerList.get(j));
            }
        }else{
            throw new Exception(tradingTeam.getTeamName() + " Team have no players");
        }
        return sortedPlayerList;
     }

    public ITradeOffer makeSwapTradeOffer(ITeam teamOffering, ITeam  teamGettingOffer) throws Exception {
        ArrayList<IPlayer> offeringTeamPayers = sortPlayerList(teamOffering);
        ArrayList<IPlayer> secondTeamPlayers = sortPlayerList(teamGettingOffer);
        ArrayList<IPlayer> playersOffered = new ArrayList<>();
        ArrayList<IPlayer> playersWanted = new ArrayList<>();
        int maxPlayersInTrade = 0;
        for (IPlayer playerToBeOffered: offeringTeamPayers){
            for (IPlayer playerToGetInExchange: secondTeamPlayers){
                if(maxPlayersInTrade + 2 >= tradeConfig.getMaxPlayersPerTrade()){
                    if (playerToGetInExchange.getPosition() == playerToBeOffered.getPosition()){
                        if (playerToGetInExchange.getPlayerStrength() > playerToBeOffered.getPlayerStrength()){
                            playersOffered.add(playerToBeOffered);
                            playersWanted.add(playerToGetInExchange);
                            maxPlayersInTrade += 2;
                        }
                    }
                }
            }
        }
        if (maxPlayersInTrade > 0){
            // TODO: 27-10-2020 is this decoupled ?
            SwapingTradeOffer newOffer = new SwapingTradeOffer(teamOffering , teamGettingOffer , playersOffered , playersWanted );
            return newOffer;
        }
        return null;
    }
}
