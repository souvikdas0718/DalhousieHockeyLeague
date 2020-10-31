package dhl.leagueModel;

import dhl.database.interfaceDB.IPlayerDB;
import dhl.leagueModel.interfaceModel.*;

import java.util.List;
import java.util.Map;

public class Aging implements IAging {
    ILeagueObjectModel leagueObjectModel;
    IAgingSystem agingSystem;
    IRetirementSystem retirementSystem;
    IInjurySystem injurySystem;
    IPlayerDB playerDB;
    Map<String, List<IPlayer>> retiringPlayers;
    List<IPlayer> retiringFreeAgents;
    int noOfDays;

    public Aging(IAgingSystem agingSystem,IRetirementSystem retirementSystem,IInjurySystem injurySystem, ILeagueObjectModel leagueObjectModel, int noOfDays, IPlayerDB playerDB){
        this.leagueObjectModel=leagueObjectModel;
        this.noOfDays=noOfDays;
        this.playerDB=playerDB;
        this.agingSystem = agingSystem;
        this.retirementSystem=retirementSystem;
        this.injurySystem=injurySystem;
    }

    public ILeagueObjectModel initiateAging() throws Exception{
        if(noOfDays % 365==0){
            for(IConference conference : leagueObjectModel.getConferences()){
                for (IDivision division : conference.getDivisions()){
                    for(ITeam team : division.getTeams()){
                        agingSystem.ageAllPlayers(team.getPlayers());
                        retiringPlayers=agingSystem.selectPlayersToRetire(team);
                    }
                }
            }
            agingSystem.ageAllPlayers(leagueObjectModel.getFreeAgents());
            retiringFreeAgents=agingSystem.selectFreeAgentsToRetire(leagueObjectModel.getFreeAgents());
            initiateRetirementForAgedPlayers();
        }
        checkPlayerInjuryRecovery();
        return leagueObjectModel;
    }

    public void initiateRetirementForAgedPlayers() throws Exception{
        retirementSystem.initiateRetirement( retiringPlayers,  retiringFreeAgents) ;
    }

    public void checkPlayerInjuryRecovery(){
        for(IConference conference : leagueObjectModel.getConferences()){
            for (IDivision division : conference.getDivisions()){
                for(ITeam team : division.getTeams()){
                    for(IPlayer player : team.getPlayers()){
                        injurySystem.healInjuredPlayers(player);
                    }
                }
            }
        }
    }
}
