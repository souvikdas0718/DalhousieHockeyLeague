package dhl.businessLogic.trade;

import dhl.businessLogic.leagueModel.PlayerPosition;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogic.trade.interfaces.IScout;

import java.util.ArrayList;
import java.util.List;

public class Scout implements IScout {

    private static final int TOTAL_GOALIES = 4;
    private static final int TOTAL_FORWARDS = 16;
    private static final int TOTAL_DEFENSE = 10;

    private ITeam myTeam;
    private ILeagueObjectModel myLeague;
    private IGameConfig gameConfig;

    public Scout(ITeam myTeam, ILeagueObjectModel myLeague, IGameConfig gameConfig) {
        this.myTeam = myTeam;
        this.myLeague = myLeague;
        this.gameConfig = gameConfig;
    }

    public void findTrade() {

        String positionToTrade = findWeakPartOfTeam(myTeam);
        IPlayer playerToGive = getWeakPlayer(myTeam, positionToTrade);
        findTeamToTradeWith(myTeam , myLeague);

    }

    public ITeam findTeamToTradeWith(ITeam tradingTeam, ILeagueObjectModel myLeague){
        for (IConference conference : myLeague.getConferences()) {
            for (IDivision division : conference.getDivisions()) {
                for (ITeam team : division.getTeams()) {
                    if (isTeamDifferent(team, tradingTeam)) {
                        if (isTeamGoodForTrading(tradingTeam, team)) {
                            return team;
                        }
                    }
                }
            }
        }
        return null;
    }

    public boolean isTeamGoodForTrading(ITeam teamOffering, ITeam teamGettingOffer){
        ArrayList<IPlayer> offeringTeamPayers = sortPlayerList(teamOffering);
        ArrayList<IPlayer> secondTeamPlayers = sortPlayerList(teamGettingOffer);
        int congifMaxPlayerPerTrade = Integer.parseInt(gameConfig.getValueFromOurObject(gameConfig.getTrading(), gameConfig.getMaxPlayersPerTrade()));
        int maxPlayersInTrade = 0;

        for (IPlayer playerToBeOffered : offeringTeamPayers) {
            for (IPlayer playerToGetInExchange : secondTeamPlayers) {
                if (maxPlayersInTrade + 2 <= congifMaxPlayerPerTrade) {
                    if ( playerToGetInExchange.getPosition().equals(playerToBeOffered.getPosition()) ) {
                        if (playerToGetInExchange.getPlayerStrength() > playerToBeOffered.getPlayerStrength()) {
                            maxPlayersInTrade += 2;
                            break;
                        }
                    }
                }
            }
        }
        if (maxPlayersInTrade > 0) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<IPlayer> sortPlayerList(ITeam tradingTeam) {
        ArrayList<IPlayer> sortedPlayerList = new ArrayList<IPlayer>();
        List<IPlayer> playerList = tradingTeam.getPlayers();
        if (playerList.size() > 0) {
            sortedPlayerList.add(playerList.get(0));
            for (int j = 1; j < playerList.size(); j++) {
                for (int i = 0; i < sortedPlayerList.size(); i++) {
                    if (sortedPlayerList.get(i).getPlayerStrength() >= playerList.get(j).getPlayerStrength()) {
                        sortedPlayerList.add(i, playerList.get(j));
                        break;
                    }
                }
            }
        }
        return sortedPlayerList;
    }

    public boolean isTeamDifferent(ITeam teamA, ITeam teamB) {
        if (teamA.getTeamName().equals(teamB.getTeamName())) {
            return false;
        } else {
            return true;
        }
    }


    public IPlayer getWeakPlayer(ITeam team, String position){
        double playerStrength = 10000;
        IPlayer weakPlayer = null;
        for (IPlayer player : team.getPlayers()){
            String tempPlayerPosition = player.getPosition();
            if (tempPlayerPosition.equals(position)){
                if(playerStrength > player.getPlayerStrength()){
                    weakPlayer = player;
                    playerStrength = player.getPlayerStrength();
                }
            }
        }

        return weakPlayer;
    }

    public String findWeakPartOfTeam(ITeam team){
        double defenceAvg = 0;
        double forwardAvg = 0;
        double goalieAvg = 0;

        for (IPlayer player : team.getPlayers()){
            if ( player.getPosition().equals( PlayerPosition.DEFENSE.toString() ) ){
                defenceAvg = defenceAvg + player.getPlayerStrength();
            }
            if( player.getPosition().equals( PlayerPosition.FORWARD.toString() ) ){
                forwardAvg = forwardAvg + player.getPlayerStrength();
            }
            if( player.getPosition().equals( PlayerPosition.GOALIE.toString() ) ){
                goalieAvg = goalieAvg + player.getPlayerStrength();
            }
        }
        defenceAvg = defenceAvg / TOTAL_DEFENSE;
        forwardAvg = forwardAvg / TOTAL_FORWARDS;
        goalieAvg = goalieAvg / TOTAL_GOALIES;
        double weakestArea = Math.min(Math.min(defenceAvg, forwardAvg), goalieAvg);
        if (weakestArea == forwardAvg){
            return PlayerPosition.FORWARD.toString();
        }else if (weakestArea == defenceAvg){
            return PlayerPosition.DEFENSE.toString();
        }else {
            return PlayerPosition.GOALIE.toString();
        }
    }
}
