package dhl.simulationStateMachine.States;

import dhl.leagueModel.Coach;
import dhl.leagueModel.Team;
import dhl.leagueModel.interfaceModel.*;
import dhl.database.interfaceDB.ILeagueObjectModelData;
import dhl.database.LeagueObjectModelData;
import dhl.simulationStateMachine.GameContext;
import dhl.simulationStateMachine.Interface.IGameState;
import java.util.ArrayList;
import java.util.Scanner;

public class CreateTeamState implements IGameState {
    GameContext ourGame;
    ILeagueObjectModel inMemoryLeague;
    IConference selectedConference;
    IDivision selectedDivision;
    String teamName;
    String generalManager;
    String headCoach;

    public CreateTeamState(GameContext newGame){
        ourGame = newGame;
        selectedConference = null;
        selectedDivision = null;
        teamName =generalManager=headCoach = null;
    }

    @Override
    public void stateEntryProcess() {
        Scanner sc = new Scanner(System.in);
        inMemoryLeague = ourGame.getInMemoryLeague();

        System.out.println("------------------------LETS CREATE NEW TEAM---------------------");

        ArrayList<IConference> conferencesArray = inMemoryLeague.getConferences();
        System.out.println("Select the Conference");
        for(int i=0; i< conferencesArray.size();i++){
            System.out.println((i+1) +".  "+ conferencesArray.get(i).getConferenceName() );
        }

        String conference = sc.nextLine();

        while ( selectedConference == null){
            selectedConference =  findConference(conferencesArray, conference);
            if(selectedConference!= null){
                System.out.println("Confrence Found: " + selectedConference.getConferenceName());
                break;
            }else{
                System.out.println("Confrence Dont Exist");
                System.out.println("Enter the Conference Name Again or enter Exit to Quit game");
                conference = sc.nextLine();
            }
        }
        if (conference.equals("Exit")){
            System.exit(0);
        }
        ArrayList<IDivision> divisionArrayList = selectedConference.getDivisions();
        System.out.println("Select the Division");
        for(int i=0; i< divisionArrayList.size();i++){
            System.out.println((i+1) +".  "+ divisionArrayList.get(i).getDivisionName() );
        }
        String division = sc.nextLine();

        while ( selectedDivision == null){
            selectedDivision =  findDivision(divisionArrayList, division);
            if(selectedDivision!= null){
                System.out.println("Division Found: " + selectedDivision.getDivisionName());
                break;
            }else{
                System.out.println("Division Dont Exist");
                System.out.println("Enter the Division Name Again or enter Exit to Quit game");
                division = sc.nextLine();
            }
        }
        if (division.equals("Exit")){
            System.exit(0);
        }

        System.out.println("------------------------LETS ADD NEW TEAM NOW---------------------");
        String userInput;
        System.out.print("Enter New Team Name: ");
        userInput = sc.nextLine();

        while (teamName == null){
            if(userInput.equals("")){
                System.out.println("invalid team name, Enter name again or enter Exit to exit ");
                userInput = sc.nextLine();
            }else if (userInput.equals("Exit")){
                System.exit(0);
            }else{
                teamName = userInput;
            }
        }
        System.out.print("Enter Team's General Manager Name: ");
        generalManager  = sc.nextLine();
        while(generalManager.equals("")){
            System.out.println("Looks like you didnt add any input please try again: ");
            generalManager = sc.nextLine();
        }
        System.out.print("Enter Team's Head Coach Name: ");
        headCoach  = sc.nextLine();
        while(headCoach.equals("")){
            System.out.println("Looks like you didnt add any input please try again: ");
            headCoach = sc.nextLine();
        }

    }

    @Override
    public void stateProcess() throws Exception {
        System.out.println("Adding Team "+ teamName+ " to the DB");
        ILeagueObjectModelData leagueObjectModelData = new LeagueObjectModelData();
        ArrayList<IPlayer> players= new ArrayList<>();
        ICoach coach = new Coach();
        ITeam newlyCreatedTeam=new Team(teamName,generalManager,coach,players);
        try {
            inMemoryLeague.saveLeagueObjectModel(
                    leagueObjectModelData,
                    inMemoryLeague.getLeagueName(),
                    selectedConference.getConferenceName(),
                    selectedDivision.getDivisionName(),
                    newlyCreatedTeam);

            ourGame.setSelectedTeam(findTeam(inMemoryLeague , teamName));
        }catch (Exception e){
            System.out.println(e.getMessage());
            ourGame.setGameInProgress(false);
        }
    }

    @Override
    public void stateExitProcess() {
        if(ourGame.isGameInProgress()) {
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
                    }
                }
            }
        }

        return teamObject;
    }
}
