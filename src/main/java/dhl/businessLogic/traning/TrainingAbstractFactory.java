package dhl.businessLogic.traning;

import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.businessLogic.traning.Interfaces.ITraining;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.aging.interfaceAging.IInjury;
import dhl.businessLogic.leagueModel.interfaceModel.IConference;
import dhl.businessLogic.leagueModel.interfaceModel.IDivision;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayerStatistics;
import dhl.businessLogic.leagueModel.interfaceModel.ICoach;

import java.util.List;

class TrainingAbstractFactory implements TrainingFactory {

    private IInjury injurySystem;
    public IGameConfig gameConfig;

    public ILeagueObjectModel updatePlayerStats(ILeagueObjectModel leagueObjectModel) throws Exception {
        for (IConference conference : leagueObjectModel.getConferences()) {
            for (IDivision division : conference.getDivisions()) {
                for (ITeam team : division.getTeams()) {

                    Double[] randomValues = {getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue()};
                    playerStatLessThanHeadCoachStat(team.getPlayers(), team.getHeadCoach(), randomValues);

                    playerStatMoreThanHeadCoachStat(team.getPlayers(), team.getHeadCoach(), randomValues, team);
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

    public void playerStatMoreThanHeadCoachStat(List<IPlayer> arrPlayer, ICoach objCoach
            , Double[] randomValues, ITeam team) {
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
