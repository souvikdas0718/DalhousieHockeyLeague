package dhl.leagueModel.interfaceModel;

import dhl.leagueModel.PlayerPosition;

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

    public void setTeamName(String teamName);
    public String getTeamName();

    public boolean isPlayerNameEmpty();
    public  boolean isPlayerPositionInvalid();
    public boolean isCaptainValueBoolean();
    public boolean checkPlayerValid() throws Exception;
}