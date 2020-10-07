package dhl.simulationStateMachine.States;

import dhl.leagueModel.interfaceModel.IConference;
import dhl.leagueModel.interfaceModel.IDivision;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.leagueModelData.ILeagueObjectModelData;
import dhl.leagueModelData.LeagueObjectModelData;
import dhl.simulationStateMachine.CreateLeagueObjectModel;
import dhl.simulationStateMachine.GameContext;
import dhl.simulationStateMachine.ImportJsonFile;
import dhl.simulationStateMachine.Interface.GameState;
import dhl.simulationStateMachine.JsonFilePath;
import org.json.simple.JSONObject;

import java.util.Scanner;

public class ImportState implements GameState {

    String validFilePath;
    ILeagueObjectModel NewInMemoryLeague;
    int option = -1;
    GameContext ourGame;

    public ImportState(GameContext newGame) {
        ourGame = newGame;
        validFilePath = null;
        NewInMemoryLeague = null;
    }

    @Override
    public void stateEntryProcess() {

        Scanner sc = new Scanner(System.in);

        while(option == -1 || option>3) {
            //System.out.println(option);
            System.out.println("Please Enter one option");
            System.out.println("1 for Loading JSON");
            System.out.println("2 for Loading Existing Team from DB");
            System.out.println("0 To Exit");

            option = sc.nextInt();
        }
        switch (option){
            case 0:
                System.out.println("Case:0");
                System.exit(0);
            case 1:
                System.out.println("case :1");

                validFilePath = new JsonFilePath().getFilePath();

                System.out.println(validFilePath);
                System.out.println(option);
                break;
            case 2:
                System.out.println("case :2");

                System.out.print("Enter LeagueName to load from DB: ");
                String leagueName = sc.nextLine();
                while(leagueName.equals("")){
                    System.out.println("Looks like you didnt add any input please try again: ");
                    leagueName = sc.nextLine();
                }

                System.out.print("Enter conference Name: ");
                String conference = sc.nextLine();
                while(conference.equals("")){
                    System.out.println("Looks like you didnt add any input please try again: ");
                    conference = sc.nextLine();
                }

                System.out.print("Enter Division Name");
                String division = sc.nextLine();
                while(division.equals("")){
                    System.out.println("Looks like you didnt add any input please try again: ");
                    division = sc.nextLine();
                }

                System.out.println("Enter Team Name");
                String team = sc.nextLine();
                while(team.equals("")){
                    System.out.println("Looks like you didnt add any input please try again: ");
                    team = sc.nextLine();
                }
                ILeagueObjectModelData databaseRefrenceOb = new LeagueObjectModelData();
                NewInMemoryLeague = NewInMemoryLeague.loadTeam(databaseRefrenceOb , leagueName, conference, division,team);

                for(int i=0; i< NewInMemoryLeague.getConferences().size();i++){
                    IConference ourConference = NewInMemoryLeague.getConferences().get(i);
                    if (ourConference.getConferenceName().equals(conference)){
                        for(int j=0;j< ourConference.getDivisions().size();j++){
                            IDivision ourDivision = ourConference.getDivisions().get(i);
                            if(ourDivision.getDivisionName().equals(division)){
                                for(int k=0; k< ourDivision.getTeams().size();k++){
                                    ITeam ourTeam = ourDivision.getTeams().get(i);
                                    if (ourTeam.getTeamName().equals(team)){
                                        ourGame.setSelectedTeam(ourTeam);
                                    }
                                }
                            }
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void stateProcess() throws Exception {
        if (validFilePath!= null){
            //System.out.println("Loading Json File into Memory from "+ validFilePath);
            JSONObject leagueJsonObject = new ImportJsonFile(validFilePath).getJsonObject();
            //System.out.println(leagueJsonObject);
            CreateLeagueObjectModel createLeagueObjectModel = new CreateLeagueObjectModel(leagueJsonObject);
            NewInMemoryLeague = createLeagueObjectModel.getLeagueObjectModel();
            System.out.println(NewInMemoryLeague.getLeagueName()+ "  Imported from the Json");
        }else{}
    }

    @Override
    public void stateExitProcess() {
        Scanner sc = new Scanner(System.in);
        ourGame.setInMemoryLeague(NewInMemoryLeague);
        //System.out.println("ExitState:  " + ourGame.getInMemoryLeague().getLeagueName());
        if (option==1) {
            ourGame.setGameState(ourGame.getCreateTeamState());
        }else if (option==2){
            ourGame.setGameState(ourGame.getSimulateState());
        }
    }
}
