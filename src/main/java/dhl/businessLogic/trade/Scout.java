package dhl.businessLogic.trade;

import dhl.businessLogic.leagueModel.PlayerPosition;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogic.trade.factory.TradeAbstractFactory;
import dhl.businessLogic.trade.factory.TradeConcreteFactory;
import dhl.businessLogic.trade.interfaces.IScout;
import dhl.businessLogic.trade.interfaces.ITradeOffer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;

public class Scout implements IScout {

    private static final int TOTAL_GOALIES = 4;
    private static final int TOTAL_FORWARDS = 16;
    private static final int TOTAL_DEFENSE = 10;
    private double PLAYER_WANTED_STRENGTH_MULTIPLIER = 1.25;

    private ITeam myTeam;
    private ILeagueObjectModel myLeague;
    private IGameConfig gameConfig;
    TradeAbstractFactory factory;

    private static final Logger logger = LogManager.getLogger(Scout.class);

    public Scout(ITeam myTeam, ILeagueObjectModel myLeague, IGameConfig gameConfig) {
        this.myTeam = myTeam;
        this.myLeague = myLeague;
        this.gameConfig = gameConfig;
        factory = new TradeConcreteFactory();
        logger.info("Scout made for Team: "+ myTeam.getTeamName());
    }

    public ITradeOffer findTrade(int congifMaxPlayerPerTrade) {
        int maxPlayersInTrade = 0;
        ArrayList<IPlayer> listOfPlayersToGive = new ArrayList<>();
        ArrayList<IPlayer> listOfPlayerWeWillGet = new ArrayList<>();
        int playerToGiveIndex,playerWeWillGetIndex;
        playerToGiveIndex = -1;
        playerWeWillGetIndex = -1;
        ITeam teamToTrade = null;

        while(maxPlayersInTrade < congifMaxPlayerPerTrade){
            if (congifMaxPlayerPerTrade > 1){
                if (maxPlayersInTrade == 0){
                    String positionWanted= findWeakPartOfTeam(myTeam);
                    listOfPlayersToGive.add(getWeakPlayer(myTeam,""));
                    playerToGiveIndex = playerToGiveIndex + 1;
                    teamToTrade = findTeamToTradeWith(myLeague, positionWanted, listOfPlayersToGive.get(playerToGiveIndex));

                    if (teamFound(teamToTrade)){
                        logger.info(myTeam.getTeamName()+"'s Scout found Trade Team:"+teamToTrade.getTeamName());
                        double playerStrengthNeeded = listOfPlayersToGive.get(playerToGiveIndex).getPlayerStrength()*PLAYER_WANTED_STRENGTH_MULTIPLIER;

                        IPlayer player = findPlayerToTrade(teamToTrade, positionWanted, playerStrengthNeeded);
                        listOfPlayerWeWillGet.add(player);
                        playerWeWillGetIndex = playerWeWillGetIndex + 1;
                        maxPlayersInTrade = maxPlayersInTrade + 2;
                        logger.info(myTeam.getTeamName()+"'s Scout found Player to Trade:"+player.getPlayerName());
                    }
                    else{
                        if(PLAYER_WANTED_STRENGTH_MULTIPLIER > 1) {
                            PLAYER_WANTED_STRENGTH_MULTIPLIER = PLAYER_WANTED_STRENGTH_MULTIPLIER - 0.1;
                        }
                        else{
                            break;
                        }
                    }
                }
                else if (congifMaxPlayerPerTrade % 2 == 1){
                    myTeam.getPlayers().remove(listOfPlayersToGive.get(playerToGiveIndex));
                    myTeam.getPlayers().add(listOfPlayerWeWillGet.get(playerWeWillGetIndex));
                    teamToTrade.getPlayers().remove(listOfPlayerWeWillGet.get(playerWeWillGetIndex));

                    String positionWanted= findWeakPartOfTeam(myTeam);
                    double playerStrengthNeeded = listOfPlayersToGive.get(playerToGiveIndex).getPlayerStrength()*PLAYER_WANTED_STRENGTH_MULTIPLIER-0.1;
                    IPlayer tempPlayerWeWillGet = getPlayerToTradeFromTeam(teamToTrade, positionWanted, playerStrengthNeeded);
                    logger.info(myTeam.getTeamName()+"'s Scout found Player to Trade:"+ tempPlayerWeWillGet.getPlayerName());

                    myTeam.getPlayers().add(listOfPlayersToGive.get(playerToGiveIndex));
                    myTeam.getPlayers().remove(listOfPlayerWeWillGet.get(playerWeWillGetIndex));
                    teamToTrade.getPlayers().add(listOfPlayerWeWillGet.get(playerWeWillGetIndex));

                    if (playerFound(tempPlayerWeWillGet)){
                        listOfPlayerWeWillGet.add(tempPlayerWeWillGet);
                        playerWeWillGetIndex = playerWeWillGetIndex + 1;
                        maxPlayersInTrade = maxPlayersInTrade + 1;
                    }
                    else {
                        break;
                    }

                }
                else if (congifMaxPlayerPerTrade % 2 == 0){
                    myTeam.getPlayers().remove(listOfPlayersToGive.get(playerToGiveIndex));
                    myTeam.getPlayers().add(listOfPlayerWeWillGet.get(playerWeWillGetIndex));
                    teamToTrade.getPlayers().remove(listOfPlayerWeWillGet.get(playerWeWillGetIndex));

                    String positionWanted = findWeakPartOfTeam(myTeam);
                    IPlayer tempPlayerToGive = getWeakPlayer(myTeam,"");

                    double playerStrengthNeeded = tempPlayerToGive.getPlayerStrength()*PLAYER_WANTED_STRENGTH_MULTIPLIER;
                    IPlayer tempPlayerWeWillGet = getPlayerToTradeFromTeam(teamToTrade, positionWanted, playerStrengthNeeded);
                    logger.info(myTeam.getTeamName()+"'s Scout found Player to Trade:"+ tempPlayerWeWillGet.getPlayerName());

                    myTeam.getPlayers().add(listOfPlayersToGive.get(playerToGiveIndex));
                    myTeam.getPlayers().remove(listOfPlayerWeWillGet.get(playerWeWillGetIndex));
                    teamToTrade.getPlayers().add(listOfPlayerWeWillGet.get(playerWeWillGetIndex));

                    if (playerFound(tempPlayerWeWillGet)) {
                        listOfPlayersToGive.add(tempPlayerToGive);
                        listOfPlayerWeWillGet.add(tempPlayerWeWillGet);
                        playerToGiveIndex = playerToGiveIndex + 1;
                        playerWeWillGetIndex = playerWeWillGetIndex + 1;
                        maxPlayersInTrade = maxPlayersInTrade + 2;
                    }
                    else {
                        break;
                    }
                }
            }
            else{
                break;
            }
        }

        if (teamFound(teamToTrade)){
            ITradeOffer newOffer = factory.createExchangingPlayerTradeOffer(myTeam, teamToTrade, listOfPlayersToGive, listOfPlayerWeWillGet);
            return newOffer;
        }
        else{
            return null;
        }
    }

    public IPlayer getPlayerToTradeFromTeam(ITeam teamToTrade, String  positionWanted, Double playerStrengthNeeded){
        IPlayer playerToSend = null;
        double playerToSendStrength = 1000;

        for (IPlayer player: teamToTrade.getPlayers()){
            if(player.getPosition().equals(positionWanted)){
                if(playerToSendStrength > player.getPlayerStrength() && playerStrengthNeeded <= player.getPlayerStrength()){
                    playerToSend = player;
                    playerToSendStrength = player.getPlayerStrength();
                }
            }
        }
        return playerToSend;
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
