package dhl.businessLogic.leagueModel.interfaceModel;


import dhl.businessLogic.leagueModel.PlayerDraft;

public abstract class IPlayerDraft {

    // TODO: 30-11-2020 naming convention
    private static IPlayerDraft uniqueInstance;

    public static IPlayerDraft instance() {
        if (uniqueInstance == null) {
            uniqueInstance = new PlayerDraft();
        }
        return uniqueInstance;
    }

    public static void setFactory(IPlayerDraft instance) {
        uniqueInstance = instance;
    }

    public abstract void swapDraftPick(int round, ITeam teamGettingDraft, ITeam teamGivingDraft);

    public abstract ITeam[][] getDraftPickSequence();

    public abstract void setDraftPickSequence(ITeam[][] draftPickSequence);
}
