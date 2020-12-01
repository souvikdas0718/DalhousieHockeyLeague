package dhl.inputOutput.ui;

import dhl.businessLogic.leagueModel.interfaceModel.ICoach;
import dhl.inputOutput.ui.interfaces.IHeadCoachListFormat;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;

import java.util.List;

public class HeadCoachListFormat implements IHeadCoachListFormat {

    IUserInputOutput ioObject;
    private static IHeadCoachListFormat singletonObject = null;

    private HeadCoachListFormat() {
        ioObject = IUserInputOutput.getInstance();
    }

    public static IHeadCoachListFormat getInstance() {
        if (singletonObject == null) {
            singletonObject = new HeadCoachListFormat();
        }
        return singletonObject;
    }

    public void showHeadCoachList(List<ICoach> coachArray) {
        String coachListHeader = String.format("%20s %10s %10s %10s %10s", "Name", "Checking", "Saving", "Shooting", "Skating");
        ioObject.printMessage(coachListHeader);

        ioObject.printMessage("-----------------------------------------------------------------------------");

        coachArray.forEach((selectedCoach) -> {
            String formattedCoachList = String.format("%20s %10s %10s %10s %10s", selectedCoach.getCoachName(), Double.toString(selectedCoach.getChecking()), Double.toString(selectedCoach.getSaving()), Double.toString(selectedCoach.getShooting()), Double.toString(selectedCoach.getSkating()));
            ioObject.printMessage(formattedCoachList);

        });
    }
}
