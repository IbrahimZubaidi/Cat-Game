package main;

import java.sql.*;

public class GameLeaderboardManager {

    private Connection connection;

    // Constructor that initializes the connection to the database
    public GameLeaderboardManager() 
    {
        try 
        {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/game", "root", "");
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }

    // Method to check and update the score
    public void checkAndUpdateScore(int id, int newScore) {
        try 
        {
            // Get the previous score from the database for the given user
            String query = "SELECT score FROM leaderboard WHERE account_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // If the user has a recorded score, check if the new score is higher
                int previousScore = rs.getInt("score");

                if (newScore > previousScore) {
                    // If the new score is higher, update it
                    updateScore(id, newScore);
                } else {
                    // If the new score is lower or the same, do nothing
                    System.out.println("New score is not higher than the previous score. No update made.");
                }
            } else {
                // If no previous score exists, insert the new score
                insertNewScore(id, newScore);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update the score in the database
    private void updateScore(int id, int newScore) {
        try {
            String query = "UPDATE leaderboard SET score = ? WHERE account_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, newScore);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Score updated successfully to: " + newScore);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to insert a new score in the database (if no score exists for the user)
    private void insertNewScore(int id, int newScore) {
        try {
            String query = "INSERT INTO leaderboard (account_id, score, lives) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.setInt(2, newScore);
            stmt.setInt(3, 7);
            stmt.executeUpdate();
            System.out.println("New score inserted: " + newScore);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}