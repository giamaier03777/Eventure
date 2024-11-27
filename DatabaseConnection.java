import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for managing the connection to a MySQL database.
 *
 * This class provides a method to establish a connection to the specified database
 * using the provided URL, username, and password. It is designed to centralize the
 * database connection logic and ensure consistent access throughout the application.
 */
public class DatabaseConnection {
    /**
     * The URL of the database, including protocol, host, port, and database name.
     * Format: jdbc:mysql://hostname:port/databaseName
     */
    private static final String URL = "jdbc:mysql://localhost:3306/your_database";

    /**
     * The username used to authenticate with the database.
     */
    private static final String USER = "your_user";

    /**
     * The password used to authenticate with the database.
     */
    private static final String PASSWORD = "your_password";

    /**
     * Establishes a connection to the database.
     *
     * @return a {@link Connection} object representing the active database connection.
     * @throws RuntimeException if there is an error connecting to the database.
     */
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }
}

