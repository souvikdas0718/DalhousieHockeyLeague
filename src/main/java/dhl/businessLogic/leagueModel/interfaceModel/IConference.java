package dhl.businessLogic.leagueModel.interfaceModel;

import java.util.List;

public interface IConference {

    String getConferenceName();

    List<IDivision> getDivisions();

    boolean checkIfConferenceHasUniqueDivisions();

    boolean checkIfConferenceHasEvenDivisions();

}
