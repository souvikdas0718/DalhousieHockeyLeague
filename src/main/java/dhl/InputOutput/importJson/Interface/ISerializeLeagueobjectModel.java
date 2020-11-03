package dhl.InputOutput.importJson.Interface;

import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;

public interface ISerializeLeagueobjectModel {
    public String serializeLeagueObjectModel(ILeagueObjectModel objLeagueObjectModel) throws Exception;

    public void WriteSerializedLeagueObjectToFile(ILeagueObjectModel objLeagueObjectModel) throws Exception;
}
