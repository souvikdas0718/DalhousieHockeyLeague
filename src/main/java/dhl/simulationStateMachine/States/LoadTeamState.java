package dhl.simulationStateMachine.States;

import dhl.leagueModel.interfaceModel.IConference;
import dhl.leagueModel.interfaceModel.IDivision;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.simulationStateMachine.GameContext;
import dhl.simulationStateMachine.Interface.GameState;

import java.util.ArrayList;
import java.util.Scanner;

public class LoadTeamState implements GameState {

    GameContext ourGame;
    ILeagueObjectModel ourLeague;
    ITeam selectedTeam;
    public LoadTeamState(GameContext newGame) {
        ourGame = newGame;
    }

    @Override
    public void stateEntryProcess() {
        //System.out.println(ourGame.getInMemoryLeague().getLeagueName());
        ourLeague = ourGame.getInMemoryLeague();
        ArrayList<IConference> conferencesArray = ourLeague.getConferences();
        Scanner sc = new Scanner(System.in);
        IConference selectedConference=null;
        IDivision selectedDivision = null;

        System.out.println("------------LETS LOAD A TEAM----------------");
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

        ArrayList<ITeam> teamArrayList = selectedDivision.getTeams();
        System.out.println("Select the Team");
        for(int i=0; i< teamArrayList.size();i++){
            System.out.println((i+1) +".  "+ teamArrayList.get(i).getTeamName() );
        }
        String team = sc.nextLine();

        while ( selectedTeam == null){
            selectedTeam =  findTeam(teamArrayList, team);
            if(selectedTeam!= null){
                System.out.println("Team Found: " + selectedTeam.getTeamName());
                break;
            }else{
                System.out.println("Team Dont Exist");
                System.out.println("Enter the Team Name Again or enter Exit to Quit game");
                team = sc.nextLine();
            }
        }
        if (team.equals("Exit")){
            System.exit(0);
        }
    }

    @Override
    public void stateProcess() {
        System.out.println( selectedTeam.getTeamName()+ "  Team Selected");
        ourGame.setSelectedTeam(selectedTeam);
    }

    @Override
    public void stateExitProcess() {
        ourGame.setGameState(ourGame.getSimulateState());
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
    public ITeam findTeam(ArrayList<ITeam> teamArrayList , String teamName){
        for(int i= 0; i< teamArrayList.size(); i++){
            ITeam ourTeam = teamArrayList.get(i);
            if(ourTeam.getTeamName().equals(teamName)){
                return ourTeam;
            }
        }
        return null;
    }
}
