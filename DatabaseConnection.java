
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {

   
    private static final String DB_URL = "jdbc:mysql://localhost:3306/quizdb";
    String DatabaseName = "ResultDB"; 
    private static final String DB_USER = "root";   
    private static final String DB_PASSWORD = "Saqib12@#";

  
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

   
    public static void saveResult(String name, String regId, int score) {
        String query = "INSERT INTO results (name, reg_id, score) VALUES (?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, regId);
            stmt.setInt(3, score);
            stmt.executeUpdate();
            System.out.println("✅ Result saved to database.");
        } catch (SQLException e) {
            System.err.println("❌ Failed to save result: " + e.getMessage());
        }
    }
}
