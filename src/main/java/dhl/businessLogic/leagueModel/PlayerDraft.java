package dhl.businessLogic.leagueModel;

import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayerDraft;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;


public class PlayerDraft extends IPlayerDraft {


    int numberOfTeams;
    ITeam[][] draftPickSequence ;

    public PlayerDraft(){
    }

    public PlayerDraft(ITeam[][] draftPickSequence){
        this.numberOfTeams = 0;
        this.draftPickSequence = draftPickSequence;
    }

    public void swapDraftPick(int round, ITeam teamGettingDraft, ITeam teamGivingDraft){
        for (int i = 0; i < draftPickSequence.length; i++){
            if (draftPickSequence[i][round] == teamGivingDraft){
                draftPickSequence[i][round] = teamGettingDraft;
            }
        }
    }

    public ITeam[][] getDraftPickSequence() {
        return draftPickSequence;
    }
}

