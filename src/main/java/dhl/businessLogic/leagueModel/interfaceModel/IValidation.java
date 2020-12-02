package dhl.businessLogic.leagueModel.interfaceModel;

import java.util.List;

public interface IValidation {

    boolean isStringEmpty(String value, String fieldName);

    boolean isListEmpty(List value, String fieldName);
}
