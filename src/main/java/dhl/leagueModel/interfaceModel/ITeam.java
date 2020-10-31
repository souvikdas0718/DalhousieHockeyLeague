package dhl.leagueModel.interfaceModel;

import dhl.InputOutput.importJson.Interface.IGameConfig;

import java.util.ArrayList;
import java.util.Date;
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

    boolean checkIfSkatersGoaliesValid() ;

    double calculateTeamStrength();



}



