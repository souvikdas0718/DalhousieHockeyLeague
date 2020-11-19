package dhl.database.databaseConfigSetup;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DatabaseConfiguration {
    public DatabaseConfiguration() throws IOException {
        loadDbProperties();
    }

    private static String dbURL;
    private static String dbUserName;
    private static String dbPassword;
    private static String dbDriver;

    private void loadDbProperties() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("./config.properties");
        if (fileInputStream == null) {
            throw new IOException("Error connecting to database.");
        }
        Properties dbProperties = new Properties();

        dbProperties.load(fileInputStream);
        dbURL = dbProperties.getProperty("dbURL");
        dbUserName = dbProperties.getProperty("dbUserName");
        dbPassword = dbProperties.getProperty("dbPassword");
        dbDriver = dbProperties.getProperty("dbDriver");
    }

    public String getDatabaseUserName() {
        return dbUserName;
    }

    public String getDatabasePassword() {
        return dbPassword;
    }

    public String getDatabaseURL() {
        return dbURL;
    }

    public String getDatabaseDriver() {
        return dbDriver;
    }
}
