package dhl.leagueModel;

import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.IPlayerStatistics;

public class Player implements IPlayer {
    private String playerName;
    private PlayerPosition position;
    private Boolean captain;
    private IPlayerStatistics playerStats;
    private int playerInjuredDays;

    public Player(){
        setDefaults();
    }

    public void setDefaults() {
        playerName = "";
        position = null;
        this.playerInjuredDays=-1;
    }

    public Player(String playerName,String position,IPlayerStatistics playerStats){
        this.playerName=playerName;
        this.setPosition(position);
        this.playerStats=playerStats;
        this.playerInjuredDays=-1;
    }
    public Player(String playerName,String position,Boolean captain,IPlayerStatistics playerStats){
        this(playerName,position,playerStats);
        this.setCaptain(captain);
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

    public void setCaptain(Boolean isCaptain) {
        this.captain=isCaptain;
    }

    public boolean getCaptain() {
        return captain;
    }

    public IPlayerStatistics getPlayerStats() {
        return playerStats;
    }

    public int getPlayerInjuredDays() {
        return playerInjuredDays;
    }

    public void setPlayerInjuredDays(int playerInjuredDays) {
        this.playerInjuredDays = playerInjuredDays;
    }

    public boolean isPlayerNameEmpty(){
       return playerName.isEmpty();
    }

    public boolean isPlayerPositionInvalid() {
        return this.position == null;
    }

    public boolean isCaptainValueBoolean(){
        return this.captain == null;
    }

    public boolean checkPlayerValid() throws Exception {
        if(this.isPlayerNameEmpty()){
            throw new Exception("Player name cannot be empty");
        }
        if(this.isPlayerPositionInvalid()){
            throw new Exception("Player position must be goalie or forward or defense");
        }
        if(this.isCaptainValueBoolean()){
            throw new Exception("Captain value must be true or false");
        }
        playerStats.checkPlayerStatistics();
        return true;
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
        if(playerInjuredDays>0){
            playerStrength=playerStrength/2.0;
        }
        return playerStrength;
    }

}

