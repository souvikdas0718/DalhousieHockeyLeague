package dhl.leagueModel;

import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.leagueModel.interfaceModel.*;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Team implements ITeam {
    private int teamId;
    private String teamName;
    private String generalManager;
    private ICoach headCoach;
    private int lossPoint;
    private int teamPoint;
    private ArrayList<IPlayer> players;

    public Team(){
        setDefault();
    }

    public void setDefault(){
        teamId=-1;
        teamName="";
        generalManager="";
        headCoach=new Coach();
        players=new ArrayList<>();
    }

    public Team(String teamName,String generalManager,ICoach headCoach, ArrayList<IPlayer> playersList){
        setTeamName(teamName);
        setGeneralManager(generalManager);
        setHeadCoach(headCoach);
        setPlayers(playersList);
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName=teamName;
    }

    public String getGeneralManager() {
        return generalManager;
    }

    public void setGeneralManager(String generalManager) {
        this.generalManager=generalManager;
    }

    public ICoach getHeadCoach() {
        return headCoach;
    }

    public void setHeadCoach(ICoach headCoach) {
        this.headCoach=headCoach;
    }

    public ArrayList<IPlayer> getPlayers(){
        return players;
    }

    public void setPlayers(ArrayList<IPlayer> playersList){
        this.players=playersList;
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

    public void checkTeamInjury(IGameConfig  gameConfig, Date currentDate){
        for(IPlayer player: players){
            player.checkPlayerInjury(gameConfig,currentDate);
        }
    }

    public double calculateTeamStrength(){
        double teamStrength=0;
        for(IPlayer player:players){
            teamStrength=teamStrength+player.getPlayerStrength();
        }
        return teamStrength;
    }

    public boolean checkIfSkatersGoaliesValid(ArrayList<IFreeAgent> freeAgents) throws Exception{
        Integer totalSkaters = 0;
        Integer totalGoalies = 0;

        for(int i=0; i<freeAgents.size(); i++){
            if (freeAgents.get(i).getPosition().equals("forward") || freeAgents.get(i).getPosition().equals("defense")){
                totalSkaters = totalSkaters + 1;
            }
            if (freeAgents.get(i).getPosition().equals("goalie")){
                totalGoalies = totalGoalies + 1;
            }
        }
        if (totalGoalies<2 || totalSkaters<18)
        {
            return false;
        }

        return true;
    }


}
