package dhl.businessLogic.leagueModel.factory.interfaceFactory;

import dhl.businessLogic.leagueModel.interfaceModel.ICoach;

public interface ICoachBuilder {
    ICoach createCoach();
    void setName(String name);
    void setSkating(double skating);
    void setShooting(double shooting);
    void setChecking(double checking);
    void setSaving(double saving);
}
