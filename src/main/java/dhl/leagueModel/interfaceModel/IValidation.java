package dhl.leagueModel.interfaceModel;

import java.util.List;

public interface IValidation{
    public void isStringEmpty(String value,String fieldName) throws Exception;
    public void isListEmpty(List value, String fieldName) throws Exception;
}
