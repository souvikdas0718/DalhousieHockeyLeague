package dhl.businessLogic.aging;

import dhl.businessLogic.aging.interfaceAging.IAging;
import dhl.businessLogic.aging.interfaceAging.IAgingSystem;
import dhl.businessLogic.aging.interfaceAging.IInjurySystem;
import dhl.businessLogic.aging.interfaceAging.IRetirementSystem;
import dhl.database.interfaceDB.IPlayerDB;
import dhl.businessLogic.leagueModel.interfaceModel.*;

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

    public Aging(IAgingSystem agingSystem, IRetirementSystem retirementSystem, IInjurySystem injurySystem, ILeagueObjectModel leagueObjectModel, int noOfDays, IPlayerDB playerDB) {
        this.leagueObjectModel = leagueObjectModel;
        this.noOfDays = noOfDays;
        this.playerDB = playerDB;
        this.agingSystem = agingSystem;
        this.retirementSystem = retirementSystem;
        this.injurySystem = injurySystem;
    }

    public ILeagueObjectModel initiateAging() throws Exception {
        int noOfDaysInAYear = AgingConstant.NOOFDAYSINYEAR.getValue();
        if ((noOfDays % noOfDaysInAYear) == 0) {
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
