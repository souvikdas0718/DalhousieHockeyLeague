package dhl.database;

import dhl.database.interfaceDB.*;

public class DatabaseObjectCreationDB {
    private ILeagueDB leagueDB;
    private IConferenceDB conferenceDB;
    private IDivisionDB divisionDB;
    private ITeamDB teamDB;
    private IPlayerDB playerDB;
    private IFreeAgentDB freeAgentDB;
    private ICoachDB coachDB;
    private IGeneralManagerDB generalManagerDB;
    private IGameConfigDB gameConfigDB;

    public DatabaseObjectCreationDB(){
        leagueDB = new LeagueDB();
        conferenceDB = new ConferenceDB();
        divisionDB = new DivisionDB();
        teamDB = new TeamDB();
        playerDB = new PlayerDB();
        freeAgentDB = new FreeAgentDB();
        coachDB=new CoachDB();
        generalManagerDB = new GeneralManagerDB();
        gameConfigDB = new GameConfigurationsDB();
    }

    public ILeagueDB getLeagueDB() {
        return leagueDB;
    }

    public IConferenceDB getConferenceDB() {
        return conferenceDB;
    }

    public IDivisionDB getDivisionDB() {
        return divisionDB;
    }

    public ITeamDB getTeamDB() {
        return teamDB;
    }

    public IPlayerDB getPlayerDB() {
        return playerDB;
    }

    public IFreeAgentDB getFreeAgentDB() {
        return freeAgentDB;
    }

    public ICoachDB getCoachDB() {
        return coachDB;
    }

    public IGeneralManagerDB getGeneralManagerDB() {
        return generalManagerDB;
    }

    public IGameConfigDB getGameConfigDB() {
        return gameConfigDB;
    }
}
