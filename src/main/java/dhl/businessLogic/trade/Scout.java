package dhl.businessLogic.trade;

import dhl.businessLogic.leagueModel.PlayerDraftAbstract;
import dhl.businessLogic.leagueModel.PlayerPosition;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogic.trade.factory.TradeAbstractFactory;
import dhl.businessLogic.trade.factory.TradeConcreteFactory;
import dhl.businessLogic.trade.interfaces.IScout;
import dhl.businessLogic.trade.interfaces.ITradeType;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class Scout implements IScout {

    private int expectedTotalGoalies;
    private int expectedTotalDefence;
    private int expectedTotalForward;
    private double PLAYER_WANTED_STRENGTH_MULTIPLIER = 1.25;

    ITeam userTeam;
    private ITeam myTeam;
    private ILeagueObjectModel myLeague;
    private IGameConfig gameConfig;
    TradeAbstractFactory factory;
    IUserInputOutput ioObject;

    private static final Logger logger = LogManager.getLogger(Scout.class);

    public Scout(ITeam myTeam, ILeagueObjectModel myLeague, IGameConfig gameConfig, ITeam userTeam) {
        this.userTeam = userTeam;
        this.myTeam = myTeam;
        this.myLeague = myLeague;
        this.gameConfig = gameConfig;
        factory = new TradeConcreteFactory();
        this.ioObject = IUserInputOutput.getInstance();
        expectedTotalDefence = myTeam.getTotalDefense();
        expectedTotalForward = myTeam.getTotalForwards();
        expectedTotalGoalies = myTeam.getTotalGoalies();
        logger.info("Scout made for Team: " + myTeam.getTeamName());
    }

    public TradeOfferAbstract findTrade(int congifMaxPlayerPerTrade) {
        int maxPlayersInTrade = 0;
        ArrayList<IPlayer> listOfPlayersToGive = new ArrayList<>();
        ArrayList<IPlayer> listOfPlayerWeWillGet = new ArrayList<>();
        int playerToGiveIndex, playerWeWillGetIndex;
        playerToGiveIndex = -1;
        playerWeWillGetIndex = -1;
        ITeam teamToTrade = null;

        while (maxPlayersInTrade < congifMaxPlayerPerTrade) {
            if (congifMaxPlayerPerTrade > 1) {
                if (maxPlayersInTrade == 0) {
                    logger.info("Scout begins Search For Players ");
                    String positionWanted = findWeakPartOfTeam(myTeam);
                    IPlayer playerWeWillGive = getWeakPlayer(myTeam, "");
                    listOfPlayersToGive.add(playerWeWillGive);
                    playerToGiveIndex = playerToGiveIndex + 1;
                    logger.info("Player we will give is: " + playerWeWillGive.getPlayerName());

                    teamToTrade = findTeamToTradeWith(myLeague, positionWanted, listOfPlayersToGive.get(playerToGiveIndex));

                    if (teamFound(teamToTrade)) {
                        logger.info(myTeam.getTeamName() + "'s Scout found Trade Team:" + teamToTrade.getTeamName());
                        double playerStrengthNeeded = listOfPlayersToGive.get(playerToGiveIndex).getPlayerStrength() * PLAYER_WANTED_STRENGTH_MULTIPLIER;

                        IPlayer player = findPlayerToTrade(teamToTrade, positionWanted, playerStrengthNeeded);
                        listOfPlayerWeWillGet.add(player);
                        playerWeWillGetIndex = playerWeWillGetIndex + 1;
                        maxPlayersInTrade = maxPlayersInTrade + 2;
                        logger.info(myTeam.getTeamName() + "'s Scout found Player to Trade:" + player.getPlayerName());
                    } else {
                        logger.debug("No player found at " + PLAYER_WANTED_STRENGTH_MULTIPLIER + " strength multiplier");
                        listOfPlayersToGive.remove(playerToGiveIndex);
                        playerToGiveIndex = playerToGiveIndex - 1;
                        if (PLAYER_WANTED_STRENGTH_MULTIPLIER > 1) {
                            logger.debug("Reducing strength multiplier by 0.1");
                            PLAYER_WANTED_STRENGTH_MULTIPLIER = PLAYER_WANTED_STRENGTH_MULTIPLIER - 0.1;
                        } else {
                            logger.debug("Strength multiplier is less than 1");
                            break;
                        }
                    }
                } else if (congifMaxPlayerPerTrade % 2 == 1) {
                    logger.debug("Finding one more player to get since we have good strength Player");
                    myTeam.getPlayers().remove(listOfPlayersToGive.get(playerToGiveIndex));
                    myTeam.getPlayers().add(listOfPlayerWeWillGet.get(playerWeWillGetIndex));
                    teamToTrade.getPlayers().remove(listOfPlayerWeWillGet.get(playerWeWillGetIndex));

                    String positionWanted = findWeakPartOfTeam(myTeam);
                    double playerStrengthNeeded = listOfPlayersToGive.get(playerToGiveIndex).getPlayerStrength() * PLAYER_WANTED_STRENGTH_MULTIPLIER - 0.1;
                    IPlayer tempPlayerWeWillGet = getPlayerToTradeFromTeam(teamToTrade, positionWanted, playerStrengthNeeded);

                    myTeam.getPlayers().add(listOfPlayersToGive.get(playerToGiveIndex));
                    myTeam.getPlayers().remove(listOfPlayerWeWillGet.get(playerWeWillGetIndex));
                    teamToTrade.getPlayers().add(listOfPlayerWeWillGet.get(playerWeWillGetIndex));

                    if (playerFound(tempPlayerWeWillGet)) {
                        listOfPlayerWeWillGet.add(tempPlayerWeWillGet);
                        playerWeWillGetIndex = playerWeWillGetIndex + 1;
                        maxPlayersInTrade = maxPlayersInTrade + 1;
                        logger.info("Another player we will get is " + tempPlayerWeWillGet.getPlayerName());
                    } else {
                        break;
                    }

                } else if (congifMaxPlayerPerTrade % 2 == 0) {
                    myTeam.getPlayers().remove(listOfPlayersToGive.get(playerToGiveIndex));
                    myTeam.getPlayers().add(listOfPlayerWeWillGet.get(playerWeWillGetIndex));
                    teamToTrade.getPlayers().remove(listOfPlayerWeWillGet.get(playerWeWillGetIndex));

                    String positionWanted = findWeakPartOfTeam(myTeam);
                    IPlayer tempPlayerToGive = getWeakPlayer(myTeam, "");

                    double playerStrengthNeeded = tempPlayerToGive.getPlayerStrength() * PLAYER_WANTED_STRENGTH_MULTIPLIER;
                    IPlayer tempPlayerWeWillGet = getPlayerToTradeFromTeam(teamToTrade, positionWanted, playerStrengthNeeded);

                    myTeam.getPlayers().add(listOfPlayersToGive.get(playerToGiveIndex));
                    myTeam.getPlayers().remove(listOfPlayerWeWillGet.get(playerWeWillGetIndex));
                    teamToTrade.getPlayers().add(listOfPlayerWeWillGet.get(playerWeWillGetIndex));

                    if (playerFound(tempPlayerWeWillGet)) {
                        logger.info(myTeam.getTeamName() + "'s Scout found Player to Trade:" + tempPlayerWeWillGet.getPlayerName());
                        listOfPlayersToGive.add(tempPlayerToGive);
                        listOfPlayerWeWillGet.add(tempPlayerWeWillGet);
                        playerToGiveIndex = playerToGiveIndex + 1;
                        playerWeWillGetIndex = playerWeWillGetIndex + 1;
                        maxPlayersInTrade = maxPlayersInTrade + 2;
                    } else {
                        break;
                    }
                }
            } else {
                logger.info("making Draft Pick trade for team: " + myTeam.getTeamName());
                String positionWanted = findWeakPartOfTeam(myTeam);
                IPlayer playerToGive = getWeakPlayer(myTeam, "");
                listOfPlayersToGive.add(playerToGive);
                playerToGiveIndex = playerToGiveIndex + 1;
                teamToTrade = findTeamToTradeWith(myLeague, positionWanted, listOfPlayersToGive.get(playerToGiveIndex));
                if (teamFound(teamToTrade)) {
                    logger.info("Scout Found team to trade" + teamToTrade.getTeamName());
                    double playerStrengthNeeded = listOfPlayersToGive.get(playerToGiveIndex).getPlayerStrength() * PLAYER_WANTED_STRENGTH_MULTIPLIER;
                    IPlayer player = findPlayerToTrade(teamToTrade, positionWanted, playerStrengthNeeded);
                    listOfPlayerWeWillGet.add(player);
                    playerWeWillGetIndex = playerWeWillGetIndex + 1;
                    maxPlayersInTrade = maxPlayersInTrade + 1;
                }
            }
        }

        if (teamFound(teamToTrade)) {
            ITradeType tradeType = getTradeTypeObject(teamToTrade);
            if (congifMaxPlayerPerTrade > 1) {
                logger.debug("Making swap Player Trade offer for team: " + myTeam.getTeamName() + " and " + teamToTrade.getTeamName());
                TradeOfferAbstract newOffer = factory.createExchangingPlayerTradeOffer(myTeam, teamToTrade, listOfPlayersToGive, listOfPlayerWeWillGet, tradeType);
                if (newOffer.checkIfTradeAccepted()) {
                    return newOffer;
                } else {
                    logger.debug("Scout found that this trade will not be accepted so offering Draft trade instead");
                    PlayerDraftAbstract playerDraft = PlayerDraftAbstract.instance();
                    ArrayList<IPlayer> playerWantedInDraft = new ArrayList<>();
                    playerWantedInDraft.add(listOfPlayerWeWillGet.get(0));
                    newOffer = factory.createDraftPickTradeOffer(myTeam, teamToTrade, playerWantedInDraft, playerDraft);
                    return newOffer;
                }
            } else {
                logger.debug("Make Draft Trade offer for team: " + myTeam.getTeamName() + " and " + teamToTrade.getTeamName());
                PlayerDraftAbstract playerDraft = PlayerDraftAbstract.instance();
                TradeOfferAbstract newOffer = factory.createDraftPickTradeOffer(myTeam, teamToTrade, listOfPlayerWeWillGet, playerDraft);
                return newOffer;
            }

        } else {
            logger.info("Scout cannot find a trade for team: " + myTeam.getTeamName());
            return null;
        }
    }

    public ITradeType getTradeTypeObject(ITeam teamToTrade) {
        ITradeType tradeType = null;
        if (teamToTrade == userTeam) {
            tradeType = factory.createAiUserTrade(ioObject, myLeague);
        } else {
            tradeType = factory.createAiAiTrade(myLeague, gameConfig);
        }
        return tradeType;
    }

    public IPlayer getPlayerToTradeFromTeam(ITeam teamToTrade, String positionWanted, Double playerStrengthNeeded) {
        IPlayer playerToSend = null;
        double playerToSendStrength = 1000;
        logger.debug(" finding player to trade from team: " + teamToTrade.getTeamName());
        for (IPlayer player : teamToTrade.getPlayers()) {
            if (player.getPosition().equals(positionWanted)) {
                if (playerToSendStrength > player.getPlayerStrength() && playerStrengthNeeded <= player.getPlayerStrength()) {
                    playerToSend = player;
                    playerToSendStrength = player.getPlayerStrength();
                }
            }
        }
        return playerToSend;
    }

    public String findWeakPartOfTeam(ITeam team) {
        double defenceAvg = 0;
        double forwardAvg = 0;
        double goalieAvg = 0;
        logger.debug("Finding weakest Part of team: " + team.getTeamName());
        for (IPlayer player : team.getPlayers()) {
            if (player.getPosition().equals(PlayerPosition.DEFENSE.toString())) {
                defenceAvg = defenceAvg + player.getPlayerStrength();
            }
            if (player.getPosition().equals(PlayerPosition.FORWARD.toString())) {
                forwardAvg = forwardAvg + player.getPlayerStrength();
            }
            if (player.getPosition().equals(PlayerPosition.GOALIE.toString())) {
                goalieAvg = goalieAvg + player.getPlayerStrength();
            }
        }
        defenceAvg = defenceAvg / expectedTotalDefence;
        forwardAvg = forwardAvg / expectedTotalForward;
        goalieAvg = goalieAvg / expectedTotalGoalies;
        double weakestArea = Math.min(Math.min(defenceAvg, forwardAvg), goalieAvg);
        if (weakestArea == forwardAvg) {
            return PlayerPosition.FORWARD.toString();
        } else if (weakestArea == defenceAvg) {
            return PlayerPosition.DEFENSE.toString();
        } else {
            return PlayerPosition.GOALIE.toString();
        }
    }

    public IPlayer getWeakPlayer(ITeam team, String position) {
        logger.debug("Finding weakest Player in team: " + team.getTeamName() + "--" + position);
        double playerStrength = 10000;
        IPlayer weakPlayer = null;
        for (IPlayer player : team.getPlayers()) {
            if (position.isEmpty()) {
                if (playerStrength > player.getPlayerStrength()) {
                    weakPlayer = player;
                    playerStrength = player.getPlayerStrength();
                }
            } else {
                if (playerStrength > player.getPlayerStrength() && position.equals(player.getPosition())) {
                    weakPlayer = player;
                    playerStrength = player.getPlayerStrength();
                }
            }
        }
        return weakPlayer;
    }

    public ITeam findTeamToTradeWith(ILeagueObjectModel myLeague, String positionWanted, IPlayer playerToGive) {
        logger.debug("Searching team to trade with.");
        for (IConference conference : myLeague.getConferences()) {
            for (IDivision division : conference.getDivisions()) {
                for (ITeam team : division.getTeams()) {
                    if (isTeamDifferent(myTeam, team)) {
                        double playerStrengthShouldBeMoreThan = playerToGive.getPlayerStrength() * PLAYER_WANTED_STRENGTH_MULTIPLIER;
                        IPlayer playerToTrade = findPlayerToTrade(team, positionWanted, playerStrengthShouldBeMoreThan);
                        if (playerFound(playerToTrade)) {
                            return team;
                        }
                    }
                }
            }
        }
        return null;
    }

    public boolean isTeamDifferent(ITeam teamA, ITeam teamB) {
        if (teamA == teamB) {
            return false;
        }
        return true;
    }

    public boolean playerFound(IPlayer player) {
        if (player == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean teamFound(ITeam team) {
        if (team == null) {
            return false;
        } else {
            return true;
        }
    }

    public IPlayer findPlayerToTrade(ITeam team, String positionWanted, double playerStrengthShouldBeMoreThan) {
        IPlayer player = getWeakPlayer(team, positionWanted);
        if (playerFound(player)) {
            if (player.getPlayerStrength() > playerStrengthShouldBeMoreThan) {
                return player;
            }
        }
        return null;
    }

}
