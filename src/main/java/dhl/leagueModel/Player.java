package dhl.leagueModel;

import dhl.leagueModel.interfaceModel.IPlayer;

public class Player implements IPlayer {
    private int playerId;
    private String playerName;
    private PlayerPosition position;
    private boolean captain;
    private String teamName;

    public Player(){
        setDefaults();
    }
    public void setDefaults() {
        playerName = "";
        position = null;
        captain = false;
        teamName = "";
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

    public void setPosition(PlayerPosition playerPosition) {
        this.position=playerPosition;
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
    public Boolean getCaptain() {
        return captain;
    }

    public void setTeamName(String teamName) {
        this.teamName=teamName;
    }

    public String getTeamName() {
        return teamName;
    }


    public boolean isPlayerNameEmpty(){
       return playerName.isEmpty();
    }


}

