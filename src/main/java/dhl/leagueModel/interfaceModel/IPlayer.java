package dhl.leagueModel.interfaceModel;

import dhl.InputOutput.importJson.Interface.IGameConfig;

import java.util.Date;

public interface IPlayer {

    String getPlayerName();

    String getPosition();

    boolean getCaptain();

    IPlayerStatistics getPlayerStats();

    public int getPlayerInjuredDays();

    public void setPlayerInjuredDays(int playerInjuredDays);

    boolean checkPlayerValid() throws Exception;

    double getPlayerStrength();

}
