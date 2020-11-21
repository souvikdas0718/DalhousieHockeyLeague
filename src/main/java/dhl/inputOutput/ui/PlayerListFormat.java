package dhl.inputOutput.ui;

import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;

import java.util.List;

public class PlayerListFormat implements IListFormat {

    IUserInputOutput ioObject;
    private static IListFormat singletonObject = null;

    private PlayerListFormat(){
        ioObject = new UserInputOutput();
    }

    public static IListFormat getInstance(){
        if (singletonObject == null){
            singletonObject = new PlayerListFormat();
        }
        return singletonObject;
    }

    public void showList(List playerArrayList) {
        String freeAgentListHeader = String.format("%10s %20s %20s %10s %10s %10s %10s %10s %10s", "ID", "Name", "Position", "Age", "Checking", "Saving", "Shooting", "Skating", "Strength");
        ioObject.printMessage(freeAgentListHeader);
        int i = 0;
        for (Object p : playerArrayList) {
            IPlayer player = (IPlayer) p;
            double playerStrength = player.getPlayerStrength();
            String formattedFreeAgentList = String.format("%10s %20s %20s %10d %10d %10d %10d %10d %10s", Integer.toString(i), player.getPlayerName(), player.getPosition(), player.getPlayerStats().getAge(), player.getPlayerStats().getChecking(), player.getPlayerStats().getSaving(), player.getPlayerStats().getShooting(), player.getPlayerStats().getSkating(), Double.toString(playerStrength));
            ioObject.printMessage(formattedFreeAgentList);
            i = i + 1;
        }
    }
}
