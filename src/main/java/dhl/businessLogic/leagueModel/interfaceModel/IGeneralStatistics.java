package dhl.businessLogic.leagueModel.interfaceModel;

public interface IGeneralStatistics {
    void setCoachName(String coachName);

    String getCoachName();

    int getAge();

    void setAge(int age);

    int getSkating();

    void setSkating(int skating);

    int getShooting();

    void setShooting(int shooting);

    int getChecking();

    void setChecking(int checking);

    int getSaving();

    void setSaving(int saving);
}