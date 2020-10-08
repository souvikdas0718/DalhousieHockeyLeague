package dhl.simulationStateMachine.States;

import dhl.leagueModel.LeagueObjectModel;
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
    ILeagueObjectModel newInMemoryLeague;
    int option = -1;
    GameContext ourGame;

    public ImportState(GameContext newGame) {
        ourGame = newGame;
        validFilePath = null;
        newInMemoryLeague = new LeagueObjectModel();
    }

    @Override
    public void stateEntryProcess() {

        Scanner sc = new Scanner(System.in);

        while(option == -1 || option>3) {
            System.out.println("Please Enter one option");
            System.out.println("1 for Loading JSON");
            System.out.println("2 for Loading Existing Team from DB");
            System.out.println("0 To Exit");

            option = sc.nextInt();
            sc.nextLine();
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
                System.out.print("Enter LeagueName to load from DB: ");
                String leagueName = sc.next();
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

                ILeagueObjectModelData databaseRefrenceOb = new LeagueObjectModelData();
                try {
                    newInMemoryLeague = newInMemoryLeague.loadTeam(databaseRefrenceOb, leagueName, team);
                }catch(Exception e) {
                    System.out.println(e);
                };
/*
                for(int i=0; i< newInMemoryLeague.getConferences().size();i++){
                    IConference ourConference = newInMemoryLeague.getConferences().get(i);
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

 */
                break;
        }
    }

    @Override
    public void stateProcess() throws Exception {
        if (validFilePath!= null){
            JSONObject leagueJsonObject = new ImportJsonFile(validFilePath).getJsonObject();
            CreateLeagueObjectModel createLeagueObjectModel = new CreateLeagueObjectModel(leagueJsonObject);
            newInMemoryLeague = createLeagueObjectModel.getLeagueObjectModel();
            System.out.println(newInMemoryLeague.getLeagueName()+ "  Imported from the Json");
        }else{}
    }

    @Override
    public void stateExitProcess() {
        Scanner sc = new Scanner(System.in);
        ourGame.setInMemoryLeague(newInMemoryLeague);
        if (option==1) {
            ourGame.setGameState(ourGame.getCreateTeamState());
        }else if (option==2){
            ourGame.setGameState(ourGame.getSimulateState());
        }
    }
    public ITeam findTeam(ILeagueObjectModel InMemoryLeague, String team){

        return null;
    }
}
