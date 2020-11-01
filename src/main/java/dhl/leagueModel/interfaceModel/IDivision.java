package dhl.leagueModel.interfaceModel;

import java.util.List;

public interface IDivision {

    String getDivisionName();

    List<ITeam> getTeams();

    boolean checkIfDivisionValid(IValidation validation) throws Exception;

}
