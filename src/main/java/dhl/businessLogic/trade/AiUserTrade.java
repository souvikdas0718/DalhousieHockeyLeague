package dhl.businessLogic.trade;

import dhl.businessLogic.leagueModel.PlayerPosition;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.interfaces.ITeamRosterUpdater;
import dhl.businessLogic.trade.interfaces.ITradeOffer;
import dhl.businessLogic.trade.interfaces.ITradeType;
import dhl.inputOutput.ui.IUserInputOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

public class AiUserTrade implements ITradeType {

    private static final int TOTAL_GOALIES = 4;
    private static final int TOTAL_FORWARDS = 16;
    private static final int TOTAL_DEFENSE = 10;

    IUserInputOutput ioObject;
    ITradeOffer tradeOffer;
    ITeamRosterUpdater updateUserTeamRoster;

    private static final Logger logger = LogManager.getLogger(AiUserTrade.class);

    public AiUserTrade(ITradeOffer tradeOffer, IUserInputOutput ioObject, ITeamRosterUpdater updateUserTeamRoster) {
        this.tradeOffer = tradeOffer;
        this.ioObject = ioObject;
        this.updateUserTeamRoster = updateUserTeamRoster;
        logger.info("Trade made for User by "+tradeOffer.getOfferingTeam().getTeamName());
    }

    public boolean isTradeAccepted(){
        DisplayTradeOfferToUser(tradeOffer.getOfferingPlayers());
        int inputFromUser = Integer.parseInt(ioObject.getUserInput());

        if (inputFromUser == 1) {
            logger.info("Trade Accepted by "+ tradeOffer.getReceivingTeam().getTeamName());
            ioObject.printMessage("Trade Accepted, Thankyou");
            return true;
        } else if (inputFromUser == 2) {
            logger.info("Trade Rejected by "+ tradeOffer.getReceivingTeam().getTeamName());
            ioObject.printMessage("Trade Rejected, Thankyou");
            return false;
        }
        return false;
    }

    public void validateTeamRosterAfterTrade(ITeam team, ILeagueObjectModel leagueObjectModel) {

        updateUserTeamRoster.validateTeamRoster(team, leagueObjectModel);

    }

    public void DisplayTradeOfferToUser(List<IPlayer> playerList) {
        ioObject.printMessage("---------------NEW TRADE OFFER FOR YOU---------------");
        ioObject.printMessage("Players you will get are");
        for (IPlayer player : playerList) {
            ioObject.printMessage("Player Name: " + player.getPlayerName());
            ioObject.printMessage("Age: " + player.getPlayerStats().getAge());
            ioObject.printMessage("Checking: " + player.getPlayerStats().getChecking());
            ioObject.printMessage("Skating: " + player.getPlayerStats().getSkating());
            ioObject.printMessage("Shooting: " + player.getPlayerStats().getShooting());
            ioObject.printMessage("Saving: " + player.getPlayerStats().getSaving());
        }
        ioObject.printMessage("Enter 1 to Accept Trade, 2 to Reject");
    }

}
