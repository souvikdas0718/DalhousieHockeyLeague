package dhl.Training;

import dhl.database.PlayerDB;
import dhl.leagueModel.Coach;
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
                    arrPlayer = updatePlayerStats(arrTeam.get(k).getPlayers(),arrTeam.get(k).getHeadCoach());
                    arrTeam.get(k).setPlayers(arrPlayer);
                }
                arrDivision.get(j).setTeams(arrTeam);
            }
            arrConference.get(i).setDivisions(arrDivision);
        }

        return objLeagueObjectModel;
    }

    public ArrayList<IPlayer> updatePlayerStats(ArrayList<IPlayer> arrPlayer, ICoach objCoach) throws Exception
    {
        for (int l=0; l<arrPlayer.size(); l++) {
            int skatingValue = 0;
            int shootingValue = 0;
            int checkingValue = 0;
            int savingValue = 0;
            int age = arrPlayer.get(l).getPlayerStats().getAge();

            if (getRandomValue() < objCoach.getSkating()) {
                skatingValue = (arrPlayer.get(l).getPlayerStats().getSkating() + 1);
            }
            if (getRandomValue() < objCoach.getShooting()) {
                shootingValue = (arrPlayer.get(l).getPlayerStats().getSkating() + 1);
            }
            if (getRandomValue() < objCoach.getChecking()) {
                checkingValue = (arrPlayer.get(l).getPlayerStats().getSkating() + 1);
            }
            if (getRandomValue() < objCoach.getSaving()) {
                savingValue = (arrPlayer.get(l).getPlayerStats().getSkating() + 1);
            }
            IPlayerStatistics objPlayerStats = new PlayerStatistics(age,skatingValue,shootingValue,checkingValue,savingValue);
            arrPlayer.get(l).setPlayerStats(objPlayerStats);
        }
        return arrPlayer;
    }
    public Double getRandomValue(){
        final Random random = null;
        final int decimalPlaces = 1;
        final double dbl =
                ((random == null ? new Random() : random).nextDouble() //
                        * (1 - 0))
                        + 0;
        Double val = Double.valueOf(String.format("%." + decimalPlaces + "f", dbl));
        return val;
    }
}
