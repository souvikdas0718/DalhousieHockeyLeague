package dhl.leagueModel.interfaceModel;

import java.util.List;

public interface ITeam {

    String getTeamName();

    String getGeneralManager();

    ICoach getHeadCoach();

    List<IPlayer> getPlayers();

    int getLossPoint();

    void setLossPoint(int lossPoint);

    int getTeamPoint();

    void setTeamPoint(int teamPoint);

    boolean checkIfTeamValid(IValidation validation) throws Exception;

    double calculateTeamStrength();

}



