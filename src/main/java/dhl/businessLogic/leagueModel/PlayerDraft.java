package dhl.businessLogic.leagueModel;

import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerDraft extends PlayerDraftAbstract {

    ITeam[][] draftPickSequence;
    private static final Logger logger = LogManager.getLogger(PlayerDraft.class);

    public PlayerDraft() {
    }

    public void swapDraftPick(int round, ITeam teamGettingDraft, ITeam teamGivingDraft) {
        logger.info("Swapping Draft round: " + (round + 1) + " From " + teamGivingDraft.getTeamName() + " To " + teamGettingDraft);
        for (int i = 0; i < draftPickSequence.length; i++) {
            if (draftPickSequence[i][round] == teamGivingDraft) {
                draftPickSequence[i][round] = teamGettingDraft;
            }
        }
    }

    public ITeam[][] getDraftPickSequence() {
        return draftPickSequence;
    }

    public void setDraftPickSequence(ITeam[][] draftPickSequence) {
        this.draftPickSequence = draftPickSequence;
    }
}

