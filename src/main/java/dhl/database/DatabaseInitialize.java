package dhl.database;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseInitialize {

    public static String dbURL;
    public static String dbUserName;
    public static String dbPassword;
    public static String dbDriver;

    private void loadDBProperties(){
        InputStream propertiesStream = getClass().getClassLoader().getResourceAsStream("config.properties");
        Properties dbProperties = new Properties();
        try {
            dbProperties.load(propertiesStream);
            dbURL = dbProperties.getProperty("dbURLTest");
            dbUserName = dbProperties.getProperty("dbUsernameTest");
            dbPassword = dbProperties.getProperty("dbPasswordTest");
            dbDriver = dbProperties.getProperty("dbDriver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection(){

        try {
            loadDBProperties();
            Class.forName(dbDriver);

            Connection con = DriverManager.getConnection( dbURL ,dbUserName,dbPassword);
            return con;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

}
