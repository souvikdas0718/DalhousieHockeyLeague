package dhl.businessLogic.trade;

import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogic.teamRosterUpdater.RosterUpdaterAbstractFactory;
import dhl.businessLogic.teamRosterUpdater.interfaces.ITeamRosterUpdater;
import dhl.businessLogic.trade.factory.TradeAbstractFactory;
import dhl.businessLogic.trade.factory.TradeConcreteFactory;
import dhl.businessLogic.trade.interfaces.IScout;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TradingEngine extends TradeEngineAbstract {

    private TradeOfferAbstract currentTrade;
    private IGameConfig gameConfig;
    private ILeagueObjectModel leagueObjectModel;
    IUserInputOutput ioObject;
    ITeam userTeam;
    ITeamRosterUpdater updateUserTeamRoster;
    TradeAbstractFactory factory;

    private static final Logger logger = LogManager.getLogger(TradingEngine.class);

    public TradingEngine(IGameConfig gameConfig, ILeagueObjectModel leagueObjectModel, ITeam userTeam) {
        factory = new TradeConcreteFactory();

        this.ioObject = IUserInputOutput.getInstance();
        this.updateUserTeamRoster = RosterUpdaterAbstractFactory.instance().createUpdateUserTeamRoster(ioObject);
        this.gameConfig = gameConfig;
        this.leagueObjectModel = leagueObjectModel;
        this.userTeam = userTeam;
    }

    public void startEngine() {
        logger.info("Starting trade Engine");
        long configLossPoint = Long.parseLong(gameConfig.getValueFromOurObject(gameConfig.getTrading(), gameConfig.getLossPoint()));
        double configRandomTradeChance = Double.parseDouble(gameConfig.getValueFromOurObject(gameConfig.getTrading(), gameConfig.getRandomTradeOfferChance()));
        for (IConference conference : leagueObjectModel.getConferences()) {
            for (IDivision division : conference.getDivisions()) {
                for (ITeam team : division.getTeams()) {
                    if (findLossPointOfTheTeam(team) > configLossPoint) {
                        logger.info(team.getTeamName() + " is eligible to Trade");
                        double randomNumber = Math.random();
                        logger.debug("random chance to trade is" + randomNumber);
                        if (randomNumber > configRandomTradeChance) {
                            logger.info("Starting trade search for team: " + team.getTeamName());
                            performTrade(team);
                        }
                    }
                }
            }
        }
    }

    public void performTrade(ITeam tradingTeam) {
        IScout teamScout = factory.createScout(tradingTeam, leagueObjectModel, gameConfig, userTeam);
        int congifMaxPlayerPerTrade = Integer.parseInt(gameConfig.getValueFromOurObject(gameConfig.getTrading(), gameConfig.getMaxPlayersPerTrade()));
        logger.info("Finding trade offer for team: " + tradingTeam.getTeamName());
        currentTrade = teamScout.findTrade(congifMaxPlayerPerTrade);
        if (currentTrade == null) {
            logger.info("Trade not possible for Team:" + tradingTeam.getTeamName());
        } else {
            logger.info("Trade found for team: " + tradingTeam.getTeamName());
            tradingTeam.setLossPoint(0);
            if (currentTrade.checkIfTradeAccepted()) {
                ioObject.printMessage("Trade Done Between Team :" + currentTrade.offeringTeam.getTeamName() + " and " + currentTrade.receivingTeam.getTeamName());
                currentTrade.implementTrade();
            }
        }
    }

    public int findLossPointOfTheTeam(ITeam team) {
        int teamLossPoint = team.getLossPoint();
        return teamLossPoint;
    }

    public void setIoObject(IUserInputOutput ioObject) {
        this.ioObject = ioObject;
    }

    public TradeOfferAbstract getCurrentTrade() {
        return currentTrade;
    }

}
