package dhl.businessLogic.leagueModel.interfaceModel;

public interface IPlayerStatistics {
    public int getAge();

    public void setAge(int age);

    public int getSkating();

    public void setSkating(int skating);

    public int getShooting();

    public void setShooting(int shooting);

    public int getChecking();

    public void setChecking(int checking);

    public int getSaving();

    public void setSaving(int saving);

    public void checkPlayerStatistics() throws Exception;
}
