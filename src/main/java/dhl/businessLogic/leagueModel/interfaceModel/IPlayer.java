package dhl.businessLogic.leagueModel.interfaceModel;

public interface IPlayer {

    String getPlayerName();

    String getPosition();

    boolean getCaptain();

    IPlayerStatistics getPlayerStats();

    int getPlayerInjuredDays();

    void setPlayerInjuredDays(int playerInjuredDays);

    boolean isActive();

    void setActive(boolean active);

    double getPlayerStrength();

    boolean isPlayerNameEmpty();

    boolean isPlayerPositionInvalid();

    boolean isCaptainValueBoolean();


}
