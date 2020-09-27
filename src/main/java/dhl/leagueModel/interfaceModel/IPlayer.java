package dhl.leagueModel.interfaceModel;

import dhl.leagueModel.PlayerPosition;

public interface IPlayer {

    public void setDefaults();

    public void setPlayerId(int id);
    public int getPlayerId();

    public void setPlayerName(String playerName);
    public String getPlayerName();

    public void setPosition(PlayerPosition playerPosition);
    public String getPosition();

    public void setCaptain(Boolean isCaptain);
    public Boolean getCaptain();

    public void setTeamName(String teamName);
    public String getTeamName();

    public boolean isPlayerNameEmpty();
}
