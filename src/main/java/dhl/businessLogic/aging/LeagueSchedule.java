package dhl.businessLogic.aging;

import dhl.businessLogic.aging.interfaceAging.IAging;
import dhl.businessLogic.aging.interfaceAging.IInjury;
import dhl.businessLogic.aging.interfaceAging.ILeagueSchedule;
import dhl.businessLogic.aging.interfaceAging.IRetirement;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeagueSchedule implements ILeagueSchedule {
    private static final int NOOFDAYSINAYEAR = 365;
    private static final Logger logger = LogManager.getLogger(LeagueSchedule.class);
    ILeagueObjectModel leagueObjectModel;
    IAging aging;
    IRetirement retirement;
    IInjury injury;
    Map<String, List<IPlayer>> retiringPlayers;
    List<IPlayer> retiringFreeAgents;

    public LeagueSchedule(IAging aging, IRetirement retirement, IInjury injury, ILeagueObjectModel leagueObjectModel) {
        logger.info("League Schedule Constructor Object created");
        logger.debug("Creating a League Schedule Constructor");
        this.leagueObjectModel = leagueObjectModel;
        this.aging = aging;
        this.retirement = retirement;
        this.injury = injury;
        retiringPlayers = new HashMap<>();
        retiringFreeAgents = new ArrayList<>();
    }

    public ILeagueObjectModel initiateAging(int noOfDays, LocalDate currentDate) throws IOException, ParseException {
        logger.info("Age player by 1 day");
        logger.debug("Initiate Aging players by 1 day");
        for (IConference conference : leagueObjectModel.getConferences()) {
            for (IDivision division : conference.getDivisions()) {
                for (ITeam team : division.getTeams()) {
                    logger.debug("Checking if current date is birthday of player");
                    aging.agePlayers(team.getPlayers(), currentDate);
                    logger.info("Checking if End of season to initiate retirement for players");
                    if ((noOfDays % NOOFDAYSINAYEAR) == 0) {
                        logger.debug("Selecting players to retire in team" + team);
                        retiringPlayers = aging.selectPlayersToRetire(team, retiringPlayers);
                    }
                }
            }
        }
        aging.agePlayers(leagueObjectModel.getFreeAgents(), currentDate);
        logger.info("Checking if End of season to initiate retirement for free agents");
        if ((noOfDays % NOOFDAYSINAYEAR) == 0) {
            logger.info("Selecting free agents to retire");
            retiringFreeAgents = aging.selectFreeAgentsToRetire(leagueObjectModel.getFreeAgents());
            initiateRetirementForAgedPlayers();
        }

        checkPlayerInjuryRecovery();
        return leagueObjectModel;
    }

    public void initiateRetirementForAgedPlayers() throws IOException, ParseException {
        logger.info("Retire players in team at the end of simulation");
        logger.debug("Initiate Retirement");
        retirement.initiateRetirement(retiringPlayers, retiringFreeAgents, leagueObjectModel);
    }

    public void checkPlayerInjuryRecovery() {
        logger.debug("Healing Injury of players each day");
        for (IConference conference : leagueObjectModel.getConferences()) {
            for (IDivision division : conference.getDivisions()) {
                for (ITeam team : division.getTeams()) {
                    for (IPlayer player : team.getPlayers()) {
                        logger.debug("Healing Injury of player" + player.getPlayerName() + player.getPlayerInjuredDays());
                        injury.healInjuredPlayersInTeam(player, team);
                    }
                }
            }
        }
        for (IPlayer player : leagueObjectModel.getFreeAgents()) {
            logger.debug("Healing Injury of freeAgent" + player.getPlayerName() + player.getPlayerInjuredDays());
            injury.healInjuredPlayers(player);
        }
    }

}
