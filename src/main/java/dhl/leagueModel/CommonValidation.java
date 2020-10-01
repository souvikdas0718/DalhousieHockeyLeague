package dhl.leagueModel;

import dhl.leagueModel.interfaceModel.IValidation;

import java.util.List;

public class CommonValidation implements IValidation {

    public void isStringEmpty(String value, String fieldName) throws Exception {
        if(value.length()==0){
            throw new Exception(fieldName+" name cannot be empty");
        }
    }

    public void isListEmpty(List  listArray, String fieldName) throws Exception {
        if(listArray.size()==0){
            throw new Exception("Please add " +fieldName);
        }
    }


    
}
