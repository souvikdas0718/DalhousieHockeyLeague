package dhl.businessLogic.leagueModel;

import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.IGenerateDraftPlayers;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayerStatistics;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateDraftPlayers implements IGenerateDraftPlayers {
    private static final int DRAFTROUNDS = 7;
    private static final int NOOFTEAMS = 8;
    private static final int NOOFDIVISIONS = 4;
    private static final int PERCENTAGEOFFORWARDS = 50;
    private static final int PERCENTAGEOFDEFENSE = 40;
    private static final int PERCENTAGEOFGOALIES = 10;
    private static final int SKEWSTAT = 75;
    private static final int[][] FORWARDSTATRANGE = {{12, 20}, {12, 20}, {9, 18}, {1, 7}};
    private static final int[][] DEFENSESTATRANGE = {{10, 19}, {9, 18}, {12, 20}, {1, 12}};
    private static final int[][] GOALIESTATRANGE = {{8, 15}, {1, 10}, {1, 12}, {12, 20}};
    private static final int SKATINGINDEX = 0;
    private static final int SHOOTINGINDEX = 1;
    private static final int CHECKINGINDEX = 2;
    private static final int SAVINGINDEX = 3;
    private static final int MINRANGEINDEX = 0;
    private static final int MAXRANGEINDEX = 1;
    private static final int MINPLAYERAGE = 18;
    private static final int MAXPLAYERAGE = 21;
    private static final int CURRENTYEAR = 2020;
    private static final int MAXDATE = 28;
    private static final int MAXMONTH = 12;
    private static final int MINVALUE = 1;
    private static final int ROWSIZE = 4;
    private static final int COLUMNSIZE = 2;

    private static final String[] FIRSTNAMES = {"Jared", "Gordan", "Walter", "John", "Harry", "Mary", "Fred", "Mohammed", "Bonnie",
            "Tami", "Chris", "Pat", "Sammy", "Abraham", "Tina", "Nancy", "Roger", "Mike", "Rob", "Zongming", "Wen", "Don", "Sai",
            "John", "Josh", "Mozhgan", "Karan", "Jimmy", "Nethan", "Shiro", "James", "Rain", "Younis", "Mathew", "KC", "Henry", "Lively",
            "Landon", "Austin", "Sam", "Steel", "Leo", "Kelly", "Mistero", "Meno", "Jeremy", "Preston", "Enzo", "Luka", "Andres", "Marcus",
            "Felix", "Mark", "Ace", "Brantley", "Atlas", "Remington", "Maximus", "Matias", "Walker", "Kyrie", "Griffin", "Kenneth",
            "Israel", "Javier", "Kyler", "Jax", "Amari", "Zane", "Knox", "Adonis", "Aidan", "Kaden", "Paul", "Omar", "Brian", "Caden"};

    private static final String[] LASTNAMES = {"Singh", "Hawkey", "Fei", "Chang", "Khatri", "Samson", "Williamson", "Thomson",
            "Smith", "Patel", "Wu", "Liu", "Burbchuk", "Cherry", "Hawkins", "Greenbaum", "Bishop", "Marley", "Halen", "Morrison",
            "Nelson", "Rogers", "Hendrix", "Page", "Bonham", "Plant", "Jones", "Julius", "Major", "Donovan", "Caiden", "Tyson",
            "Nico", "Sergio", "Nasir", "Rory", "Devin", "Jaiden", "Jared", "Kason", "Malik", "Jeffrey", "Ismael", "Elian",
            "Marshall", "Lawson", "Desmond", "Winston", "Nehemiah", "Ari", "Conner", "Jay", "Kade", "Andy", "Johnny", "Jayceon",
            "Marco", "Seth", "Ibrahim", "Raiden", "Collin", "Edgar", "Erik", "Troy", "Clark", "Jaxton", "Johnathan", "Gregory",
            "Stevenson", "Kirb", "Kirk", "Burns", "Ron", "Davidson", "Fernandez", "Jackson", "Keny"};
    private static final Logger logger = LogManager.getLogger(GenerateDraftPlayers.class);
    int totalNewPlayers;
    List<IPlayer> draftPlayers;
    PlayerPosition[] playerPosition;
    Random random;
    LeagueModelAbstractFactory factory;

    public GenerateDraftPlayers() {
        logger.info("Generate Draft Player Constructor Initialized");
        this.totalNewPlayers = NOOFTEAMS * NOOFDIVISIONS * DRAFTROUNDS;
        random = new Random();
        factory = LeagueModelAbstractFactory.instance();
        draftPlayers = new ArrayList<>();
        playerPosition = PlayerPosition.values();
    }

    public List<IPlayer> getDraftPlayers() {
        return draftPlayers;
    }

    public List<IPlayer> generateDraftPlayers() {
        logger.info("Generate Draft Player for forward,goalie,defense");
        generatePlayers(PlayerPosition.FORWARD.toString(), PERCENTAGEOFFORWARDS);
        generatePlayers(PlayerPosition.DEFENSE.toString(), PERCENTAGEOFDEFENSE);
        generatePlayers(PlayerPosition.GOALIE.toString(), PERCENTAGEOFGOALIES);
        sortBestPlayerOrder(draftPlayers);
        return draftPlayers;
    }

    public String randomName() {
        logger.debug("Generating names of players");
        return FIRSTNAMES[random.nextInt(FIRSTNAMES.length)] + " " + LASTNAMES[random.nextInt(LASTNAMES.length)];
    }

    public List<IPlayer> generatePlayers(String position, int totalPlayerInPosition) {
        logger.debug("Generate players for each position");
        double result = (totalNewPlayers * totalPlayerInPosition) / 100.00;
        int noOfPlayers = (int) Math.round(result);
        for (int i = 0; i < noOfPlayers; i++) {
            String playerName = randomName();
            IPlayerStatistics playerStatistics = generateStatForPlayer(position);
            draftPlayers.add(this.factory.createPlayer(playerName, position, false, playerStatistics));
        }
        return draftPlayers;
    }

    public IPlayerStatistics generateStatForPlayer(String position) {
        logger.debug("Generate player statistics for position" + position);
        int[][] defaultPlayerStatForPosition = new int[ROWSIZE][COLUMNSIZE];
        if (position.equals(PlayerPosition.FORWARD.toString())) {
            defaultPlayerStatForPosition = FORWARDSTATRANGE;
        } else if (position.equals(PlayerPosition.DEFENSE.toString())) {
            defaultPlayerStatForPosition = DEFENSESTATRANGE;
        } else if (position.equals(PlayerPosition.GOALIE.toString())) {
            defaultPlayerStatForPosition = GOALIESTATRANGE;
        }
        IPlayerStatistics playerStatistics = getPlayerStatistics(defaultPlayerStatForPosition);
        int birthDay = (int) (Math.random() * (MAXDATE - MINVALUE + 1) + MINVALUE);
        int birthMonth = (int) (Math.random() * (MAXMONTH - MINVALUE + 1) + MINVALUE);
        int birthYear = CURRENTYEAR - (int) (Math.random() * (MAXPLAYERAGE - MINPLAYERAGE + 1) + MINPLAYERAGE);
        playerStatistics.setDateOfBirth(birthDay, birthMonth, birthYear);
        return playerStatistics;
    }

    public int getSkewedStatMinValue(int minStat, int maxStat) {
        logger.debug("Get skewed stat min value");
        int rangeDifference = maxStat - minStat;
        int skewRange = (SKEWSTAT * rangeDifference) / 100;
        logger.debug(skewRange);
        return minStat + skewRange;
    }

    public IPlayerStatistics getPlayerStatistics(int[][] stat) {
        logger.info("Generating Player statistics");
        int skewedSkatingMinValue = getSkewedStatMinValue(stat[SKATINGINDEX][MINRANGEINDEX], stat[SKATINGINDEX][MAXRANGEINDEX]);
        int skewedShootingMinValue = getSkewedStatMinValue(stat[SHOOTINGINDEX][MINRANGEINDEX], stat[SHOOTINGINDEX][MAXRANGEINDEX]);
        int skewedCheckingMinValue = getSkewedStatMinValue(stat[CHECKINGINDEX][MINRANGEINDEX], stat[CHECKINGINDEX][MAXRANGEINDEX]);
        int skewedSavingMinValue = getSkewedStatMinValue(stat[SAVINGINDEX][MINRANGEINDEX], stat[SAVINGINDEX][MAXRANGEINDEX]);
        int skating = (int) (Math.random() * (stat[SKATINGINDEX][MAXRANGEINDEX] - skewedSkatingMinValue + 1) + skewedSkatingMinValue);
        int shooting = (int) (Math.random() * (stat[SHOOTINGINDEX][MAXRANGEINDEX] - skewedShootingMinValue + 1) + skewedShootingMinValue);
        int checking = (int) (Math.random() * (stat[CHECKINGINDEX][MAXRANGEINDEX] - skewedCheckingMinValue + 1) + skewedCheckingMinValue);
        int saving = (int) (Math.random() * (stat[SAVINGINDEX][MAXRANGEINDEX] - skewedSavingMinValue + 1) + skewedSavingMinValue);

        return this.factory.createPlayerStatistics(skating, shooting, checking, saving);
    }

    public void sortBestPlayerOrder(List<IPlayer> players) {
        logger.info("Sorting generated players in order of best player to worst player");
        ITeam team = factory.createTeamDefault();
        team.sortPlayersInTeamByStrength(players);
    }
}
