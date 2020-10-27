package dhl.leagueModel;

import dhl.importJson.Interface.IGameConfig;
import dhl.leagueModel.interfaceModel.IInjurySystem;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.IPlayerStatistics;
import java.util.Date;

public class Player implements IPlayer {
    private int playerId;
    private String playerName;
    private PlayerPosition position;
    private Boolean captain;
    private IPlayerStatistics playerStats;
    private IInjurySystem injurySystem;

    public Player(){
        setDefaults();
    }

    public void setDefaults() {
        playerName = "";
        position = null;
        this.injurySystem=new InjurySystem();
    }

    public Player(String playerName,String position,Boolean captain,IPlayerStatistics playerStats){
        this.setPlayerName(playerName);
        this.setPosition(position);
        this.setCaptain(captain);
        this.setPlayerStats(playerStats);
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

    public void setCaptain(Boolean isCaptain) {
        this.captain=isCaptain;
    }

    public boolean getCaptain() {
        return captain;
    }

    public IPlayerStatistics getPlayerStats() {
        return playerStats;
    }

    public void setPlayerStats(IPlayerStatistics playerStats) {
        this.playerStats = playerStats;
    }

    public IInjurySystem getInjurySystem() {
        return injurySystem;
    }

    public void setInjurySystem(IInjurySystem injurySystem) {
        this.injurySystem = injurySystem;
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

    public boolean isPlayerNotAlreadyInjured(){
        if(injurySystem.isInjured()){
            return false;
        }
        return true;
    }

    public IPlayer checkPlayerInjury(IGameConfig gameConfig, Date currentDate){
        if(isPlayerNotAlreadyInjured()){
            this.setInjurySystem(injurySystem.checkIfPlayerInjured(gameConfig,currentDate));
        }
        return this;
    }

    public double getPlayerStrength(){
        double playerStrength=0;
        if(position == PlayerPosition.FORWARD){
            playerStrength=playerStats.getSkating() + playerStats.getShooting() + (playerStats.getChecking() / 2);
        }
        else if(position == PlayerPosition.DEFENSE){
            playerStrength=playerStats.getSkating() + playerStats.getChecking() + (playerStats.getShooting() / 2);
        }
        else if(position == PlayerPosition.GOALIE){
            playerStrength=playerStats.getSkating() + playerStats.getSaving() ;
        }
        if(injurySystem.isInjured()){
            playerStrength=playerStrength/2;
        }
        return playerStrength;
    }

}

