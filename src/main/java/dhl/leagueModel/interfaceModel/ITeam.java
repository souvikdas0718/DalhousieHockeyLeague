package dhl.leagueModel.interfaceModel;

import java.util.ArrayList;
import java.util.List;

public interface ITeam {
    public void setDefault();
    public String getTeamName();
    public void setTeamName(String teamName);
    public String getGeneralManager();
    public void setGeneralManager(String generalManager);
    public String getHeadCoach();
    public void setHeadCoach(String headCoach);
    public String getDivisionName();
    public void setDivisionName(String divisionName);
    public String getConferenceName();
    public void setConferenceName(String conferenceName);
    public void checkIfOneCaptainPerTeam(List<IPlayer> playerList) throws Exception;
    public boolean checkIfSizeOfTeamValid(List<IPlayer> playerList);
    public boolean checkIfTeamValid(IParserOutput parserOutput,IValidation validation) throws Exception;
}



