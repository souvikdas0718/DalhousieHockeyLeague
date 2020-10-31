package dhl.trade;

import dhl.InputOutput.UI.IUserInputOutput;
import dhl.InputOutput.UI.UserInputOutput;
import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.trade.Interface.ITradeOffer;
import dhl.trade.Interface.ITradeType;

public class AiUserTrade implements ITradeType {

    IUserInputOutput ioObject;
    ITradeOffer tradeOffer;

    public AiUserTrade(ITradeOffer tradeOffer, IUserInputOutput ioObject){
        this.tradeOffer = tradeOffer;
        this.ioObject = ioObject;
    }

    @Override
    public boolean isTradeAccepted() {
        try{
            DisplayTradeOfferToUser();
            int inputfromUser = Integer.parseInt(ioObject.getUserInput());

            if (inputfromUser == 1){
                return true;
            }else if( inputfromUser == 2){
                return false;
            }else{
                throw new Exception("Wrong Input please give valid input");
            }
        }catch(Exception e){
            ioObject.printMessage("----------ERROR----------");
            ioObject.printMessage(e.getMessage());
        }

        return false;
    }

    public void DisplayTradeOfferToUser(){
        ioObject.printMessage("---------------NEW TRADE OFFER FOR YOU---------------");
        ioObject.printMessage("Players you will get are");
        for(IPlayer player : tradeOffer.getOfferingPlayers()){
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
