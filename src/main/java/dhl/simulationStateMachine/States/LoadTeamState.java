package dhl.simulationStateMachine.States;

import dhl.leagueModel.LeagueObjectModel;
import dhl.leagueModel.interfaceModel.IConference;
import dhl.leagueModel.interfaceModel.IDivision;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.database.ILeagueObjectModelData;
import dhl.database.LeagueObjectModelData;
import dhl.simulationStateMachine.GameContext;
import dhl.simulationStateMachine.Interface.GameState;
import java.util.ArrayList;
import java.util.Scanner;

public class LoadTeamState implements GameState {
    GameContext ourGame;
    ILeagueObjectModel ourLeague;
    ITeam selectedTeam;
    ILeagueObjectModel newInMemoryLeague;

    public LoadTeamState(GameContext newGame) {
        newInMemoryLeague = new LeagueObjectModel();
        ourGame = newGame;
    }

    @Override
    public void stateEntryProcess() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter LeagueName to load from DB: ");
        String leagueName = sc.nextLine();
        while(leagueName.equals("")){
            System.out.println("Looks like you didnt add any input please try again: ");
            leagueName = sc.nextLine();
        }

        System.out.print("Enter Team Name:  ");
        String team = sc.nextLine();
        while(team.equals("")){
            System.out.println("Looks like you didnt add any input please try again: ");
            team = sc.nextLine();
        }

        try {
            System.out.println("Finding: "+ team+ " of League:"+ leagueName+ " In DataBase");
            ILeagueObjectModelData databaseRefrenceOb = new LeagueObjectModelData();
            newInMemoryLeague = newInMemoryLeague.loadLeagueObjectModel(databaseRefrenceOb, leagueName, team);
            ourGame.setSelectedTeam(findTeam(newInMemoryLeague , team));
        }catch(Exception e) {
            System.out.println(e.getMessage());
            ourGame.setGameinProgress(false);
        };

    }

    @Override
    public void stateProcess() {
        if(ourGame.isGameinProgress()) {
            System.out.println(ourGame.getSelectedTeam().getTeamName() + "  Team Selected");
        }
    }

    @Override
    public void stateExitProcess() {
        if(ourGame.isGameinProgress()) {
            ourGame.setGameState(ourGame.getSimulateState());
        }
    }

    public IConference findConference(ArrayList<IConference> confrenceArray, String conferenceName ){
        for(int i= 0; i< confrenceArray.size(); i++){
            IConference ourConference = confrenceArray.get(i);
            if(ourConference.getConferenceName().equals(conferenceName)){
                return ourConference;
            }
        }
        return null;
    }

    public IDivision findDivision(ArrayList<IDivision> divisionArrayList , String divisionName){
        for(int i= 0; i< divisionArrayList.size(); i++){
            IDivision ourDivision = divisionArrayList.get(i);
            if(ourDivision.getDivisionName().equals(divisionName)){
                return ourDivision;
            }
        }
        return null;
    }

    public ITeam findTeam(ILeagueObjectModel inMemoryLeague, String teamName){

        ITeam teamObject = null;

        for(IConference conference: inMemoryLeague.getConferences() ){
            for(IDivision division: conference.getDivisions()){
                for (ITeam team: division.getTeams()){
                    if (team.getTeamName().equals(teamName)){
                        teamObject = team;
                        System.out.println("Team Found");
                    }
                }
            }
        }

        return teamObject;
    }
}
