package dhl.businessLogic.trade;

import dhl.businessLogic.leagueModel.PlayerPosition;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.interfaces.IUpdateUserTeamRoster;
import dhl.businessLogic.trade.interfaces.ITradeOffer;
import dhl.businessLogic.trade.interfaces.ITradeType;
import dhl.inputOutput.ui.IUserInputOutput;

import java.util.ArrayList;
import java.util.List;

public class AiUserTrade implements ITradeType {

    private static final int TOTAL_GOALIES = 4;
    private static final int TOTAL_FORWARDS = 16;
    private static final int TOTAL_DEFENSE = 10;

    IUserInputOutput ioObject;
    ITradeOffer tradeOffer;
    IUpdateUserTeamRoster updateUserTeamRoster;

    public AiUserTrade(ITradeOffer tradeOffer, IUserInputOutput ioObject, IUpdateUserTeamRoster updateUserTeamRoster) {
        this.tradeOffer = tradeOffer;
        this.ioObject = ioObject;
        this.updateUserTeamRoster = updateUserTeamRoster;
    }

    public boolean isTradeAccepted(){
        DisplayTradeOfferToUser(tradeOffer.getOfferingPlayers());
        int inputFromUser = Integer.parseInt(ioObject.getUserInput());

        if (inputFromUser == 1) {
            ioObject.printMessage("Trade Accepted, Thankyou");
            return true;
        } else if (inputFromUser == 2) {
            ioObject.printMessage("Trade Rejected, Thankyou");
            return false;
        }
        return false;
    }

    public void validateTeamRosterAfterTrade(ITeam team, ILeagueObjectModel leagueObjectModel) {

        int totalForwards = 0;
        int totalDefense = 0;
        int totalGoalies = 0;
        ArrayList<IPlayer> players = (ArrayList<IPlayer>) team.getPlayers();

        for (IPlayer player : players) {
            String position = player.getPosition();
            if (position.equals(PlayerPosition.FORWARD.toString())){
                totalForwards = totalForwards + 1;
            }
            else if (position.equals(PlayerPosition.DEFENSE.toString())){
                totalDefense = totalDefense + 1;
            }
            else if (position.equals(PlayerPosition.GOALIE.toString())) {
                totalGoalies = totalGoalies + 1;
            }
        }
        if(totalDefense > TOTAL_DEFENSE || totalDefense < TOTAL_DEFENSE){
            updatePlayer(totalDefense, PlayerPosition.DEFENSE.toString(), TOTAL_DEFENSE, team, leagueObjectModel);
        }
        if(totalForwards > TOTAL_FORWARDS || totalForwards < TOTAL_FORWARDS){
            updatePlayer(totalForwards, PlayerPosition.FORWARD.toString(), TOTAL_FORWARDS, team, leagueObjectModel);
        }
        if(totalGoalies > TOTAL_GOALIES || totalGoalies < TOTAL_GOALIES){
            updatePlayer(totalGoalies, PlayerPosition.GOALIE.toString(), TOTAL_GOALIES, team, leagueObjectModel);
        }
    }

    public void updatePlayer(int currentCount, String playerPosition, int requierdCount, ITeam team, ILeagueObjectModel league){
        if(currentCount > requierdCount){
            while (currentCount > requierdCount){
                updateUserTeamRoster.dropPlayer(playerPosition, team, league);
                currentCount = currentCount - 1;
            }
        }
        else if(currentCount < requierdCount){
            while (currentCount < requierdCount){
                updateUserTeamRoster.addPlayer(playerPosition, team, league);
                currentCount = currentCount + 1;
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
