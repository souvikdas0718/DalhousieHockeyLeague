package dhl.leagueModel.interfaceModel;

public interface ICoach {
    String getCoachName();

    double getSkating();

    double getShooting();

    double getChecking();

    double getSaving();

    boolean checkIfCoachValid(IValidation validation) throws Exception;
}
