package dhl.simulationStateMachine.States;

import dhl.database.LeagueObjectModelData;
import dhl.database.interfaceDB.ILeagueObjectModelData;
import dhl.leagueModel.LeagueObjectModel;
import dhl.leagueModel.Player;
import dhl.leagueModel.PlayerStatistics;
import dhl.leagueModel.Team;
import dhl.leagueModel.interfaceModel.*;
import dhl.simulationStateMachine.GameContext;
import dhl.simulationStateMachine.States.Interface.ICreateTeamStateLogic;

import java.util.ArrayList;

public class CreateTeamStateLogic implements ICreateTeamStateLogic {
    public ILeagueObjectModel saveleagueObject(String leagueName, String conferenceName, String divisionName, String teamName,
                                 String generalManager, ArrayList<IFreeAgent> freeAgents, ICoach coach,
                                 GameContext ourGame, ILeagueObjectModel inMemoryLeague, ILeagueObjectModelData leagueObjectModelData) throws Exception {
        ILeagueObjectModel leagueObjectModel = new LeagueObjectModel();
        try {
            leagueObjectModel = inMemoryLeague.saveLeagueObjectModel(
                    leagueObjectModelData,
                    leagueName,
                    conferenceName,
                    divisionName,
                    createNewTeamObject(freeAgents, teamName, generalManager, coach));

            ourGame.setSelectedTeam(findTeam(inMemoryLeague , teamName));
        } catch (Exception e){
            System.out.println(e.getMessage());
            ourGame.setGameInProgress(false);
        }
        return leagueObjectModel;
    }

    public ITeam createNewTeamObject(ArrayList<IFreeAgent> freeAgents, String teamName,
                                     String generalManager, ICoach coach) throws Exception {
        ArrayList<IPlayer> players= new ArrayList<>();

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



        ITeam newlyCreatedTeam=new Team(teamName,generalManager,coach,players);

        return newlyCreatedTeam;
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
    public String findGeneralManager(ArrayList<IGeneralManager> generalManagerArray, String generalManager ){
        for(int i= 0; i< generalManagerArray.size(); i++){
            IGeneralManager ourGeneralManager = generalManagerArray.get(i);
            if(ourGeneralManager.getGeneralManagerName().equals(generalManager)){
                return generalManager;
            }
        }
        return null;
    }
    public String findCoach(ArrayList<ICoach> coachArray, String coachName ){
        for(int i= 0; i< coachArray.size(); i++){
            ICoach ourCoach = coachArray.get(i);
            if(ourCoach.getCoachName().equals(coachName)){
                return coachName;
            }
        }
        return null;
    }
}
