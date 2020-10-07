package dhl.leagueModelData;

import java.util.ArrayList;
import java.util.HashMap;
import dhl.leagueModel.*;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;

public interface ILeagueObjectModelData {

    public void insertLeagueModel(ILeagueObjectModel conferences);

    public boolean checkIfLeagueAlreadyExists(String leagueName);

    public boolean checkIfTeamAlreadyExists(String teamName, String divisionName);

    public ILeagueObjectModel loadLeagueModel(String leaguename);
}
