package db_connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mariadb://localhost:3306/game_db";
    private static final String USER = "root";
    private static final String PASSWORD = "69420";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void insertUserId(int id) {
        String sql = "INSERT INTO users (id) VALUES (?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("User ID inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to insert user ID: " + e.getMessage());
        }
    }
}





