package dhl.leagueModel;

import dhl.leagueModel.interfaceModel.IFreeAgent;
import dhl.leagueModel.interfaceModel.IPlayerStatistics;

public class FreeAgent implements IFreeAgent {
    private int playerId;
    private String playerName;
    private PlayerPosition position;
    private IPlayerStatistics playerStats;

    public FreeAgent(){
        setDefaults();
    }

    public void setDefaults() {
        playerName = "";
        position = null;
        playerStats=null;
    }

    public FreeAgent(String playerName, String position,IPlayerStatistics playerStatistics){
        this.setPlayerName(playerName);
        this.setPosition(position);
        this.setPlayerStats(playerStatistics);
    }

    public void setPlayerId(int id) {
        this.playerId=id;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerName(String playerName) {
        this.playerName=playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPosition(String playerPosition) {
        switch (playerPosition){
            case "goalie": {
                this.position=PlayerPosition.GOALIE;
                break;
            }
            case "forward": {
                this.position=PlayerPosition.FORWARD;
                break;
            }
            case "defense":{
                this.position=PlayerPosition.DEFENSE;
                break;
            }
        }

    }

    public String getPosition() {
        if(position==null){
            return "";
        }
        else return position.toString();
    }

    public IPlayerStatistics getPlayerStats() {
        return playerStats;
    }

    public void setPlayerStats(IPlayerStatistics playerStats) {
        this.playerStats = playerStats;
    }

}

