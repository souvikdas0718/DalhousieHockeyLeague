package dhl.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseInitialize {

    public static String dbURL;
    public static String dbUserName;
    public static String dbPassword;
    public static String dbDriver;

    private void loadDBProperties() throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("../../config.properties");

        if (fileInputStream != null) {
            System.out.println("Reading Success");
        } else {
            System.out.println("Reading failed");
        }
        Properties dbProperties = new Properties();
        try {
            dbProperties.load(fileInputStream);
            dbURL = dbProperties.getProperty("dbURL");
            dbUserName = dbProperties.getProperty("dbUserName");
            dbPassword = dbProperties.getProperty("dbPassword");
            dbDriver = dbProperties.getProperty("dbDriver");
            System.out.println("dbURL: " + dbURL);
            System.out.println("dbUserName: " + dbUserName);
            System.out.println("dbPassword: " + dbPassword);
            System.out.println("dbDriver: " + dbDriver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {

        try {
            loadDBProperties();
            Class.forName(dbDriver);

            Connection databaseConnection = DriverManager.getConnection(dbURL, dbUserName, dbPassword);
            return databaseConnection;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

}