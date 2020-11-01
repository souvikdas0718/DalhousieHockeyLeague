package dhl.leagueModel;

import dhl.leagueModel.interfaceModel.*;

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

    public Team(){
        setDefault();
    }

    public void setDefault(){
        teamName="";
        generalManager="";
        headCoach=new Coach();
        players=new ArrayList<>();
    }

    public Team(String teamName,String generalManager,ICoach headCoach, List<IPlayer> playersList){
        this.teamName=teamName;
        this.generalManager=generalManager;
        this.headCoach=headCoach;
        this.players=playersList;
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

    public List<IPlayer> getPlayers(){
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

    public void checkIfOneCaptainPerTeam(List<IPlayer> playerList) throws Exception {
        Predicate<IPlayer> playerPredicate = player -> player.getCaptain() ;
        List<IPlayer> captainList=playerList.stream().filter(playerPredicate).collect(Collectors.toList());
        if(playerList!=null && playerList.size()>0  && captainList.size()==0){
            throw new Exception("Please select captain for the team");
        }
        if(captainList.size()>1){
            throw new Exception("There can be only one captain per team");
        }
    }

    public boolean checkIfSizeOfTeamValid(List<IPlayer> playerList) {
        return playerList.size() ==20 ;
    }

    public boolean checkIfSkatersGoaliesValid(){
        int totalSkaters = 0;
        int totalGoalies = 0;
        for(IPlayer player: players){
            String position=player.getPosition();
            if (position.equals("forward") || position.equals("defense")){
                totalSkaters = totalSkaters + 1;
            }
            if (position.equals("goalie")){
                totalGoalies = totalGoalies + 1;
            }
        }
        if (totalGoalies<2 || totalSkaters<18)
        {
            return false;
        }
        return true;
    }

    public boolean checkIfTeamValid(IValidation validation) throws Exception{
        validation.isStringEmpty(teamName,"Team name");
        this.headCoach.checkIfCoachValid(validation);
        validation.isStringEmpty(generalManager,"General manager name");
        checkIfOneCaptainPerTeam(players);
        if(this.checkIfSizeOfTeamValid(players)==false){
            throw new Exception("Each team must have 20 players");
        }
        return true;
    }

    public double calculateTeamStrength(){
        double teamStrength=0;
        for(IPlayer player:players){
            teamStrength=teamStrength+player.getPlayerStrength();
        }
        return teamStrength;
    }
}
