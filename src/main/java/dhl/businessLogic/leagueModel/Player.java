package dhl.businessLogic.leagueModel;

import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayerStatistics;

public class Player implements IPlayer {
    private static final int INJUREDDAYSDEFAULTVALUE=-1;
    private String playerName;
    private PlayerPosition position;
    private Boolean captain;
    private IPlayerStatistics playerStats;
    private int playerInjuredDays;
    private boolean active;

    public Player() {
        setDefaults();
    }

    public void setDefaults() {
        playerName = "";
        position = null;
        this.playerInjuredDays = INJUREDDAYSDEFAULTVALUE;
        this.active = false;
    }

    public Player(String playerName, String position, IPlayerStatistics playerStats) {
        setDefaults();
        this.playerName = playerName;
        this.setPosition(position);
        this.playerStats = playerStats;
    }

    public Player(String playerName, String position, Boolean captain, IPlayerStatistics playerStats) {
        this(playerName, position, playerStats);
        this.setCaptain(captain);
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPosition(String playerPosition) {
        this.position = null;
        for(PlayerPosition position:  PlayerPosition.values()){
            if(playerPosition.equals(position.toString())){
                this.position = position;
                break;
            }
        }
    }

    public String getPosition() {
        if (position == null) {
            return "";
        } else {
            return position.toString();
        }
    }

    public void setCaptain(Boolean isCaptain) {
        this.captain = isCaptain;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isPlayerNameEmpty() {
        return playerName.length() == 0;
    }

    public boolean isPlayerPositionInvalid() {
        return this.position == null;
    }

    public boolean isCaptainValueBoolean() {
        return this.captain == null;
    }

    public boolean checkPlayerValid() throws Exception {
        if (this.isPlayerNameEmpty()) {
            throw new Exception("Player name cannot be empty");
        }
        if (this.isPlayerPositionInvalid()) {
            throw new Exception("Player position must be goalie or forward or defense");
        }
        if (this.isCaptainValueBoolean()) {
            throw new Exception("Captain value must be true or false");
        }
        playerStats.checkPlayerStatistics();
        return true;
    }

    public double getPlayerStrength() {
        double playerStrength = 0;
        if (position == PlayerPosition.FORWARD) {
            playerStrength = playerStats.getSkating() + playerStats.getShooting() + (playerStats.getChecking() / 2.0);
        } else if (position == PlayerPosition.DEFENSE) {
            playerStrength = playerStats.getSkating() + playerStats.getChecking() + (playerStats.getShooting() / 2.0);
        } else if (position == PlayerPosition.GOALIE) {
            playerStrength = playerStats.getSkating() + playerStats.getSaving();
        }
        if (playerInjuredDays > 0) {
            playerStrength = playerStrength / 2.0;
        }
        return playerStrength;
    }
}

