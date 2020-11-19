package dhl.database.databaseConfigSetup;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseInitialize {
    private static DatabaseInitialize uniqueInstance = null;

    private String dbURL;
    private String dbUserName;
    private String dbPassword;
    private String dbDriver;

    public static DatabaseInitialize instance() throws Exception {
        if (null == uniqueInstance) {
            uniqueInstance = new DatabaseInitialize();
        }
        return uniqueInstance;
    }

    public DatabaseInitialize() throws IOException {
        DatabaseConfiguration defaultConfig = null;

        defaultConfig = new DatabaseConfiguration();

        dbURL = defaultConfig.getDatabaseURL();
        dbUserName = defaultConfig.getDatabaseUserName();
        dbPassword = defaultConfig.getDatabasePassword();
        dbDriver = defaultConfig.getDatabaseDriver();
    }

    public Connection getConnection() throws Exception {
        Class.forName(dbDriver);
        Connection databaseConnection = DriverManager.getConnection(dbURL, dbUserName, dbPassword);
        return databaseConnection;
    }

}


