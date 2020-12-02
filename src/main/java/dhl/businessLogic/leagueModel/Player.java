package dhl.businessLogic.leagueModel;

import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayerStatistics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Player implements IPlayer {
    private static final int INJUREDDAYSDEFAULTVALUE = -1;
    private static final Logger logger = LogManager.getLogger(Player.class);
    private String playerName;
    private PlayerPosition position;
    private Boolean captain;
    private IPlayerStatistics playerStats;
    private int playerInjuredDays;
    private boolean active;

    public void setDefaults() {
        playerName = "";
        position = null;
        this.playerInjuredDays = INJUREDDAYSDEFAULTVALUE;
        this.active = false;
    }

    public Player() {
        setDefaults();
    }

    public Player(String playerName, String position, IPlayerStatistics playerStats) {
        this();
        logger.info("Creating player with name " + playerName);
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
        logger.info("Setting player position" + playerName);
        for (PlayerPosition position : PlayerPosition.values()) {
            if (playerPosition.equals(position.toString())) {
                logger.debug("Player Position set as" + position);
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
        if (captain == null) {
            return false;
        }
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
        logger.debug("Player position incorrect for player" + playerName);
        return this.position == null;
    }

    public boolean isCaptainValueBoolean() {
        logger.debug("Captain value incorrect for player" + playerName);
        return this.captain == null;
    }

    public double getPlayerStrength() {
        logger.info("Calculating strength of player" + playerName);
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
        logger.debug("Strength of player" + playerName + "is" + playerStrength);
        return playerStrength;
    }
}

