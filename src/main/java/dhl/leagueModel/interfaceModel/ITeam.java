package dhl.leagueModel.interfaceModel;

import dhl.importJson.Interface.IGameConfig;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface ITeam {

    public void setDefault();

    public String getTeamName();

    public void setTeamName(String teamName);

    public String getGeneralManager();

    public void setGeneralManager(String generalManager);

    public ICoach getHeadCoach();

    public void setHeadCoach(ICoach headCoach);

    public ArrayList<IPlayer> getPlayers();

    public void setPlayers(ArrayList<IPlayer> playersList);

    public int getLossPoint();

    public void setLossPoint(int lossPoint);

    public int getTeamPoint();

    public void setTeamPoint(int teamPoint);

    public void checkIfOneCaptainPerTeam(List<IPlayer> playerList) throws Exception;

    public boolean checkIfSizeOfTeamValid(List<IPlayer> playerList);

    public boolean checkIfTeamValid(IValidation validation) throws Exception;

    public ITeam checkTeamInjury(IGameConfig  gameConfig, Date currentDate);

    public double calculateTeamStrength();

}



