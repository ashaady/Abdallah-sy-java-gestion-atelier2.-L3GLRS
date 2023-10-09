package gestion_atelier_db.repositories.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Mysql {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/gestion_atelier_couture";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public Connection connection;

    public Mysql() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found: " + e.getMessage());
        }
    }

    public int executeUpdate(String query, Object... params) {
        try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            setParameters(statement, params);
            int rowsAffected = statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
            return rowsAffected;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
            return 0;
        }
    }

    public ResultSet executeQuery(String query, Object... params) {
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            setParameters(statement, params);
            return statement.executeQuery();
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
            return null;
        }
    }

    private void setParameters(PreparedStatement statement, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            statement.setObject(i + 1, params[i]);
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}