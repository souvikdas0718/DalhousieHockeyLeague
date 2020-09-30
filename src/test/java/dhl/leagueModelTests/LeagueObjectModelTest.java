package dhl.leagueModelTests;

import dhl.leagueModel.*;
import dhl.leagueModel.interfaceModel.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class LeagueObjectModelTest {
    LeagueObjectModel leagueModel;
    IParserOutput parsedOutput;
    @BeforeEach
    public void initialize(){
        leagueModel=new LeagueObjectModel();
        parsedOutput=new MockParserOutput();
    }

    @Test
    public void defaultConstructorTest(){
        Assertions.assertTrue(leagueModel.getLeagueName().isEmpty());
        leagueModel.setLeagueName("Dhl");
        Assertions.assertEquals("Dhl",leagueModel.getLeagueName() );
    }
    @Test
    public void setTeamPlayerMappingTest(){
        ArrayList<IPlayer> playerList = new ArrayList<IPlayer>();
        IPlayer player= new Player("Harry","forward",false,"Ontario");
        playerList.add(player);
        parsedOutput.setPlayers(playerList);
        leagueModel.setTeamPlayerMapping(parsedOutput,"Ontario");
        Assertions.assertEquals(leagueModel.getTeamPlayerMapping().get("Ontario").size(),playerList.size());
    }

    @Test
    public void setDivisionTeamsMappingTest(){
        ArrayList<ITeam> teamsList=new ArrayList<>();
        teamsList.add(new Team("Boston","Harry","Mike","Atlantic","Western"));
        parsedOutput.setTeams(teamsList);
        leagueModel.setDivisionTeamsMapping(parsedOutput,"Atlantic");
        Assertions.assertEquals(leagueModel.getDivisionTeamsMapping().get("Atlantic").size(),teamsList.size());
    }

    @Test
    public void setConferenceDivisionsMappingTest(){
        ArrayList<IDivision> divisionsList=new ArrayList<>();
        divisionsList.add(new Division("Atlantic"));
         parsedOutput.setDivisions(divisionsList);
        leagueModel.setConferenceDivisionsMapping(parsedOutput,"Western");
        Assertions.assertEquals(leagueModel.getConferenceDivisionsMapping().get("Western").size(),divisionsList.size());
    }

    @Test
    public void setLeagueConferencesMappingTest(){
        ArrayList<IConference> conferenceList=new ArrayList<>();
        conferenceList.add(new Conference("Western","Dhl"));
        parsedOutput.setConferences(conferenceList);
        leagueModel.setLeagueConferencesMapping(parsedOutput,"Dhl");
        Assertions.assertEquals(leagueModel.getLeagueConferenceMapping().get("Dhl").size(),conferenceList.size());
    }
    @Test
    public void setFreeAgentsTest(){
        IParserOutput parsedOutput=new MockParserOutput();
        ArrayList<IPlayer> freeAgents= parsedOutput.getFreeAgents();
        leagueModel.setFreeAgents(parsedOutput);
        Assertions.assertEquals(leagueModel.getFreeAgents().size(),freeAgents.size());
    }
    @AfterEach
    public void destroyObject(){
        leagueModel=null;
        parsedOutput=null;
    }
}


