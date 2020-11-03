package dhl.businessLogic.leagueModel.interfaceModel;

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
