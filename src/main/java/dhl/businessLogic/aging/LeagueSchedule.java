package dhl.businessLogic.aging;

import dhl.businessLogic.aging.interfaceAging.ILeagueSchedule;
import dhl.businessLogic.aging.interfaceAging.IAging;
import dhl.businessLogic.aging.interfaceAging.IInjury;
import dhl.businessLogic.aging.interfaceAging.IRetirement;
import dhl.businessLogic.leagueModel.interfaceModel.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeagueSchedule implements ILeagueSchedule {
    private static final int NOOFDAYSINAYEAR=365;
    ILeagueObjectModel leagueObjectModel;
    IAging aging;
    IRetirement retirement;
    IInjury injury;
    Map<String, List<IPlayer>> retiringPlayers;
    List<IPlayer> retiringFreeAgents;
    int noOfDays;

    public LeagueSchedule(IAging aging, IRetirement retirement, IInjury injury, ILeagueObjectModel leagueObjectModel, int noOfDays) {
        this.leagueObjectModel = leagueObjectModel;
        this.noOfDays = noOfDays;
        this.aging = aging;
        this.retirement = retirement;
        this.injury= injury;
        retiringPlayers = new HashMap<>();
        retiringFreeAgents = new ArrayList<>();
    }

    public ILeagueObjectModel initiateAging() throws Exception {
        if ((noOfDays % NOOFDAYSINAYEAR) == 0) {
            for (IConference conference : leagueObjectModel.getConferences()) {
                for (IDivision division : conference.getDivisions()) {
                    for (ITeam team : division.getTeams()) {
                        aging.ageAllPlayers(team.getPlayers());
                        retiringPlayers = aging.selectPlayersToRetire(team);
                    }
                }
            }
            aging.ageAllPlayers(leagueObjectModel.getFreeAgents());
            retiringFreeAgents = aging.selectFreeAgentsToRetire(leagueObjectModel.getFreeAgents());
            initiateRetirementForAgedPlayers();
        }
        checkPlayerInjuryRecovery();
        return leagueObjectModel;
    }

    public void initiateRetirementForAgedPlayers() throws Exception {
        retirement.initiateRetirement(retiringPlayers, retiringFreeAgents);
    }

    public void checkPlayerInjuryRecovery() {
        for (IConference conference : leagueObjectModel.getConferences()) {
            for (IDivision division : conference.getDivisions()) {
                for (ITeam team : division.getTeams()) {
                    for (IPlayer player : team.getPlayers()) {
                        injury.healInjuredPlayersInTeam(player,team);
                    }
                }
            }
        }
        for (IPlayer player : leagueObjectModel.getFreeAgents()) {
            injury.healInjuredPlayers(player);
        }
    }
}
