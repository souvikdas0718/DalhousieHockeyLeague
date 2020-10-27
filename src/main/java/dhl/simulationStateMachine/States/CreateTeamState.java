package dhl.simulationStateMachine.States;

import dhl.leagueModel.Player;
import dhl.leagueModel.Coach;
import dhl.leagueModel.PlayerStatistics;
import dhl.leagueModel.Team;
import dhl.leagueModel.interfaceModel.*;
import dhl.database.interfaceDB.ILeagueObjectModelData;
import dhl.database.LeagueObjectModelData;
import dhl.simulationStateMachine.GameContext;
import dhl.simulationStateMachine.Interface.IGameState;

import java.util.ArrayList;

public class CreateTeamState implements IGameState {
    private GameContext ourGame;
    private ILeagueObjectModel inMemoryLeague;
    private IConference selectedConference;
    private IDivision selectedDivision;
    private String teamName;
    private String generalManager;
    private String headCoach;
    private ICoach coach;
    private ArrayList<IFreeAgent> freeAgents;
    private UserInterface userInterface;

    public CreateTeamState(GameContext newGame){
        ourGame = newGame;
        selectedConference = null;
        selectedDivision = null;
        teamName =generalManager=headCoach = null;
        freeAgents = new ArrayList<>();
        coach = new Coach();
        userInterface = new UserInterface();
    }

    @Override
    public void stateEntryProcess() {
        inMemoryLeague = ourGame.getInMemoryLeague();

        userInterface.printMessage("------------------------LETS CREATE NEW TEAM---------------------");
        selectConference();
        selectDivision();

        userInterface.printMessage("------------------------LETS ADD NEW TEAM NOW---------------------");
        selectTeam();
        selectGeneralManager();
        selectTeamHeadCoach();
        selectFreeAgents();
    }

    private void selectConference(){
        ArrayList<IConference> conferencesArray = inMemoryLeague.getConferences();
        userInterface.printMessage("Select the Conference");
        for(int i=0; i< conferencesArray.size();i++){
            userInterface.printMessage((i+1) +".  "+ conferencesArray.get(i).getConferenceName() );
        }

        String conference = userInterface.getUserInput();

        while ( selectedConference == null){
            selectedConference =  findConference(conferencesArray, conference);
            if(selectedConference!= null){
                userInterface.printMessage("Confrence Found: " + selectedConference.getConferenceName());
                break;
            }else{
                userInterface.printMessage("Confrence Dont Exist");
                userInterface.printMessage("Enter the Conference Name Again or enter Exit to Quit game");
                conference = userInterface.getUserInput();
            }
        }
        if (conference.equals("Exit")){
            System.exit(0);
        }
    }

    public void selectDivision(){
        ArrayList<IDivision> divisionArrayList = selectedConference.getDivisions();
        userInterface.printMessage("Select the Division");
        for(int i=0; i< divisionArrayList.size();i++){
            userInterface.printMessage((i+1) +".  "+ divisionArrayList.get(i).getDivisionName() );
        }
        String division = userInterface.getUserInput();

        while ( selectedDivision == null){
            selectedDivision =  findDivision(divisionArrayList, division);
            if(selectedDivision!= null){
                userInterface.printMessage("Division Found: " + selectedDivision.getDivisionName());
                break;
            }else{
                userInterface.printMessage("Division Dont Exist");
                userInterface.printMessage("Enter the Division Name Again or enter Exit to Quit game");
                division = userInterface.getUserInput();
            }
        }
        if (division.equals("Exit")){
            System.exit(0);
        }
    }

    private void selectTeam(){
        String userInput;
        userInterface.printMessage("Enter New Team Name: ");
        userInput = userInterface.getUserInput();

        while (teamName == null){
            if(userInput.equals("")){
                System.out.println("invalid team name, Enter name again or enter Exit to exit ");
                userInput = userInterface.getUserInput();
            }else if (userInput.equals("Exit")){
                System.exit(0);
            }else{
                teamName = userInput;
            }
        }
    }

    private void selectGeneralManager(){
        userInterface.printMessage("Select Team's General Manager Name: ");
        ArrayList<IGeneralManager> generalManagerArray = inMemoryLeague.getGeneralManagers();
        generalManagerArray.forEach((generalManager)->{
            userInterface.printMessage(generalManager.getGeneralManagerName());
        });
        generalManager  = userInterface.getUserInput();
        while(generalManager.equals("")){
            userInterface.printMessage("Looks like you didnt add any input please try again: ");
            generalManager = userInterface.getUserInput();
        }
    }

