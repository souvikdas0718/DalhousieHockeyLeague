package dhl.inputOutput.ui;

import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.inputOutput.ui.interfaces.IListFormat;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class PlayerListFormat implements IListFormat {

    private static final Logger logger = LogManager.getLogger(PlayerListFormat.class);
    IUserInputOutput ioObject;
    private static IListFormat singletonObject = null;

    private PlayerListFormat() {
        ioObject = IUserInputOutput.getInstance();
    }

    public static IListFormat getInstance() {
        if (singletonObject == null) {
            logger.debug("PlayerListFormat's instance created");
            singletonObject = new PlayerListFormat();
        }
        logger.debug("PlayerListFormat's instance accessed");
        return singletonObject;
    }

    public void showList(List playerArrayList) {
        logger.debug("Showing player list to user");
        String freeAgentListHeader = String.format("%10s %20s %20s %10s %10s %10s %10s %10s", "ID", "Name", "Position", "Checking", "Saving", "Shooting", "Skating", "Strength");
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
