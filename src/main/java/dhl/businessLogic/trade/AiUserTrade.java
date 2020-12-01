package dhl.businessLogic.trade;

import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.teamRosterUpdater.RosterUpdaterAbstractFactory;
import dhl.businessLogic.teamRosterUpdater.interfaces.ITeamRosterUpdater;
import dhl.businessLogic.trade.interfaces.ITradeType;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class AiUserTrade implements ITradeType {

    ITeamRosterUpdater updateUserTeamRoster;
    RosterUpdaterAbstractFactory rosterUpdater;
    IUserInputOutput ioObject;
    ILeagueObjectModel league;
    private static final Logger logger = LogManager.getLogger(AiUserTrade.class);

    public AiUserTrade(IUserInputOutput ioObject, ILeagueObjectModel league) {
        this.rosterUpdater = RosterUpdaterAbstractFactory.instance();
        this.ioObject = ioObject;
        this.updateUserTeamRoster = rosterUpdater.createUpdateUserTeamRoster(ioObject);
        this.league = league;
    }

    public boolean isTradeAccepted(ArrayList<IPlayer> playersOffered, ArrayList<IPlayer> playerswanted, ITeam receivingTeam) {
        logger.debug("Showing Trade to user");
        DisplayTradeOfferToUser(playersOffered);
        int inputFromUser = Integer.parseInt(ioObject.getUserInput());
        if (inputFromUser == 1) {
            logger.info("Trade Accepted by " + receivingTeam.getTeamName());
            ioObject.printMessage("Trade Accepted, Thankyou");
            return true;
        } else if (inputFromUser == 2) {
            logger.info("Trade Rejected by " + receivingTeam.getTeamName());
            ioObject.printMessage("Trade Rejected, Thankyou");
            return false;
        }
        return false;
    }

    public void validateTeamRosterAfterTrade(ITeam team) {
        updateUserTeamRoster.validateTeamRoster(team, league);
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
