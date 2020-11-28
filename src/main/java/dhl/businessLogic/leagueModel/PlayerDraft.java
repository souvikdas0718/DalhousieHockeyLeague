package dhl.businessLogic.leagueModel;

import dhl.businessLogic.leagueModel.interfaceModel.*;

import java.util.ArrayList;

public class PlayerDraft implements IPlayerDraft{

    int NUMBEROFROUNDS = 7;

    ILeagueObjectModel league;
    int numberOfTeams;
    ArrayList<ArrayList<ITeam>> draftPickSequence = null;

    public PlayerDraft(ILeagueObjectModel league){
        this.numberOfTeams = 0;
        this.league = league;
        draftPickSequence = new ArrayList<>();
        initDraftSequence(league);
    }

    public void initDraftSequence(ILeagueObjectModel league){
        for(int i = 0; i < NUMBEROFROUNDS; i++){
            ArrayList<ITeam> draftRound = new ArrayList<>();
            for(IConference conference: league.getConferences()){
                for (IDivision division: conference.getDivisions()){
                    for (ITeam team : division.getTeams()){
                        draftRound.add(team);
                    }
                }
            }
            draftPickSequence.add(draftRound);
        }
    }

    public void swapDraftPick(int round, ITeam teamGettingDraft, ITeam teamGivingDraft){
        ArrayList<ITeam> draftRound = draftPickSequence.get(round);
        for (int i = 0; i < draftRound.size(); i++){
            if (draftRound.get(i) == teamGivingDraft){
                (draftPickSequence.get(round)).set(i , teamGettingDraft);
            }
        }
    }

    public ArrayList<ArrayList<ITeam>> getDraftPickSequence() {
        return draftPickSequence;
    }
}
