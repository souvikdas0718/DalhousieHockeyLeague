package dhl.leagueModel;

import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.leagueModel.interfaceModel.ITraining;
import dhl.leagueModel.interfaceModel.*;
import java.util.List;
import java.util.Random;

public class Training implements ITraining {

    private IInjurySystem injurySystem;
    private IGameConfig gameConfig;

    public Training(IInjurySystem injurySystem)
    {
        this.injurySystem=injurySystem;
    }

    public ILeagueObjectModel updatePlayerStats(ILeagueObjectModel leagueObjectModel) throws Exception{
        for(IConference conference :  leagueObjectModel.getConferences()) {
            for (IDivision division : conference.getDivisions()) {
                for (ITeam team : division.getTeams()) {

                    String[] randomValues = {getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue()};
                    playerStatLessThanHeadCoachStat(team.getPlayers(),team.getHeadCoach(), randomValues);

                    gameConfig = leagueObjectModel.getGameConfig();
                    playerStatMoreThanHeadCoachStat(team.getPlayers(),team.getHeadCoach(), randomValues);
                }
            }
        }
        return leagueObjectModel;
    }

    public List<IPlayer> playerStatLessThanHeadCoachStat(List<IPlayer> arrPlayer, ICoach objCoach, String[] randomValues) throws Exception
    {
        for (IPlayer player : arrPlayer) {
            IPlayerStatistics playerStat= player.getPlayerStats();
            if (Double.parseDouble(randomValues[0]) < objCoach.getSkating()) {
                playerStat.setSkating(playerStat.getSkating()+1);
            }
            if (Double.parseDouble(randomValues[1]) < objCoach.getShooting()) {
                playerStat.setShooting(playerStat.getShooting()+1);
            }
            if (Double.parseDouble(randomValues[2]) < objCoach.getChecking()) {
                playerStat.setChecking(playerStat.getChecking()+1);
            }
            if (Double.parseDouble(randomValues[3]) < objCoach.getSaving()) {
                playerStat.setSaving(playerStat.getSaving()+1);
            }
        }
        return arrPlayer;
    }

    public void playerStatMoreThanHeadCoachStat(List<IPlayer> arrPlayer, ICoach objCoach, String[] randomValues){
        for (IPlayer player : arrPlayer) {
            if ((Double.parseDouble(randomValues[0]) > objCoach.getSkating()) ||
                    (Double.parseDouble(randomValues[1]) > objCoach.getShooting()) ||
                        (Double.parseDouble(randomValues[2]) > objCoach.getChecking()) ||
                            (Double.parseDouble(randomValues[3]) > objCoach.getSaving())){
                injurySystem.checkIfPlayerInjured(gameConfig,player);
            }
        }
    }

    public String getRandomValue(){
        Random random = null;
        int decimalPlaces = 1;
        double dbl =
                ((random == null ? new Random() : random).nextDouble() //
                        * (1 - 0))
                        + 0;
        String val = String.format("%." + decimalPlaces + "f", dbl);
        return val;
    }
}
