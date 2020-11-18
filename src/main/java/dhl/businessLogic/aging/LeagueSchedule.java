package dhl.businessLogic.aging;

import dhl.businessLogic.aging.interfaceAging.ILeagueSchedule;
import dhl.businessLogic.aging.interfaceAging.IAging;
import dhl.businessLogic.aging.interfaceAging.IInjury;
import dhl.businessLogic.aging.interfaceAging.IRetirement;
import dhl.database.interfaceDB.IPlayerDB;
import dhl.businessLogic.leagueModel.interfaceModel.*;

import java.util.List;
import java.util.Map;

public class LeagueSchedule implements ILeagueSchedule {
    private static final int NOOFDAYSINAYEAR=365;
    ILeagueObjectModel leagueObjectModel;
    IAging agingSystem;
    IRetirement retirementSystem;
    IInjury injurySystem;
    IPlayerDB playerDB;
    Map<String, List<IPlayer>> retiringPlayers;
    List<IPlayer> retiringFreeAgents;
    int noOfDays;

    public LeagueSchedule(IAging agingSystem, IRetirement retirementSystem, IInjury injurySystem, ILeagueObjectModel leagueObjectModel, int noOfDays, IPlayerDB playerDB) {
        this.leagueObjectModel = leagueObjectModel;
        this.noOfDays = noOfDays;
        this.playerDB = playerDB;
        this.agingSystem = agingSystem;
        this.retirementSystem = retirementSystem;
        this.injurySystem = injurySystem;
    }

    public ILeagueObjectModel initiateAging() throws Exception {
        if ((noOfDays % NOOFDAYSINAYEAR) == 0) {
            for (IConference conference : leagueObjectModel.getConferences()) {
                for (IDivision division : conference.getDivisions()) {
                    for (ITeam team : division.getTeams()) {
                        agingSystem.ageAllPlayers(team.getPlayers());
                        retiringPlayers = agingSystem.selectPlayersToRetire(team);
                    }
                }
            }
            agingSystem.ageAllPlayers(leagueObjectModel.getFreeAgents());
            retiringFreeAgents = agingSystem.selectFreeAgentsToRetire(leagueObjectModel.getFreeAgents());
            initiateRetirementForAgedPlayers();
        }
        checkPlayerInjuryRecovery();
        return leagueObjectModel;
    }

    public void initiateRetirementForAgedPlayers() throws Exception {
        retirementSystem.initiateRetirement(retiringPlayers, retiringFreeAgents);
    }

    public void checkPlayerInjuryRecovery() {
        for (IConference conference : leagueObjectModel.getConferences()) {
            for (IDivision division : conference.getDivisions()) {
                for (ITeam team : division.getTeams()) {
                    for (IPlayer player : team.getPlayers()) {
                        injurySystem.healInjuredPlayersInTeam(player,team);
                    }
                }
            }
        }
        for (IPlayer player : leagueObjectModel.getFreeAgents()) {
            injurySystem.healInjuredPlayers(player);
        }
    }
}
