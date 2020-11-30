package dhl.businessLogic.leagueModel;


import dhl.businessLogic.leagueModel.interfaceModel.ITeam;

public abstract class PlayerDraftAbstract {

    private static PlayerDraftAbstract uniqueInstance;

    public static PlayerDraftAbstract instance() {
        if (uniqueInstance == null) {
            uniqueInstance = new PlayerDraft();
        }
        return uniqueInstance;
    }

    public static void setFactory(PlayerDraftAbstract instance) {
        uniqueInstance = instance;
    }

    public abstract void swapDraftPick(int round, ITeam teamGettingDraft, ITeam teamGivingDraft);

    public abstract ITeam[][] getDraftPickSequence();

    public abstract void setDraftPickSequence(ITeam[][] draftPickSequence);
}
