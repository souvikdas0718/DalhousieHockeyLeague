package dhl.businessLogic.leagueModel.interfaceModel;

import java.util.List;
import java.util.stream.Collectors;

public interface ITeam {

    String getTeamName();

    String getGeneralManager();

    ICoach getHeadCoach();

    List<IPlayer> getPlayers();

    int getLossPoint();

    void setLossPoint(int lossPoint);

    int getTeamPoint();

    void setTeamPoint(int teamPoint);

    void setRoster();

    List<IPlayer> getActiveRoster();

    List<IPlayer> getInactiveRoster();

    boolean checkIfTeamValid(IValidation validation) throws Exception;

    boolean checkIfSkatersGoaliesValid();

    double calculateTeamStrength();

    void sortPlayersInTeamByStrength(List<IPlayer> playersList);

    List<IPlayer> filterPlayersInTeam(String position,List<IPlayer> teamPlayers);


}



