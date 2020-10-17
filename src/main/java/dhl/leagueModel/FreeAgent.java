package dhl.leagueModel;

import dhl.leagueModel.interfaceModel.IFreeAgent;

public class FreeAgent implements IFreeAgent {
    private int playerId;
    private String playerName;
    private PlayerPosition position;

    public FreeAgent(){
        setDefaults();
    }

    public void setDefaults() {
        playerName = "";
        position = null;
    }

    public FreeAgent(String playerName, String position){
        this.setPlayerName(playerName);
        this.setPosition(position);
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

}

