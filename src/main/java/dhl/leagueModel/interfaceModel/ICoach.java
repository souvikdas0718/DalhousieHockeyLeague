package dhl.leagueModel.interfaceModel;

public interface ICoach {
    public String getCoachName();

    public void setCoachName(String coachName);

    public double getSkating();

    public void setSkating(double skating);

    public double getShooting();

    public void setShooting(double shooting);

    public double getChecking();

    public void setChecking(double checking);

    public double getSaving();

    public void setSaving(double saving);

    public boolean checkIfCoachValid(IValidation validation) throws Exception;
}
