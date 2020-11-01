package dhl.database;

import dhl.database.interfaceDB.*;

public class DatabaseObjectCreationDB {
    private ILeagueDB ileagueDB;
    private IConferenceDB iConferenceDB;
    private IDivisionDB iDivisionDB;
    private ITeamDB iTeamDB;
    private IPlayerDB iPlayerDB;
    private IFreeAgentDB ifreeAgentDB;
    private ICoachDB iCoachDB;
    private IGeneralManagerDB iGeneralManagerDB;
    private IGameConfigDB iGameConfigDB;

    public DatabaseObjectCreationDB(){
        ileagueDB = new LeagueDB();
        iConferenceDB = new ConferenceDB();
        iDivisionDB = new DivisionDB();
        iTeamDB = new TeamDB();
        iPlayerDB = new PlayerDB();
        ifreeAgentDB = new FreeAgentDB();
        iCoachDB=new CoachDB();
        iGeneralManagerDB = new GeneralManagerDB();
        //iGameConfigDB = new GameConfigDB();
    }

    public ILeagueDB getIleagueDB() {
        return ileagueDB;
    }

    public IConferenceDB getiConferenceDB() {
        return iConferenceDB;
    }

    public IGameConfigDB getiGameConfigDB() {
        return iGameConfigDB;
    }

    public IDivisionDB getiDivisionDB() {
        return iDivisionDB;
    }

    public ITeamDB getiTeamDB() {
        return iTeamDB;
    }

    public IPlayerDB getiPlayerDB() {
        return iPlayerDB;
    }

    public IFreeAgentDB getIfreeAgentDB() {
        return ifreeAgentDB;
    }

    public ICoachDB getiCoachDB() {
        return iCoachDB;
    }

    public IGeneralManagerDB getiGeneralManagerDB() {
        return iGeneralManagerDB;
    }
}
