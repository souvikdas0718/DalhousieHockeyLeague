package dhl.leagueModel.interfaceModel;

import java.util.List;

public interface IConference {

    String getConferenceName();

    List<IDivision> getDivisions();

    boolean checkIfConferenceValid(IValidation validation) throws Exception;

}
