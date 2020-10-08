package dhl.simulationStateMachineTest;

import dhl.leagueModel.Conference;
import dhl.leagueModel.Division;
import dhl.leagueModel.Player;
import dhl.leagueModel.Team;
import dhl.leagueModel.interfaceModel.IConference;
import dhl.leagueModel.interfaceModel.IDivision;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.ITeam;

import java.util.ArrayList;

public class LeagueObjectModelMocks {

    public IPlayer getPlayerObjectMock(){
        IPlayer playerMock = new Player(
                "Mock Player",
                "defense",
                false
        );
        return playerMock;
    }

    public ArrayList<IPlayer> getPlayerArrayMock(){
        ArrayList<IPlayer> playerArrayListMock = new ArrayList<>();

        playerArrayListMock.add(new Player(
                "Mock Player",
                "defense",
                false
        ));
        playerArrayListMock.add(new Player(
                "Mock Player2",
                "forward",
                true
        ));
        return playerArrayListMock;
    }

    public ITeam getTeamObjectMock(){
        return new Team("Mock Team", "Mock Manager", "Mock Coach", getPlayerArrayMock() );
    }

    public ArrayList<ITeam> getTeamArrayMock(){
        ArrayList<ITeam> teamArrayListMock = new ArrayList<>();

        teamArrayListMock.add(new Team("Mock Team2" , "mock Manager2", "Mock Coach2", getPlayerArrayMock()));
        teamArrayListMock.add(new Team("Mock Team1" , "mock Manager1", "Mock Coach1", getPlayerArrayMock()));

        return teamArrayListMock;
    }

    public IDivision getDivisionObjectMock(){
        return new Division("Mock Division" , getTeamArrayMock());
    }

    public ArrayList<IDivision> getDivisionArrayMock(){
        ArrayList<IDivision> divisionArrayListMock = new ArrayList<>();

        divisionArrayListMock.add(new Division("Mock Division2" , getTeamArrayMock()));
        divisionArrayListMock.add(new Division("Mock Division1" , getTeamArrayMock()));

        return divisionArrayListMock;
    }

    public IConference getConferenceObjectMock(){
        return new Conference( "Mock Conference" , getDivisionArrayMock());
    }

    public ArrayList<IConference> getConferenceArrayMock(){
        ArrayList<IConference> conferenceArrayListMock= new ArrayList<>();

        conferenceArrayListMock.add(new Conference("Mock Conference1" , getDivisionArrayMock()));
        conferenceArrayListMock.add(new Conference("Mock Conference2" , getDivisionArrayMock()));

        return conferenceArrayListMock;
    }
}
