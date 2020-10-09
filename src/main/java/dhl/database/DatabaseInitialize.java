package dhl.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseInitialize {
    public static String dbURL;
    public static String dbUserName;
    public static String dbPassword;
    public static String dbDriver;

    private void loadDbProperties() throws IOException {
            FileInputStream fileInputStream = new FileInputStream("../../config.properties");
            if (fileInputStream != null) {
                Properties dbProperties = new Properties();

                dbProperties.load(fileInputStream);
                dbURL = dbProperties.getProperty("dbURL");
                dbUserName = dbProperties.getProperty("dbUserName");
                dbPassword = dbProperties.getProperty("dbPassword");
                dbDriver = dbProperties.getProperty("dbDriver");
            } else {
                throw new IOException("Error connecting to database.");
            }
    }

    public Connection getConnection() {
        try {
            loadDbProperties();
            Class.forName(dbDriver);

            Connection databaseConnection = DriverManager.getConnection(dbURL, dbUserName, dbPassword);
            return databaseConnection;

        } catch (Exception e) {
            System.out.println("Error connecting to database.");
        }

        return null;
    }
}