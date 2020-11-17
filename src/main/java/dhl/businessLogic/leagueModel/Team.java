package dhl.businessLogic.leagueModel;

import dhl.businessLogic.leagueModel.constants.PlayerPosition;
import dhl.businessLogic.leagueModel.constants.TeamConstant;
import dhl.businessLogic.leagueModel.interfaceModel.ICoach;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.leagueModel.interfaceModel.IValidation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Team implements ITeam {
    private String teamName;
    private String generalManager;
    private ICoach headCoach;
    private int lossPoint;
    private int teamPoint;
    private List<IPlayer> players;

    public Team() {
        setDefault();
    }

    public void setDefault() {
        teamName = "";
        generalManager = "";
        headCoach = new Coach();
        players = new ArrayList<>();
    }

    public Team(String teamName, String generalManager, ICoach headCoach, List<IPlayer> playersList) {
        setDefault();
        this.teamName = teamName;
        this.generalManager = generalManager;
        this.headCoach = headCoach;
        this.players = playersList;
        if(this.players.size()== TeamConstant.TOTALTEAMSIZE.getValue()){
            this.setRoster();
        }
    }

    public String getTeamName() {
        return teamName;
    }

    public String getGeneralManager() {
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
        addPlayersToTeamRoster(PlayerPosition.FORWARD.toString(),TeamConstant.NOOFFORWARDS.getValue());
        addPlayersToTeamRoster(PlayerPosition.DEFENSE.toString(),TeamConstant.NOOFDEFENCE.getValue());
        addPlayersToTeamRoster(PlayerPosition.GOALIE.toString(),TeamConstant.NOOFGOALIES.getValue());
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
        return playerList.size() == TeamConstant.TOTALTEAMSIZE.getValue();
    }

    public boolean checkIfSkatersGoaliesValid() {
        List<IPlayer> activeRoster = getActiveRoster();
        List<IPlayer> goaliesInActiveRoster = filterPlayersInTeam(PlayerPosition.GOALIE.toString(), activeRoster);
        List<IPlayer> forwardsInActiveRoster = filterPlayersInTeam(PlayerPosition.FORWARD.toString(), activeRoster);
        List<IPlayer> defenseInActiveRoster = filterPlayersInTeam(PlayerPosition.DEFENSE.toString(), activeRoster);

        List<IPlayer> goaliesInTeam = filterPlayersInTeam(PlayerPosition.GOALIE.toString(), players);
        List<IPlayer> forwardsInTeam = filterPlayersInTeam(PlayerPosition.FORWARD.toString(), players);
        List<IPlayer> defenseInTeam = filterPlayersInTeam(PlayerPosition.DEFENSE.toString(), players);

        if(goaliesInTeam.size()==TeamConstant.TOTALGOALIES.getValue() && forwardsInTeam.size()==TeamConstant.TOTALFORWARDS.getValue() && defenseInTeam.size()==TeamConstant.TOTALDEFENSE.getValue()
         && goaliesInActiveRoster.size()==TeamConstant.NOOFGOALIES.getValue() && (forwardsInActiveRoster.size()+defenseInActiveRoster.size()==TeamConstant.NOOFSKATERS.getValue())){
            return true;
        }
        return false;
    }

    public boolean checkIfTeamValid(IValidation validation) throws Exception {
        validation.isStringEmpty(teamName, "Team name");
        this.headCoach.checkIfCoachValid(validation);
        validation.isStringEmpty(generalManager, "General manager name");
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
