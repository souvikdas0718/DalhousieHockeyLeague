package dhl.inputOutput.importJson.interfaces;

import dhl.businessLogic.leagueModel.interfaceModel.*;

import java.util.List;

public interface ICreateLeagueObjectModel {

    ILeagueObjectModel getLeagueObjectModel() throws Exception;

    List<IConference> getConferenceArrayList() throws Exception;

    List<IDivision> getDivisionObjectArrayList() throws Exception;

    List<ITeam> getTeamObjectArrayList() throws Exception;

    List<IPlayer> getPlayerArrayList() throws Exception;
}
