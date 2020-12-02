package dhl.businessLogic.leagueModel.interfaceModel;

import java.util.List;

public interface ITeam {
    String getTeamName();

    IGeneralManager getGeneralManager();

    ICoach getHeadCoach();

    List<IPlayer> getPlayers();

    int getLossPoint();

    void setLossPoint(int lossPoint);

    int getTeamPoint();

    void setTeamPoint(int teamPoint);

    void setRoster();

    List<IPlayer> getActiveRoster();

    List<IPlayer> getInactiveRoster();

    boolean checkTeamPlayersCount();

    double calculateTeamStrength();

    void sortPlayersInTeamByStrength(List<IPlayer> playersList);

    List<IPlayer> filterPlayersInTeam(String position, List<IPlayer> teamPlayers);

    boolean checkIfOneCaptainPerTeam(List<IPlayer> playerList);

    boolean checkIfSizeOfTeamValid(List<IPlayer> playerList);

    int getTotalTeamSize();

    int getTotalGoalies();

    int getTotalForwards();

    int getTotalDefense();
}



