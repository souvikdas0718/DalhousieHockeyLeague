package dhl.leagueModel;

import dhl.leagueModel.interfaceModel.IConference;

public class Conference implements IConference {
    private int conferenceId;
    private String conferenceName;
    private String leagueName;


    public Conference(){
      setDefaults();
    }
    public void setDefaults(){
        conferenceName="";
        leagueName="";
    }
    public String getConferenceName() {
        return conferenceName;
    }

    public void setConferenceName(String conferenceName) {
        this.conferenceName = conferenceName;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }




}
