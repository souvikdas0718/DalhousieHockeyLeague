package dhl.leagueModel;

import dhl.leagueModel.interfaceModel.IFreeAgent;
import dhl.leagueModel.interfaceModel.IInjurySystem;
import dhl.leagueModel.interfaceModel.IPlayerStatistics;

public class FreeAgent implements IFreeAgent {
    private int playerId;
    private String playerName;
    private PlayerPosition position;
    private IPlayerStatistics playerStats;
    private IInjurySystem injurySystem;

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
        this.injurySystem=new InjurySystem();
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

    public double getPlayerStrength(){
        double playerStrength=0;
        if(position == PlayerPosition.FORWARD){
            playerStrength=playerStats.getSkating() + playerStats.getShooting() + (playerStats.getChecking() / 2.0);
        }
        else if(position == PlayerPosition.DEFENSE){
            playerStrength=playerStats.getSkating() + playerStats.getChecking() + (playerStats.getShooting() / 2.0);
        }
        else if(position == PlayerPosition.GOALIE){
            playerStrength=playerStats.getSkating() + playerStats.getSaving() ;
        }
        if(injurySystem.isInjured()){
            playerStrength=playerStrength/2.0;
        }
        return playerStrength;
    }

}

