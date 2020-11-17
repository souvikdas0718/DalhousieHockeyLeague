package dhl.businessLogic.leagueModel.constants;

public enum TeamConstant {
    TOTALTEAMSIZE(30),
    TOTALGOALIES(4),
    TOTALFORWARDS(16),
    TOTALDEFENSE(10),
    NOOFSKATERS(18),
    NOOFFORWARDS(12),
    NOOFDEFENCE(6),
    NOOFGOALIES(2);

    private final int value;

    TeamConstant(int constantValue) {
        value = constantValue;
    }

    public int getValue() {
        return value;
    }
}
