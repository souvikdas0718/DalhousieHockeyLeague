package dhl.businessLogic.leagueModel;

import dhl.businessLogic.leagueModel.interfaceModel.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Team implements ITeam {
    private static final int TOTALTEAMSIZE = 30;
    private static final int TOTALGOALIES = 4;
    private static final int TOTALFORWARDS = 16;
    private static final int TOTALDEFENSE = 10;
    private static final int NOOFFORWARDS = 12;
    private static final int NOOFDEFENCE = 6;
    private static final int NOOFGOALIES = 2;
    private String teamName;
    private IGeneralManager generalManager;
    private ICoach headCoach;
    private int lossPoint;
    private int teamPoint;
    private List<IPlayer> players;

    public Team() {
        setDefault();
    }

    public void setDefault() {
        teamName = "";
        generalManager = new GeneralManager();
        headCoach = new Coach();
        players = new ArrayList<>();
    }

    public Team(String teamName, IGeneralManager generalManager, ICoach headCoach, List<IPlayer> playersList) {
        setDefault();
        this.teamName = teamName;
        this.generalManager = generalManager;
        this.headCoach = headCoach;
        this.players = playersList;
        if(this.players.size()== TOTALTEAMSIZE){
            this.setRoster();
        }
    }

    public String getTeamName() {
        return teamName;
    }

    public IGeneralManager getGeneralManager() {
        return generalManager;
    }

    public ICoach getHeadCoach() {
        return headCoach;
    }

    public List<IPlayer> getPlayers() {
        return players;
    }

    public int getLossPoint() {
        return lossPoint;
    }

    public void setLossPoint(int lossPoint) {
        this.lossPoint = lossPoint;
    }

    public int getTeamPoint() {
        return teamPoint;
    }

    public void setTeamPoint(int teamPoint) {
        this.teamPoint = teamPoint;
    }

    public List<IPlayer> getInactiveRoster() {
        return players.stream().filter(player -> player.isActive() == false).collect(Collectors.toList());
    }

    public List<IPlayer> getActiveRoster() {
        return players.stream().filter(player -> player.isActive()).collect(Collectors.toList());
    }

    public void setRoster() {
        sortPlayersInTeamByStrength(this.players);
        addPlayersToTeamRoster(PlayerPosition.FORWARD.toString(),NOOFFORWARDS);
        addPlayersToTeamRoster(PlayerPosition.DEFENSE.toString(),NOOFDEFENCE);
        addPlayersToTeamRoster(PlayerPosition.GOALIE.toString(),NOOFGOALIES);
    }

    public void addPlayersToTeamRoster( String position,int playerCount){
        int counter = 0;
        for(IPlayer player:players){
            if(player.getPosition().equals(position)){
                if(counter < playerCount){
                    player.setActive(true);
                    counter=counter+1;
                }
                else {
                    player.setActive(false);
                }
            }
        }
    }

    public void checkIfOneCaptainPerTeam(List<IPlayer> playerList) throws Exception {
        Predicate<IPlayer> playerPredicate = player -> player.getCaptain();
        List<IPlayer> captainList = playerList.stream().filter(playerPredicate).collect(Collectors.toList());
        if ( playerList.size() > 0 && captainList.size() == 0) {
            throw new Exception("Please select captain for the team");
        }
        if (captainList.size() > 1) {
            throw new Exception("There can be only one captain per team");
        }
    }

    public boolean checkIfSizeOfTeamValid(List<IPlayer> playerList) {
        return playerList.size() == TOTALTEAMSIZE;
    }

    public boolean checkTeamPlayersCount() {
        List<IPlayer> activeRoster = getActiveRoster();
        List<IPlayer> goaliesInActiveRoster = filterPlayersInTeam(PlayerPosition.GOALIE.toString(), activeRoster);
        List<IPlayer> forwardsInActiveRoster = filterPlayersInTeam(PlayerPosition.FORWARD.toString(), activeRoster);
        List<IPlayer> defenseInActiveRoster = filterPlayersInTeam(PlayerPosition.DEFENSE.toString(), activeRoster);

        List<IPlayer> goaliesInTeam = filterPlayersInTeam(PlayerPosition.GOALIE.toString(), players);
        List<IPlayer> forwardsInTeam = filterPlayersInTeam(PlayerPosition.FORWARD.toString(), players);
        List<IPlayer> defenseInTeam = filterPlayersInTeam(PlayerPosition.DEFENSE.toString(), players);

        if(goaliesInTeam.size() == TOTALGOALIES && forwardsInTeam.size() == TOTALFORWARDS && defenseInTeam.size() == TOTALDEFENSE
         && goaliesInActiveRoster.size()==NOOFGOALIES && (forwardsInActiveRoster.size()+defenseInActiveRoster.size()==NOOFFORWARDS + NOOFDEFENCE)){
            return true;
        }
        return false;
    }

    public boolean checkIfTeamValid(IValidation validation) throws Exception {
        validation.isStringEmpty(teamName, "Team name");
        this.headCoach.checkIfCoachValid(validation);
        validation.isStringEmpty(generalManager.getGeneralManagerName(), "General manager name");
        checkIfOneCaptainPerTeam(players);
        if (this.checkIfSizeOfTeamValid(players) == false) {
            throw new Exception("Each team must have 30 players");
        }
        return true;
    }

    public double calculateTeamStrength() {
        double teamStrength = 0;
        for (IPlayer player : getActiveRoster()) {
            teamStrength = teamStrength + player.getPlayerStrength();
        }
        return teamStrength;
    }

    public void sortPlayersInTeamByStrength(List<IPlayer> playersList) {
        playersList.sort((player1, player2) -> Double.compare(player2.getPlayerStrength(), player1.getPlayerStrength()));
    }

    public List<IPlayer> filterPlayersInTeam(String position,List<IPlayer> teamPlayers){
        return teamPlayers.stream().filter((IPlayer player) -> player.getPosition().equals(position)).collect(Collectors.toList());
    }
}
