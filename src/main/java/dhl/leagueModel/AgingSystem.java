package dhl.leagueModel;

import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.leagueModel.interfaceModel.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class AgingSystem {
    private int averageRetirementAge;
    private int maximumAge;
    private double likelihoodForGreaterThanAvg;
    private double likelihoodForLesserThanAvg;

    public AgingSystem(IGameConfig gameConfig){
        HashMap agingConfig=gameConfig.getHashMap("aging");
        averageRetirementAge=(int)(long)agingConfig.get("averageRetirementAge");
        maximumAge=(int)(long)agingConfig.get("maximumAge");
        likelihoodForGreaterThanAvg=80.0;
        likelihoodForLesserThanAvg=20.0;
    }

    public double getLikelihoodForGreaterThanAvg() {
        return likelihoodForGreaterThanAvg;
    }

    public void setLikelihoodForGreaterThanAvg(double likelihoodForGreaterThanAvg) {
        this.likelihoodForGreaterThanAvg = likelihoodForGreaterThanAvg;
    }

    public double getLikelihoodForLesserThanAvg() {
        return likelihoodForLesserThanAvg;
    }

    public void setLikelihoodForLesserThanAvg(double likelihoodForLesserThanAvg) {
        this.likelihoodForLesserThanAvg = likelihoodForLesserThanAvg;
    }

    public int getAverageRetirementAge() {
        return averageRetirementAge;
    }

    public void setAverageRetirementAge(int averageRetirementAge) {
        this.averageRetirementAge = averageRetirementAge;
    }

    public int getMaximumAge() {
        return maximumAge;
    }

    public void setMaximumAge(int maximumAge) {
        this.maximumAge = maximumAge;
    }

    public void ageAllPlayers(ILeagueObjectModel leagueObjectModel, int noOfDays){
        if(noOfDays==365){
            for(IConference conference : leagueObjectModel.getConferences()){
                for (IDivision division : conference.getDivisions()){
                    for(ITeam team :division.getTeams()){
                        for(IPlayer player:team.getPlayers()){
                            agePlayer(player.getPlayerStats());
                        }
                    }
                }
            }
            for(IFreeAgent freeAgent:leagueObjectModel.getFreeAgents()){
                agePlayer(freeAgent.getPlayerStats());
            }
        }
    }

    public void agePlayer(IPlayerStatistics playerStatistics){
        playerStatistics.setAge(playerStatistics.getAge()+1);

    }

    public Map<String,List<IPlayer>> selectPlayersToRetire(List<ITeam> teams){
        Map<String,List<IPlayer>> playersSelectedToRetire=new HashMap<>();
        int rangeOfAge=(maximumAge-averageRetirementAge)/3;
        for(ITeam team:teams){
            List retiringPlayers = new ArrayList();
            for(IPlayer player:team.getPlayers()){
                IPlayerStatistics playerStatistics=player.getPlayerStats();
                if(playerStatistics.getAge()==maximumAge){
                    retiringPlayers.add(player.getPlayerName());
                }
                else if(playerStatistics.getAge()>=averageRetirementAge){
                    if(checkLikelihoodOfRetirement(getLikelihoodForGreaterThanAvg())) {
                        retiringPlayers.add(player.getPlayerName());
                    }
                }
                else if(playerStatistics.getAge()<averageRetirementAge && playerStatistics.getAge()>averageRetirementAge-rangeOfAge){
                    if(checkLikelihoodOfRetirement(getLikelihoodForLesserThanAvg())) {
                        retiringPlayers.add(player.getPlayerName());
                    }
                }
            }
            playersSelectedToRetire.put(team.getTeamName(),retiringPlayers);
        }
        return playersSelectedToRetire;
    }

    public Map<String,List<IFreeAgent>> selectFreeAgentsToRetire(ILeagueObjectModel leagueObjectModel){
        Map<String,List<IFreeAgent>> agentsSelectedToRetire=new HashMap<>();
        List<IFreeAgent> retiringFreeAgents= new ArrayList<>();
        int rangeOfAge=(maximumAge-averageRetirementAge)/3;
        for(IFreeAgent freeAgent:leagueObjectModel.getFreeAgents()){
            IPlayerStatistics playerStatistics=freeAgent.getPlayerStats();
            if(playerStatistics.getAge()==maximumAge){
                retiringFreeAgents.add(freeAgent);
            }
            else if(playerStatistics.getAge()>=averageRetirementAge){
                if(checkLikelihoodOfRetirement(getLikelihoodForGreaterThanAvg())) {
                    retiringFreeAgents.add(freeAgent);
                }
            }
            else if(playerStatistics.getAge()<averageRetirementAge && playerStatistics.getAge()>averageRetirementAge-rangeOfAge){
                if(checkLikelihoodOfRetirement(getLikelihoodForLesserThanAvg())) {
                    retiringFreeAgents.add(freeAgent);
                }
            }
        }
        agentsSelectedToRetire.put(leagueObjectModel.getLeagueName(),retiringFreeAgents);
        return agentsSelectedToRetire;
    }

    public boolean checkLikelihoodOfRetirement(double likelihood){
        double ramdomNumber =  Math.random();
        ramdomNumber = ramdomNumber * 100;
        if(ramdomNumber <= likelihood) {
            return true;
        }
        return false;
    }





}
