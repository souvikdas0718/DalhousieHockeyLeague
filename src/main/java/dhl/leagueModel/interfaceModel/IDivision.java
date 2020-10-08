package dhl.leagueModel.interfaceModel;

import java.util.ArrayList;

public interface IDivision {

    public void setDivisionName(String divisionName);

    public String getDivisionName();

    public void setTeams(ArrayList<ITeam> teamsList);

    public ArrayList<ITeam> getTeams();

    public boolean checkIfDivisionValid(IValidation validation) throws Exception;

}
