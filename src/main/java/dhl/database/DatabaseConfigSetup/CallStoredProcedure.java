package dhl.database.DatabaseConfigSetup;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.Types;

public class CallStoredProcedure {
    private String storedProcedureName;
    private Connection connection;
    private CallableStatement statement;


    public CallStoredProcedure(String storedProcedureName) throws Exception {
        this.storedProcedureName = storedProcedureName;
        connection = null;
        statement = null;
        openConnection();
        createStatement();
    }

    private void createStatement() throws SQLException {
        statement = connection.prepareCall("{call " + storedProcedureName + "}");
    }

    private void openConnection() throws Exception {
        connection = DatabaseInitialize.instance().getConnection();
    }

    public void cleanup() {
        try {
            if (checkStatment()) {
                statement.close();
            }
            if (checkConnection()) {
                connection.close();
            }
        } catch (Exception e) {
            System.out.println(String.format("SQL Exception encountered"
                    + "while attempting to cleanup database connections"));
        }
    }

    public boolean checkStatment() throws SQLException {
        return !statement.isClosed();
    }

    public boolean checkConnection() throws SQLException {
        return !connection.isClosed();
    }

    public void setParameter(int paramIndex, String value) throws SQLException {
        statement.setString(paramIndex, value);
    }

    public void registerOutputParameterString(int paramIndex) throws SQLException {
        statement.registerOutParameter(paramIndex, java.sql.Types.VARCHAR);
    }

    public void registerOutputParameterBoolean(int paramIndex) throws SQLException {
        statement.registerOutParameter(paramIndex, java.sql.Types.BOOLEAN);
    }

    public void setParameter(int paramIndex, long value) throws SQLException {
        statement.setLong(paramIndex, value);
    }

    public void setParameter(int paramIndex, double value) throws SQLException {
        statement.setDouble(paramIndex, value);
    }

    public void setDate(int paramIndex, Date value) throws SQLException {
        statement.setDate(paramIndex, value);
    }

    public void setParameter(int paramIndex, Boolean value) throws SQLException {
        statement.setBoolean(paramIndex, value);
    }

    public void registerOutputParameterInt(int paramIndex) throws SQLException {
        statement.registerOutParameter(paramIndex, Types.INTEGER);
    }

    public void registerOutputParameterLong(int paramIndex) throws SQLException {
        statement.registerOutParameter(paramIndex, java.sql.Types.BIGINT);
    }

    public ResultSet executeWithResults() throws SQLException {
        if (statement.execute()) {
            return statement.getResultSet();
        }
        return null;
    }

    public ResultSet getMoreResults() throws SQLException {
        if (statement.getMoreResults()) {
            return statement.getResultSet();
        }
        return null;
    }

    public void execute() throws SQLException {
        statement.execute();
    }

    public void setParameter(int paramIndex, Date season_startDate) {
        try {
            statement.setDate(paramIndex, season_startDate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
