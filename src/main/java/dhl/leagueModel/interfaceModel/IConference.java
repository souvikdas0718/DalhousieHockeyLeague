package dhl.leagueModel.interfaceModel;

import java.util.ArrayList;

public interface IConference {

    public String getConferenceName();

    public void setConferenceName(String conferenceName);

    public ArrayList<IDivision> getDivisions();

    public void setDivisions(ArrayList<IDivision> divisionList);

    public boolean checkIfConferenceValid(IValidation validation) throws Exception;

    public void checkIfConferenceHasEvenDivisions() throws Exception;
}
