package dhl;

import dhl.leagueModel.LeagueObjectModel;
import dhl.leagueModel.interfaceModel.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class LeagueObjectModelTest {
    LeagueObjectModel leagueModel;
    @BeforeEach
    public void initialize(){
        leagueModel=new LeagueObjectModel();
    }

    @Test
    public void defaultConstructorTest(){
        Assert.assertTrue(leagueModel.getLeagueName().isEmpty());
        leagueModel.setLeagueName("Rob");
        Assert.assertEquals("Rob",leagueModel.getLeagueName() );
    }
    @Test
    public void setTeamPlayerMappingTest(){
        IParserOutput parsedOutput=new MockParserOutput();
        HashMap<String, ArrayList<IPlayer>> teamPlayersList= parsedOutput.getTeamPlayers();
        leagueModel.setTeamPlayerMapping(parsedOutput);
       Assert.assertEquals(leagueModel.getTeamPlayerMapping().size(),teamPlayersList.size());
    }

    @Test
    public void setDivisionTeamsMappingTest(){
        IParserOutput parsedOutput=new MockParserOutput();
        HashMap<String, ArrayList<ITeam>> divisionTeams= parsedOutput.getDivisionTeams();
        leagueModel.setDivisionTeamsMapping(parsedOutput);
        Assert.assertEquals(leagueModel.getDivisionTeamsMapping().size(),divisionTeams.size());
    }

    @Test
    public void setConferenceDivisionsMappingTest(){
        IParserOutput parsedOutput=new MockParserOutput();
        HashMap<String, ArrayList<IDivision>> conferenceDivisions= parsedOutput.getConferenceDivisions();
        leagueModel.setConferenceDivisionsMapping(parsedOutput);
        Assert.assertEquals(leagueModel.getConferenceDivisionsMapping().size(),conferenceDivisions.size());
    }

    @Test
    public void setLeagueConferencesMappingTest(){
        IParserOutput parsedOutput=new MockParserOutput();
        HashMap<String, ArrayList<IConference>> leagueConferences= parsedOutput.getLeagueConferences();
        leagueModel.setLeagueConferencesMapping(parsedOutput);
        Assert.assertEquals(leagueModel.getLeagueConferenceMapping().size(),leagueConferences.size());
    }
    @Test
    public void setFreeAgentsTest(){
        IParserOutput parsedOutput=new MockParserOutput();
        ArrayList<IPlayer> freeAgents= parsedOutput.getFreeAgents();
        leagueModel.setFreeAgents(parsedOutput);
        Assert.assertEquals(leagueModel.getFreeAgents().size(),freeAgents.size());
    }
}