    private void selectTeamHeadCoach(){
        userInterface.printMessage("Select Team's Head Coach Name: ");
        ArrayList<ICoach> coachArray = inMemoryLeague.getCoaches();
        coachArray.forEach((coach)->{
            userInterface.printMessage(coach.getCoachName() + " " + coach.getChecking() + " " + coach.getSaving() + " " + coach.getSkating() + " " + coach.getShooting());
        });
        headCoach = userInterface.getUserInput();
        while(headCoach.equals("")){
            userInterface.printMessage("Looks like you didnt add any input please try again: ");
            headCoach = userInterface.getUserInput();
        }
        for(int i=0; i< coachArray.size();i++){
            if (coachArray.get(i).getCoachName().equals(headCoach)){
                coach = coachArray.get(i);
            }
        }
    }

    private void selectFreeAgents(){
        ArrayList<IFreeAgent> freeAgentsArray = inMemoryLeague.getFreeAgents();
        System.out.println("Select the Players from free Agents list (Input multiple names separated by a comma):");
        for(int i=0; i< freeAgentsArray.size();i++){
            userInterface.printMessage((i+1) +".  "+ freeAgentsArray.get(i).getPlayerName() );
        }

        String selectedfreeAgents = userInterface.getUserInput();
        String[] arr = selectedfreeAgents.split(",");
        for (int i=0; i<arr.length; i++){
            IFreeAgent currentFreeAgent = findFreeAgent(freeAgentsArray, arr[i].trim().toString());
            if (currentFreeAgent != null) {

                freeAgents.add(currentFreeAgent);
            }
            else{
                userInterface.printMessage("Free agent Doesn't Exist");
                userInterface.printMessage("Enter the Free Agent Name Again or enter Exit to Quit game");
                selectedfreeAgents = userInterface.getUserInput();
            }
        }

        if (selectedfreeAgents.equals("Exit")){
            System.exit(0);
        }
    }

    @Override
    public void stateProcess() throws Exception {
        try {
            userInterface.printMessage("Adding Team "+ teamName+ " to the DB");
            ILeagueObjectModelData leagueObjectModelData = new LeagueObjectModelData();

            inMemoryLeague.saveLeagueObjectModel(
                    leagueObjectModelData,
                    inMemoryLeague.getLeagueName(),
                    selectedConference.getConferenceName(),
                    selectedDivision.getDivisionName(),
                    createNewTeamObject());

            ourGame.setSelectedTeam(findTeam(inMemoryLeague , teamName));
        } catch (Exception e){
            System.out.println(e.getMessage());
            ourGame.setGameInProgress(false);
        }
    }

    private ITeam createNewTeamObject() throws Exception {
        ArrayList<IPlayer> players= new ArrayList<>();
        ITeam objTeam = new Team();

        freeAgents.forEach((freeAgent)->{
            IPlayer player = new Player();
            IPlayerStatistics playerstats = new PlayerStatistics(freeAgent.getPlayerStats().getAge(),
                    freeAgent.getPlayerStats().getSkating(),freeAgent.getPlayerStats().getShooting(),
                    freeAgent.getPlayerStats().getChecking(), freeAgent.getPlayerStats().getSaving());
            player.setPlayerName(freeAgent.getPlayerName());
            player.setPosition(freeAgent.getPosition());
            player.setCaptain(false);
            player.setPlayerStats(playerstats);
            players.add(player);
        });

        objTeam.checkIfSkatersGoaliesValid(players);

        ITeam newlyCreatedTeam=new Team(teamName,generalManager,coach,players);

        return newlyCreatedTeam;
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

    public IFreeAgent findFreeAgent(ArrayList<IFreeAgent> freeAgentArrayList, String freeAgentName){
        for(int i= 0; i< freeAgentArrayList.size(); i++){
            IFreeAgent ourFreeAgent = freeAgentArrayList.get(i);
            if(ourFreeAgent.getPlayerName().equals(freeAgentName)){
                return ourFreeAgent;
            }
        }
        return null;
    }
}
