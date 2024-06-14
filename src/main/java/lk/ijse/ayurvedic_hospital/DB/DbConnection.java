package lk.ijse.ayurvedic_hospital.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static DbConnection dbConnection;
    private final Connection connection;

    private DbConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Aruvedic_Hospital",
                    "root",
                    "Ijse@1234"
            );
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Failed to establish a database connection: " + e.getMessage());
        }
    }

    public static DbConnection getInstance() throws SQLException {
        if (dbConnection == null) {
            dbConnection = new DbConnection();
        }
        return dbConnection;
    }

    public Connection getConnection() {
        return connection;
    }
}
