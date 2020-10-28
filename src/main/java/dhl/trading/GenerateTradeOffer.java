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
                    if(team.getTeamName() != tradingTeam.getTeamName()){
                        ITradeOffer currentTrade = makeSwapTradeOffer(tradingTeam , team);
                        if( currentTrade != null){
                            tradingTeam.setLossPoint(0);
                            ArrayList<IPlayer> playersOffered = currentTrade.getPlayersOffered();
                            ArrayList<IPlayer> playersWanted = currentTrade.getPlayersWantedInExchange();
                            ITeam offeringTeam = currentTrade.getOfferingTeam();
                            ITeam recevingTeam = currentTrade.getReceivingTeam();
                            for(IPlayer player: playersOffered){
                                recevingTeam.getPlayers().add(player);
                                offeringTeam.getPlayers().remove(player);
                            }
                            for(IPlayer player: playersWanted){
                                recevingTeam.getPlayers().remove(player);
                                offeringTeam.getPlayers().add(player);
                            }
                        }
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
                            break;
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
