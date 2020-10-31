package dhl.simulationStateMachine.States;

import dhl.leagueModel.LeagueObjectModel;
import dhl.leagueModel.interfaceModel.IConference;
import dhl.leagueModel.interfaceModel.IDivision;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.database.interfaceDB.ILeagueObjectModelData;
import dhl.database.LeagueObjectModelData;
import dhl.simulationStateMachine.GameContext;
import dhl.simulationStateMachine.Interface.IGameState;
import dhl.simulationStateMachine.States.Interface.ILoadTeamStateLogic;

import java.util.ArrayList;
import java.util.Scanner;

public class LoadTeamStateUI implements IGameState {
    GameContext ourGame;
    ILeagueObjectModel ourLeague;
    ITeam selectedTeam;
    ILeagueObjectModel newInMemoryLeague;

    public LoadTeamStateUI(GameContext newGame) {
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
            ILoadTeamStateLogic objLoadTeamStateLogic = new LoadTeamStateLogic();
            ILeagueObjectModelData databaseRefrenceOb = new LeagueObjectModelData();
            objLoadTeamStateLogic.findTeamOfLeagueInDatabase( leagueName,  team,  newInMemoryLeague,  ourGame, databaseRefrenceOb);
        }catch(Exception e) {
            System.out.println(e.getMessage());
            ourGame.setGameInProgress(false);
        };

    }

    @Override
    public void stateProcess() {
        if(ourGame.isGameInProgress()) {
            System.out.println(ourGame.getSelectedTeam().getTeamName() + "  Team Selected");
        }
    }

    @Override
    public void stateExitProcess() {
        if(ourGame.isGameInProgress()) {
            ourGame.setGameState(ourGame.getSimulateState());
        }
    }

}
