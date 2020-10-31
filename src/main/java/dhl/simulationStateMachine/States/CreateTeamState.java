package dhl.simulationStateMachine.States;

import dhl.leagueModel.*;
import dhl.leagueModel.interfaceModel.*;
import dhl.database.interfaceDB.ILeagueObjectModelData;
import dhl.database.LeagueObjectModelData;
import dhl.simulationStateMachine.GameContext;
import dhl.simulationStateMachine.Interface.IGameState;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CreateTeamState implements IGameState {
    GameContext ourGame;
    ILeagueObjectModel inMemoryLeague;
    IConference selectedConference;
    IDivision selectedDivision;
    String teamName;
    String generalManager;
    String headCoach;
    ICoach coach;
    List<IPlayer> freeAgents;

    public CreateTeamState(GameContext newGame){
        ourGame = newGame;
        selectedConference = null;
        selectedDivision = null;
        teamName =generalManager=headCoach = null;
        freeAgents = new ArrayList<>();
        coach = new Coach();
    }

    @Override
    public void stateEntryProcess() {
        Scanner sc = new Scanner(System.in);
        inMemoryLeague = ourGame.getInMemoryLeague();

        System.out.println("------------------------LETS CREATE NEW TEAM---------------------");

        List<IConference> conferencesArray = inMemoryLeague.getConferences();
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
        List<IDivision> divisionArrayList = selectedConference.getDivisions();
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
        System.out.println("Select Team's General Manager Name: ");
        List<IGeneralManager> generalManagerArray = inMemoryLeague.getManagers();
        generalManagerArray.forEach((generalManager)->{
            System.out.println(generalManager.getGeneralManagerName());
        });
        generalManager  = sc.nextLine();
        while(generalManager.equals("")){
            System.out.println("Looks like you didnt add any input please try again: ");
            generalManager = sc.nextLine();
        }
        System.out.println("Select Team's Head Coach Name: ");
        List<ICoach> coachArray = inMemoryLeague.getCoaches();
        coachArray.forEach((coach)->{
            System.out.println(coach.getCoachName() + " " + coach.getChecking() + " " + coach.getSaving() + " " + coach.getSkating() + " " + coach.getShooting());
        });
        headCoach  = sc.nextLine();
        while(headCoach.equals("")){
            System.out.println("Looks like you didnt add any input please try again: ");
            headCoach = sc.nextLine();
        }
        for(int i=0; i< coachArray.size();i++){
            if (coachArray.get(i).getCoachName().equals(headCoach)){
                coach = coachArray.get(i);
            }
        }

        List<IPlayer> freeAgentsArray = inMemoryLeague.getFreeAgents();
        System.out.println("Select the Players from free Agents list (Input multiple names separated by a comma):");
        for(int i=0; i< freeAgentsArray.size();i++){
            System.out.println((i+1) +".  "+ freeAgentsArray.get(i).getPlayerName() );
        }

        String selectedfreeAgents = sc.nextLine();
        String[] arr = selectedfreeAgents.split(",");
        for (int i=0; i<arr.length; i++){
            IPlayer currentFreeAgent = findFreeAgent(freeAgentsArray, arr[i].trim().toString());
            if (currentFreeAgent != null) {
                freeAgents.add(currentFreeAgent);
            }
            else{
                System.out.println("Free agent Doesn't Exist");
                System.out.println("Enter the Free Agent Name Again or enter Exit to Quit game");
                selectedfreeAgents = sc.nextLine();
            }
        }
        if (selectedfreeAgents.equals("Exit")){
            System.exit(0);
        }
    }

    @Override
    public void stateProcess() throws Exception {
        System.out.println("Adding Team "+ teamName+ " to the DB");
        ILeagueObjectModelData leagueObjectModelData = new LeagueObjectModelData();
        List<IPlayer> players= new ArrayList<>();

        freeAgents.forEach((freeAgent)->{
            IPlayer player = new Player(freeAgent.getPlayerName(),freeAgent.getPosition(),false,freeAgent.getPlayerStats());
            players.add(player);
        });
        ITeam newlyCreatedTeam=new Team(teamName,generalManager,coach,players);
        ILeagueObjectModelValidation leagueObjectModelValidation = new LeagueObjectModelValidation();
        ILeagueObjectModelInput leagueObjectModelInput = new LeagueObjectModelInput(inMemoryLeague.getLeagueName(), selectedConference.getConferenceName(), selectedDivision.getDivisionName(), newlyCreatedTeam,leagueObjectModelValidation);
        IValidation validation = new CommonValidation();
        newlyCreatedTeam.checkIfTeamValid(validation);
        try {
            inMemoryLeague.saveLeagueObjectModel(leagueObjectModelData, leagueObjectModelInput);

            ourGame.setSelectedTeam(findTeam(inMemoryLeague , teamName));
        }catch (Exception e){
            System.out.println(e.getMessage());
            ourGame.setGameinProgress(false);
        }
    }

    @Override
    public void stateExitProcess() {
        if(ourGame.isGameinProgress()) {
            ourGame.setGameState(ourGame.getSimulateState());
        }
    }

    public IConference findConference(List<IConference> confrenceArray, String conferenceName ){
        for(int i= 0; i< confrenceArray.size(); i++){
            IConference ourConference = confrenceArray.get(i);
            if(ourConference.getConferenceName().equals(conferenceName)){
                return ourConference;
            }
        }
        return null;
    }

    public IDivision findDivision(List<IDivision> divisionArrayList , String divisionName){
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

    public IPlayer findFreeAgent(List<IPlayer> freeAgentArrayList, String freeAgentName){
        for(int i= 0; i< freeAgentArrayList.size(); i++){
            IPlayer ourFreeAgent = freeAgentArrayList.get(i);
            if(ourFreeAgent.getPlayerName().equals(freeAgentName)){
                return ourFreeAgent;
            }
        }
        return null;
    }
}
