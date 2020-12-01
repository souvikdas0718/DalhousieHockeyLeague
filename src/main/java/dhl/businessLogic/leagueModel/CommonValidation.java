package dhl.businessLogic.leagueModel;

import dhl.businessLogic.leagueModel.interfaceModel.IValidation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CommonValidation implements IValidation {
    private static final Logger logger = LogManager.getLogger(CommonValidation.class);

    public boolean isStringEmpty(String value, String fieldName) {
        logger.debug("Checking if string is empty for field :" + fieldName);
        return value.length() == 0;
    }

    public boolean isListEmpty(List listArray, String fieldName) {
        logger.debug("Checking if list is empty for field :" + fieldName);
        return listArray.size() == 0;
    }

}
