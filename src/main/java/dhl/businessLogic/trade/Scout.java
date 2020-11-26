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

        int congifMaxPlayerPerTrade = Integer.parseInt(gameConfig.getValueFromOurObject(gameConfig.getTrading(), gameConfig.getMaxPlayersPerTrade()));
        int maxPlayersInTrade = 0;
        IPlayer playerToGive, playerToGive2 = null;
        IPlayer playerWeWillGet, playerWeWillGet2 = null;

        String positionWanted= findWeakPartOfTeam(myTeam);
        playerToGive = getWeakPlayer(myTeam,"");
        ITeam teamToTrade = findTeamToTradeWith(myLeague, positionWanted, playerToGive);

        if ( teamFound(teamToTrade) ){
            playerWeWillGet = findPlayerToTrade(teamToTrade, positionWanted, playerToGive.getPlayerStrength()*1.25);
            maxPlayersInTrade = maxPlayersInTrade + 2;
            if ( congifMaxPlayerPerTrade > maxPlayersInTrade){
                if (congifMaxPlayerPerTrade % 2  == 0){
                    myTeam.getPlayers().remove(playerToGive);
                    myTeam.getPlayers().add(playerWeWillGet);
                    teamToTrade.getPlayers().remove(playerWeWillGet);

                    positionWanted= findWeakPartOfTeam(myTeam);
                    playerToGive2 = getWeakPlayer(myTeam,"");
                    playerWeWillGet2 = findPlayerToTrade(teamToTrade, positionWanted, playerToGive.getPlayerStrength()*1.25);

                    myTeam.getPlayers().remove(playerWeWillGet);
                    myTeam.getPlayers().add(playerToGive);
                    teamToTrade.getPlayers().add(playerWeWillGet);
                    maxPlayersInTrade = maxPlayersInTrade + 2;

                }else{
                    teamToTrade.getPlayers().remove(playerWeWillGet);
                    playerWeWillGet2 = findPlayerToTrade(teamToTrade, positionWanted, playerToGive.getPlayerStrength()*1.15);
                    maxPlayersInTrade = maxPlayersInTrade + 1;
                }
            }
        }
        // TODO: 26-11-2020  make this as loop
        // TODO: 26-11-2020 Create offer and return it to engine
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

    public IPlayer getWeakPlayer(ITeam team, String position){
        double playerStrength = 10000;
        IPlayer weakPlayer = null;
        for (IPlayer player : team.getPlayers()){
            if (position.isEmpty()) {
                if (playerStrength > player.getPlayerStrength()) {
                    weakPlayer = player;
                    playerStrength = player.getPlayerStrength();
                }
            }else{
                if (playerStrength > player.getPlayerStrength() && position.equals(player.getPosition())) {
                    weakPlayer = player;
                    playerStrength = player.getPlayerStrength();
                }
            }
        }

        return weakPlayer;
    }

    public ITeam findTeamToTradeWith(ILeagueObjectModel myLeague,String positionWanted,IPlayer playerToGive){
        double PLAYER_WANTED_STRENGTH_MULTIPLIER = 1.25;
        for (IConference conference : myLeague.getConferences()) {
            for (IDivision division : conference.getDivisions()) {
                for (ITeam team : division.getTeams()) {
                    double playerStrengthShouldBeMoreThan = playerToGive.getPlayerStrength()* PLAYER_WANTED_STRENGTH_MULTIPLIER;
                    IPlayer playerToTrade = findPlayerToTrade(team, positionWanted, playerStrengthShouldBeMoreThan);
                    if( playerFound( playerToTrade ) ){
                        return team;
                    }
                }
            }
        }
        return null;
    }

    public  boolean playerFound(IPlayer player){
        if (player == null){
            return false;
        }else {
            return true;
        }
    }

    public  boolean teamFound(ITeam team){
        if (team == null){
            return false;
        }else {
            return true;
        }
    }

    public IPlayer findPlayerToTrade(ITeam team, String positionWanted, double playerStrengthShouldBeMoreThan){
        IPlayer player = getWeakPlayer(team , positionWanted);
        if (player.getPlayerStrength()>playerStrengthShouldBeMoreThan){
            return player;
        }
        return null;
    }

}
