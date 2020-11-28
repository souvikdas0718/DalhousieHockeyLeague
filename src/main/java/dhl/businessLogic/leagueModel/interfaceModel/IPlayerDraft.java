package dhl.businessLogic.leagueModel.interfaceModel;

import java.util.ArrayList;

public interface IPlayerDraft {

    public void swapDraftPick(int round, ITeam teamGettingDraft, ITeam teamGivingDraft);

    public ArrayList<ArrayList<ITeam>> getDraftPickSequence();

}
