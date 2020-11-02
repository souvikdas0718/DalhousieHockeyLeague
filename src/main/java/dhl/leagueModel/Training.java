package dhl.leagueModel;

import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.leagueModel.interfaceModel.ITraining;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.IInjurySystem;
import dhl.leagueModel.interfaceModel.IConference;
import dhl.leagueModel.interfaceModel.IDivision;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.IPlayerStatistics;
import dhl.leagueModel.interfaceModel.ICoach;

import java.util.List;

public class Training implements ITraining {

    private IInjurySystem injurySystem;
    public IGameConfig gameConfig;

    public Training(IInjurySystem injurySystem, IGameConfig gameConfig) {
        this.injurySystem = injurySystem;
        this.gameConfig = gameConfig;
    }

    public ILeagueObjectModel updatePlayerStats(ILeagueObjectModel leagueObjectModel) throws Exception {
        for (IConference conference : leagueObjectModel.getConferences()) {
            for (IDivision division : conference.getDivisions()) {
                for (ITeam team : division.getTeams()) {

                    Double[] randomValues = {getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue()};
                    playerStatLessThanHeadCoachStat(team.getPlayers(), team.getHeadCoach(), randomValues);

                    playerStatMoreThanHeadCoachStat(team.getPlayers(), team.getHeadCoach(), randomValues);
                }
            }
        }
        return leagueObjectModel;
    }

    public List<IPlayer> playerStatLessThanHeadCoachStat(List<IPlayer> arrPlayer, ICoach objCoach, Double[] randomValues) throws Exception {
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

    public void playerStatMoreThanHeadCoachStat(List<IPlayer> arrPlayer, ICoach objCoach, Double[] randomValues) {
        for (IPlayer player : arrPlayer) {
            if ((randomValues[0] > objCoach.getSkating()) ||
                    (randomValues[1] > objCoach.getShooting()) ||
                    (randomValues[2] > objCoach.getChecking()) ||
                    (randomValues[3] > objCoach.getSaving())) {
                injurySystem.checkIfPlayerInjured(gameConfig, player);
            }
        }
    }

    public Double getRandomValue() {
        Double randomValue = Math.random();
        return randomValue;
    }
}
