package dhl.leagueModel.interfaceModel;

public interface IFreeAgent {

    public void setDefaults();

    public void setPlayerId(int id);

    public int getPlayerId();

    public void setPlayerName(String playerName);

    public String getPlayerName();

    public void setPosition(String playerPosition);

    public String getPosition();
}
