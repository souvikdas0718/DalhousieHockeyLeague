package dhl.businessLogic.leagueModel.interfaceModel;

public interface ICoach {

    String getCoachName();

    double getSkating();

    double getShooting();

    double getChecking();

    double getSaving();

    boolean isCoachStatInvalid(double statValue);

}
