package dhl.simulationStateMachine.States;

import dhl.leagueModel.Coach;
import dhl.leagueModel.Team;
import dhl.leagueModel.interfaceModel.*;
import dhl.database.interfaceDB.ILeagueObjectModelData;
import dhl.database.LeagueObjectModelData;
import dhl.simulationStateMachine.GameContext;
import dhl.simulationStateMachine.Interface.IGameState;
import dhl.simulationStateMachine.States.Interface.ICreateTeamStateLogic;

import java.util.ArrayList;
import java.util.Scanner;

public class CreateTeamStateUI implements IGameState {
    private GameContext ourGame;
    private ILeagueObjectModel inMemoryLeague;
    private IConference selectedConference;
    private IDivision selectedDivision;
    private String teamName;
    private String selectedGeneralManager;
    private String selectedHeadCoach;
    private ICoach coach;
    private ArrayList<IFreeAgent> selectedFreeAgents;
    ICreateTeamStateLogic objCreateTeamStateLogic;

    Scanner sc = new Scanner(System.in);

    public CreateTeamStateUI(GameContext newGame){
        ourGame = newGame;
        selectedConference = null;
        selectedDivision = null;
        teamName =selectedGeneralManager=selectedHeadCoach = null;
        selectedFreeAgents = new ArrayList<>();
        coach = new Coach();
        objCreateTeamStateLogic = new CreateTeamStateLogic();
    }

    @Override
    public void stateEntryProcess() {
        inMemoryLeague = ourGame.getInMemoryLeague();

        System.out.println("------------------------LETS CREATE NEW TEAM---------------------");
        selectConference();
        selectDivision();

        System.out.println("------------------------LETS ADD NEW TEAM NOW---------------------");
        selectTeam();
        selectGeneralManager();
        selectTeamHeadCoach();
        selectFreeAgents();
    }

    public void selectConference(){
        ArrayList<IConference> conferencesArray = inMemoryLeague.getConferences();
        System.out.println("Select the Conference");
        for(int i=0; i< conferencesArray.size();i++){
            System.out.println((i+1) +".  "+ conferencesArray.get(i).getConferenceName());
        }

        String conference = sc.nextLine();

        while ( selectedConference == null){

            selectedConference =  objCreateTeamStateLogic.findConference(conferencesArray, conference);
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
    }

    public void selectDivision(){
        ArrayList<IDivision> divisionArrayList = selectedConference.getDivisions();
        System.out.println("Select the Division");
        for(int i=0; i< divisionArrayList.size();i++){
            System.out.println((i+1) +".  "+ divisionArrayList.get(i).getDivisionName() );
        }
        String division = sc.nextLine();

        while ( selectedDivision == null){

            selectedDivision =  objCreateTeamStateLogic.findDivision(divisionArrayList, division);
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
    }

    private void selectTeam(){
        String userInput;
        System.out.println("Enter New Team Name: ");
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
    }

    private void selectGeneralManager(){
        System.out.println("Select Team's General Manager Name: ");
        ArrayList<IGeneralManager> generalManagerArray = inMemoryLeague.getGeneralManagers();
        generalManagerArray.forEach((generalManager)->{
            System.out.println(generalManager.getGeneralManagerName());
        });
        String generalManager  = sc.nextLine();

        while ( selectedGeneralManager == null){
            ICreateTeamStateLogic objCreateTeamStateLogic = new CreateTeamStateLogic();
            selectedGeneralManager =  objCreateTeamStateLogic.findGeneralManager(generalManagerArray, generalManager);
            if(selectedGeneralManager == null){
                System.out.println("General Manager Doesn't Exist");
                System.out.println("Enter the General Manager Name Again or enter Exit to Quit game");
                generalManager = sc.nextLine();
            }
        }
        if (generalManager.equals("Exit")){
            System.exit(0);
        }
    }

    private void selectTeamHeadCoach(){
        System.out.println("Select Team's Head Coach Name: ");
        ArrayList<ICoach> coachArray = inMemoryLeague.getCoaches();
        System.out.println("-----------------------------------------------------------------------------");
        String.format("%20s %10s %10s %10s %10s","Name","Checking","Saving","Shooting","Skating");
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------");

        coachArray.forEach((coach)->{
            System.out.format("%20s %10s %10s %10s %10s", coach.getCoachName(), Double.toString(coach.getChecking()), Double.toString(coach.getSaving()), Double.toString(coach.getSkating()));
            System.out.println();
        });
        System.out.println("-----------------------------------------------------------------------------");
        String headCoach = sc.nextLine();
        while ( selectedHeadCoach == null){
            selectedHeadCoach =  objCreateTeamStateLogic.findCoach(coachArray, headCoach);
            if(selectedHeadCoach == null){
                System.out.println("Coach Doesn't Exist");
                System.out.println("Enter the Coach Name Again or enter Exit to Quit game");
                headCoach = sc.nextLine();
            }
        }
        if (headCoach.equals("Exit")){
            System.exit(0);
        }
        for(int i=0; i< coachArray.size();i++){
            if (coachArray.get(i).getCoachName().equals(headCoach)){
                coach = coachArray.get(i);
            }
        }
    }

    private void selectFreeAgents() {
        ArrayList<IFreeAgent> freeAgentsArray = inMemoryLeague.getFreeAgents();
        System.out.println("Select the Players from free Agents list (Input multiple names separated by a comma):");
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
        System.out.format("%20s %20s %10s %10s %10s %10s %10s","Name","Position","Age","Checking","Saving","Shooting","Skating");
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
        for(int i=0; i< freeAgentsArray.size();i++){
            System.out.format("%20s %20s %10d %10d %10d %10d %10d", freeAgentsArray.get(i).getPlayerName(), freeAgentsArray.get(i).getPosition(), freeAgentsArray.get(i).getPlayerStats().getAge(), freeAgentsArray.get(i).getPlayerStats().getChecking(), freeAgentsArray.get(i).getPlayerStats().getSaving(), freeAgentsArray.get(i).getPlayerStats().getShooting(), freeAgentsArray.get(i).getPlayerStats().getSkating()); System.out.println();
        }
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
        String inputfreeAgents = sc.nextLine();
        while ( selectedFreeAgents.size()==0) {
            try{
                selectedFreeAgents = objCreateTeamStateLogic.validateInputFreeAgents(inputfreeAgents, freeAgentsArray);
            }
            catch (Exception ex){
                System.out.println(ex.getMessage());
                inputfreeAgents = sc.nextLine();
            }
            if (inputfreeAgents.equals("Exit")){
                System.exit(0);
            }
        }
    }

    @Override
    public void stateProcess() throws Exception {
        try {
            System.out.println("Adding Team "+ teamName+ " to the DB");
            ILeagueObjectModelData leagueObjectModelData = new LeagueObjectModelData();

            ICreateTeamStateLogic obj = new CreateTeamStateLogic();
            obj.saveleagueObject(inMemoryLeague.getLeagueName(), selectedConference.getConferenceName(),
                    selectedDivision.getDivisionName(), teamName, selectedGeneralManager, selectedFreeAgents, coach, ourGame,
                    inMemoryLeague, leagueObjectModelData);

        } catch (Exception e){
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

}
