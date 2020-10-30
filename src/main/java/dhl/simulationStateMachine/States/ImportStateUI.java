package dhl.simulationStateMachine.States;

import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.leagueModel.LeagueObjectModel;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.simulationStateMachine.GameContext;
import dhl.simulationStateMachine.Interface.IGameState;
import dhl.InputOutput.importJson.JsonFilePath;
import dhl.simulationStateMachine.States.Interface.IImportStateLogic;

import java.util.Scanner;

public class ImportStateUI implements IGameState {
    String validFilePath;
    ILeagueObjectModel newInMemoryLeague;
    int option = -1;
    GameContext ourGame;
    IGameConfig gameConfig;
    public ImportStateUI(GameContext newGame) {
        ourGame = newGame;
        validFilePath = null;
        newInMemoryLeague = new LeagueObjectModel();
    }

    @Override
    public void stateEntryProcess() {

        Scanner sc = new Scanner(System.in);

        while(option == -1 || option > 3) {
            System.out.println("Please Enter one option");
            System.out.println("1 for Loading JSON");
            System.out.println("2 for Loading Existing Team from DB");
            System.out.println("0 To Exit");

            try{
                option = Integer.parseInt(sc.next());
            } catch(NumberFormatException exception){
                System.out.println("This is not a Correct Option");
            }
            sc.nextLine();
        }
        switch (option){
            case 0:
                System.out.println("Case:0");
                System.exit(0);
            case 1:
                System.out.println("case :1");
                validFilePath = new JsonFilePath().getFilePath();
                break;
            case 2:
                System.out.println("===========LETS LOAD TEAM FROM DB THEN===========");
                break;
        }
    }

    @Override
    public void stateProcess() throws Exception {
        if (validFilePath!= null){
            try {
                IImportStateLogic objImportStateLogic = new ImportStateLogic();
                newInMemoryLeague = objImportStateLogic.importAndGetLeagueObject(validFilePath, gameConfig, newInMemoryLeague);
                System.out.println(newInMemoryLeague.getLeagueName() + "  Imported from the Json");
            }catch(Exception e){
                System.out.println(e.getMessage());
                System.exit(0);
            }
        }
    }

    @Override
    public void stateExitProcess() {
        if(ourGame.isGameInProgress()) {
            ourGame.setInMemoryLeague(newInMemoryLeague);
            ourGame.setGameConfig(gameConfig);
            if (option == 1) {
                ourGame.setGameState(ourGame.getCreateTeamState());
            } else if (option == 2) {
                ourGame.setGameState(ourGame.getLoadTeamState());
            }
        }
    }


}
