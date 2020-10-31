package dhl.Training;

import dhl.leagueModel.PlayerStatistics;
import dhl.leagueModel.interfaceModel.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Training implements ITraining {
    public ILeagueObjectModel statIncrease(ILeagueObjectModel objLeagueObjectModel) throws Exception{
        for(IConference conference :  objLeagueObjectModel.getConferences()) {
            for (IDivision division : conference.getDivisions()) {
                for (ITeam team : division.getTeams()) {
                    String[] randomValues = {getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue()};
                    updatePlayerStats(team.getPlayers(),team.getHeadCoach(), randomValues);
                }
            }
        }

        return objLeagueObjectModel;
    }

    public List<IPlayer> updatePlayerStats(List<IPlayer> arrPlayer, ICoach objCoach, String[] randomValues) throws Exception
    {
        //TODO: refactor
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
