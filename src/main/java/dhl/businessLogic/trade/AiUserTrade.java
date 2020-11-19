package dhl.businessLogic.trade;

import dhl.businessLogic.leagueModel.PlayerPosition;
import dhl.inputOutput.ui.IUserInputOutput;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.interfaces.IUpdateUserTeamRoster;
import dhl.businessLogic.trade.interfaces.ITradeOffer;
import dhl.businessLogic.trade.interfaces.ITradeType;

import java.util.ArrayList;
import java.util.List;

public class AiUserTrade implements ITradeType {

    IUserInputOutput ioObject;
    ITradeOffer tradeOffer;
    IUpdateUserTeamRoster updateUserTeamRoster;

    public AiUserTrade(ITradeOffer tradeOffer, IUserInputOutput ioObject, IUpdateUserTeamRoster updateUserTeamRoster) {
        this.tradeOffer = tradeOffer;
        this.ioObject = ioObject;
        this.updateUserTeamRoster = updateUserTeamRoster;
    }

    public boolean isTradeAccepted() throws Exception {
        DisplayTradeOfferToUser(tradeOffer.getOfferingPlayers());
        int inputFromUser = Integer.parseInt(ioObject.getUserInput());

        if (inputFromUser == 1) {
            return true;
        } else if (inputFromUser == 2) {
            return false;
        } else {
            throw new Exception("Wrong Input please give valid input");
        }
    }

    public void validateTeamRosterAfterTrade(ITeam team, ILeagueObjectModel leagueObjectModel) throws Exception {
        int totalSkaters = 0;
        int totalGoalies = 0;
        ArrayList<IPlayer> players = (ArrayList<IPlayer>) team.getPlayers();

        for (IPlayer player : players) {
            String position = player.getPosition();
            if (position.equals(PlayerPosition.FORWARD) || position.equals(PlayerPosition.DEFENSE)) {
                totalSkaters = totalSkaters + 1;
            }
            if (position.equals(PlayerPosition.GOALIE)) {
                totalGoalies = totalGoalies + 1;
            }
        }
        if (totalSkaters > 18) {
            while (totalSkaters > 18) {
                updateUserTeamRoster.dropSkater(team, leagueObjectModel);
                totalSkaters = totalSkaters - 1;
            }
        } else if (totalSkaters < 18) {
            while (totalSkaters < 18) {
                updateUserTeamRoster.addSkater(team, leagueObjectModel);
                totalSkaters = totalSkaters + 1;
            }
        }
        if (totalGoalies > 2) {
            while (totalGoalies > 2) {
                updateUserTeamRoster.dropGoalie(team, leagueObjectModel);
                totalGoalies = totalGoalies - 1;
            }

        } else if (totalGoalies < 2) {
            while (totalGoalies < 2) {
                updateUserTeamRoster.addGoalie(team, leagueObjectModel);
                totalGoalies = totalGoalies + 1;
            }
        }
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
