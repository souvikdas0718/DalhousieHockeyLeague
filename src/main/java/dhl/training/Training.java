package dhl.Training;

import dhl.leagueModel.PlayerStatistics;
import dhl.leagueModel.interfaceModel.*;

import java.util.ArrayList;
import java.util.Random;

public class Training implements ITraining {
    public ILeagueObjectModel statIncrease(ILeagueObjectModel objLeagueObjectModel) throws Exception{
        ArrayList<IConference> arrConference = new ArrayList<>();
        ArrayList<IDivision> arrDivision = new ArrayList<>();
        ArrayList<ITeam> arrTeam = new ArrayList<>();
        ArrayList<IPlayer> arrPlayer = new ArrayList<>();

        arrConference = objLeagueObjectModel.getConferences();
        for(int i=0; i<arrConference.size(); i++)
        {
            arrDivision = arrConference.get(i).getDivisions();
            for (int j = 0; j<arrDivision.size(); j++)
            {
                arrTeam = arrDivision.get(j).getTeams();
                for(int k=0; k<arrTeam.size(); k++)
                {
                    String[] randomValues = {getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue()};
                    arrPlayer = updatePlayerStats(arrTeam.get(k).getPlayers(),arrTeam.get(k).getHeadCoach(), randomValues);
                    arrTeam.get(k).setPlayers(arrPlayer);
                }
                arrDivision.get(j).setTeams(arrTeam);
            }
            arrConference.get(i).setDivisions(arrDivision);
        }

        return objLeagueObjectModel;
    }

    public ArrayList<IPlayer> updatePlayerStats(ArrayList<IPlayer> arrPlayer, ICoach objCoach, String[] randomValues) throws Exception
    {
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
                shootingValue = (arrPlayer.get(l).getPlayerStats().getSkating() + 1);
            }
            if (Double.parseDouble(randomValues[2]) < objCoach.getChecking()) {
                checkingValue = (arrPlayer.get(l).getPlayerStats().getSkating() + 1);
            }
            if (Double.parseDouble(randomValues[3]) < objCoach.getSaving()) {
                savingValue = (arrPlayer.get(l).getPlayerStats().getSkating() + 1);
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
