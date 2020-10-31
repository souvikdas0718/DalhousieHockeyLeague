package dhl.importJson.Interface;

import dhl.leagueModel.interfaceModel.*;

import java.util.List;

public interface ICreateLeagueObjectModel {

    public ILeagueObjectModel getLeagueObjectModel() throws Exception;

    public List<IConference> getConferenceArrayList() throws Exception;

    public List<IDivision> getDivisionObjectArrayList() throws Exception;

    public List<ITeam> getTeamObjectArrayList() throws Exception;

    public List<IPlayer> getPlayerArrayList() throws Exception;
}
