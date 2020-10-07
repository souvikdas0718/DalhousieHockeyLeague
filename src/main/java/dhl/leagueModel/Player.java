package dhl.leagueModel;

import dhl.leagueModel.interfaceModel.IPlayer;

public class Player implements IPlayer {
    private int playerId;
    private String playerName;
    private PlayerPosition position;
    private Boolean captain;


    public Player(){
        setDefaults();
    }
    public void setDefaults() {
        playerName = "";
        position = null;
    }
    public Player(String playerName,String position,Boolean captain){
        this.setPlayerName(playerName);
        this.setPosition(position);
        this.setCaptain(captain);
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

    public boolean isPlayerNameEmpty(){
       return playerName.isEmpty();
    }

    public boolean isPlayerPositionInvalid() {
        return this.position == null;
    }
    public boolean isCaptainValueBoolean(){
        return this.captain != null;
    }

    public boolean checkPlayerValid() throws Exception {
        if(this.isPlayerNameEmpty()){
            throw new Exception("Player name cannot be empty");
        }
        if(this.isPlayerPositionInvalid()){
            throw new Exception("Player position must be goalie or forward or defense");
        }
        if(!this.isCaptainValueBoolean()){
            throw new Exception("Captain value must be true or false");
        }

        return true;
    }


}

