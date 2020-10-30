package dhl.leagueModel.interfaceModel;

import dhl.importJson.Interface.IGameConfig;

import java.util.Date;

public interface IPlayer {

    public void setDefaults();

    public void setPlayerId(int id);

    public int getPlayerId();

    public void setPlayerName(String playerName);

    public String getPlayerName();

    public void setPosition(String playerPosition);

    public String getPosition();

    public void setCaptain(Boolean isCaptain);

    public boolean getCaptain();

    public IPlayerStatistics getPlayerStats();

    public void setPlayerStats(IPlayerStatistics playerStats);

    public IInjurySystem getInjurySystem();

    public void setInjurySystem(IInjurySystem injurySystem);

    public boolean isPlayerNameEmpty();

    public  boolean isPlayerPositionInvalid();

    public boolean isCaptainValueBoolean();

    public boolean checkPlayerValid() throws Exception;

    public void checkPlayerInjury(IGameConfig gameConfig, Date currentDate);

    public double getPlayerStrength();

}
