package dhl.leagueModel;

import dhl.leagueModel.interfaceModel.*;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class LeagueObjectModelValidation implements ILeagueObjectModelValidation {

    public boolean checkIfLeagueObjectModelValid(IValidation validation,ILeagueObjectModel leagueObjectModel) throws Exception{
        validation.isStringEmpty(leagueObjectModel.getLeagueName(),"League name");
        this.checkIfLeagueDetailsValid(leagueObjectModel.getConferences());
        return true;
    }

    public void checkIfLeagueDetailsValid(List<IConference> conferences) throws Exception{
        if(conferences!=null && conferences.size()%2!=0){
            throw new Exception("A League must contain even number of conferences");
        }
        List<String> conferenceNames=new ArrayList<>();
        conferences.stream().map(conference-> conference.getConferenceName()).forEach(confName->conferenceNames.add(confName));
        Set<String> conferenceSet = new HashSet<>(conferenceNames);
        if (conferenceSet.size() < conferences.size()) {
            throw new Exception("The names of conferences inside a league must be unique");
        }
    }

    public boolean checkUserInputForLeague(ILeagueObjectModel leagueObjectModel, ILeagueObjectModelInput leagueObjectModelInput)throws Exception{
        if(leagueObjectModelInput.getLeagueName() != leagueObjectModel.getLeagueName()){
            throw new Exception("League name is not present in file imported.");
        }
        if(leagueObjectModel.getConferences()!=null){
            List<IConference> conferenceList=leagueObjectModel.getConferences().stream().filter((IConference conference)->
            {return leagueObjectModelInput.getConferenceName().equals(conference.getConferenceName());
            }).collect(Collectors.toList());
            if(conferenceList.size()==0){
                throw new Exception("Conference name is not present in file imported");
            }
            IConference selectedConference=conferenceList.get(0);
            List<IDivision> divisionList=selectedConference.getDivisions().stream().filter((IDivision division)->{
                return leagueObjectModelInput.getDivisionName().equals(division.getDivisionName());
            }).collect(Collectors.toList());
            if(divisionList.size()==0){
                throw new Exception("Division name is not present in file imported");
            }
            IDivision  selectedDivision=divisionList.get(0);
            ITeam newTeam = leagueObjectModelInput.getNewlyCreatedTeam();
            List<ITeam> teamList=selectedDivision.getTeams().stream().filter((ITeam team)->{return newTeam.getTeamName()==team.getTeamName();}).collect(Collectors.toList());
            if(teamList.size()!=0){
                throw new Exception("Team name entered is already present in file imported");
            }
        }
        return  true;
    }
}
