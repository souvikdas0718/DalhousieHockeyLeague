package dhl.Training;

import dhl.leagueModel.PlayerStatistics;
import dhl.leagueModel.interfaceModel.*;

import java.util.ArrayList;
import java.util.Random;

public class Training implements ITraining {
    public ILeagueObjectModel statIncrease(ILeagueObjectModel objLeagueObjectModel) throws Exception {
        ArrayList<IConference> arrConference = new ArrayList<>();
        ArrayList<IDivision> arrDivision = new ArrayList<>();
        ArrayList<ITeam> arrTeam = new ArrayList<>();
        ArrayList<IPlayer> arrPlayer = new ArrayList<>();

        arrConference = objLeagueObjectModel.getConferences();
        for (IConference conference : objLeagueObjectModel.getConferences()) {
            for (IDivision division : conference.getDivisions()) {
                for (ITeam team : division.getTeams()) {
                    String[] randomValues = {getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue()};
                    arrPlayer = updatePlayerStats(team.getPlayers(), team.getHeadCoach(), randomValues);
                    team.setPlayers(arrPlayer);
                }
                division.setTeams(arrTeam);
            }
            conference.setDivisions(arrDivision);
        }
        return objLeagueObjectModel;
    }

    public ArrayList<IPlayer> updatePlayerStats(ArrayList<IPlayer> arrPlayer, ICoach objCoach, String[] randomValues) throws Exception
    {
        //TODO: refactor
        for (int l=0; l<arrPlayer.size(); l++) {
            int skatingValue = 0;
            int shootingValue = 0;
            int checkingValue = 0;
            int savingValue = 0;
            int age = arrPlayer.get(l).getPlayerStats().getAge();

            if (Double.parseDouble(randomValues[0]) < objCoach.getSkating()) {
                skatingValue = (arrPlayer.get(l).getPlayerStats().getSkating() + 1);
            }
            if (Double.parseDouble(randomValues[1]) < objCoach.getShooting()) {
                shootingValue = (arrPlayer.get(l).getPlayerStats().getShooting() + 1);
            }
            if (Double.parseDouble(randomValues[2]) < objCoach.getChecking()) {
                checkingValue = (arrPlayer.get(l).getPlayerStats().getChecking() + 1);
            }
            if (Double.parseDouble(randomValues[3]) < objCoach.getSaving()) {
                savingValue = (arrPlayer.get(l).getPlayerStats().getSaving() + 1);
            }
            IPlayerStatistics objPlayerStats = new PlayerStatistics(age,skatingValue,shootingValue,checkingValue,savingValue);
            arrPlayer.get(l).setPlayerStats(objPlayerStats);
        }
        return arrPlayer;
    }
    public String getRandomValue(){
        final Random random = null;
        final int decimalPlaces = 1;
        final double dbl =
                ((random == null ? new Random() : random).nextDouble() //
                        * (1 - 0))
                        + 0;
        String val = String.format("%." + decimalPlaces + "f", dbl);
        return val;
    }
}
