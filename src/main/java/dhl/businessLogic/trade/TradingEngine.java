package dhl.businessLogic.trade;

import dhl.businessLogic.simulationStateMachine.UpdateUserTeamRoster;
import dhl.businessLogic.trade.factory.TradeAbstractFactory;
import dhl.businessLogic.trade.factory.TradeConcreteFactory;
import dhl.inputOutput.ui.IUserInputOutput;
import dhl.inputOutput.importJson.interfaces.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogic.simulationStateMachine.interfaces.IUpdateUserTeamRoster;
import dhl.businessLogic.trade.interfaces.ITradeOffer;
import dhl.businessLogic.trade.interfaces.ITradeType;
import dhl.businessLogic.trade.interfaces.ITradingEngine;
import dhl.inputOutput.ui.UserInputOutput;

import java.util.ArrayList;
import java.util.List;

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
        this.updateUserTeamRoster = new UpdateUserTeamRoster(ioObject);

        this.gameConfig = gameConfig;
        this.leagueObjectModel = leagueObjectModel;
        this.userTeam = userTeam;
    }

    public void startEngine() {
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
    }

    public ITradeOffer getCurrentTrade() {
        return currentTrade;
    }

    public void performTrade(ITeam tradingTeam){
        try{
            ITeam teamToTradeWith = findTeamToTradeWith(tradingTeam);
            currentTrade = generateTradeOffer(tradingTeam, teamToTradeWith);
            tradingTeam.setLossPoint(0);
            sendTradeToRecevingTeam(currentTrade, userTeam);
        } catch (Exception e) {
            ioObject.printMessage(e.getMessage());
        }
    }

    public void sendTradeToRecevingTeam(ITradeOffer currentTrade, ITeam userTeam) throws Exception {
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

    public ITeam findTeamToTradeWith(ITeam tradingTeam) throws Exception {
        for (IConference conference : leagueObjectModel.getConferences()) {
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
        throw new Exception(" No Good Player availabe to swap for Team: " + tradingTeam.getTeamName());
    }

    public int findLossPointOfTheTeam(ITeam team) {
        int teamLossPoint = team.getLossPoint();
        return teamLossPoint;
    }

    public ITradeOffer generateTradeOffer(ITeam teamOffering, ITeam teamGettingOffer) throws Exception {
        ArrayList<IPlayer> offeringTeamPayers = sortPlayerList(teamOffering);
        ArrayList<IPlayer> secondTeamPlayers = sortPlayerList(teamGettingOffer);
        ArrayList<IPlayer> playersOffered = new ArrayList<>();
        ArrayList<IPlayer> playersWanted = new ArrayList<>();
        int congifMaxPlayerPerTrade = Integer.parseInt(gameConfig.getValueFromOurObject(gameConfig.getTrading(), gameConfig.getMaxPlayersPerTrade()));
        int maxPlayersInTrade = 0;
        for (IPlayer playerToBeOffered : offeringTeamPayers) {
            for (IPlayer playerToGetInExchange : secondTeamPlayers) {
                if (maxPlayersInTrade + 2 <= congifMaxPlayerPerTrade) {
                    if (playerToGetInExchange.getPosition().equals(playerToBeOffered.getPosition())) {
                        if (playerToGetInExchange.getPlayerStrength() > playerToBeOffered.getPlayerStrength()) {
                            if (isPlayerNotInWantedList(playerToGetInExchange, playersWanted)) {
                                playersOffered.add(playerToBeOffered);
                                playersWanted.add(playerToGetInExchange);
                                maxPlayersInTrade += 2;
                                break;
                            }
                        }
                    }
                }
            }
        }
        ITradeOffer newOffer = factory.createExchangingPlayerTradeOffer(teamOffering, teamGettingOffer, playersOffered, playersWanted);
        return newOffer;
    }

    public boolean isPlayerNotInWantedList(IPlayer playerToGetInExchange, ArrayList<IPlayer> playersWanted) {
        boolean playerNotFound = true;
        for (IPlayer player : playersWanted) {
            if (player == playerToGetInExchange) {
                playerNotFound = false;
            }
        }
        return playerNotFound;
    }

    public ArrayList<IPlayer> sortPlayerList(ITeam tradingTeam) throws Exception {
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
        } else {
            throw new Exception(tradingTeam.getTeamName() + " Team have no players");
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

    public boolean isTeamGoodForTrading(ITeam teamOffering, ITeam teamGettingOffer) throws Exception {

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
}
