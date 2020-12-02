package dhl.businessLogic.traning;

import dhl.businessLogic.aging.interfaceAging.IInjury;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Training implements ITraining {
    private static final Logger logger = LogManager.getLogger(Training.class);
    private IInjury injurySystem;
    public IGameConfig gameConfig;

    public Training(IInjury injurySystem, IGameConfig gameConfig) {
        this.injurySystem = injurySystem;
        this.gameConfig = gameConfig;
    }

    public ILeagueObjectModel updatePlayerStats(ILeagueObjectModel leagueObjectModel) throws Exception {
        for (IConference conference : leagueObjectModel.getConferences()) {
            for (IDivision division : conference.getDivisions()) {
                for (ITeam team : division.getTeams()) {

                    Double[] randomValues = {getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue()};
                    playerStatLessThanHeadCoachStat(team.getPlayers(), team, randomValues);

                    playerStatMoreThanHeadCoachStat(team.getPlayers(), team, randomValues);
                    logger.debug("Training: Updated player Stats");
                }
            }
        }
        return leagueObjectModel;
    }

    public List<IPlayer> playerStatLessThanHeadCoachStat(List<IPlayer> arrPlayer, ITeam team, Double[] randomValues) throws Exception {
        ICoach objCoach = team.getHeadCoach();
        for (IPlayer player : arrPlayer) {
            IPlayerStatistics playerStat = player.getPlayerStats();
            if (randomValues[0] < objCoach.getSkating()) {
                playerStat.setSkating(playerStat.getSkating() + 1);
            }
            if (randomValues[1] < objCoach.getShooting()) {
                playerStat.setShooting(playerStat.getShooting() + 1);
            }
            if (randomValues[2] < objCoach.getChecking()) {
                playerStat.setChecking(playerStat.getChecking() + 1);
            }
            if (randomValues[3] < objCoach.getSaving()) {
                playerStat.setSaving(playerStat.getSaving() + 1);
            }
        }
        return arrPlayer;
    }

    public void playerStatMoreThanHeadCoachStat(List<IPlayer> arrPlayer, ITeam team, Double[] randomValues) {
        ICoach objCoach = team.getHeadCoach();
        for (IPlayer player : arrPlayer) {
            if ((randomValues[0] > objCoach.getSkating()) ||
                    (randomValues[1] > objCoach.getShooting()) ||
                    (randomValues[2] > objCoach.getChecking()) ||
                    (randomValues[3] > objCoach.getSaving())) {
                injurySystem.checkIfPlayerInjured(gameConfig, player, team);
            }
        }
    }

    public Double getRandomValue() {
        Double randomValue = Math.random();
        return randomValue;
    }
}
