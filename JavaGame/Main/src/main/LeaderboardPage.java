package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class LeaderboardPage implements ActionListener 
{
    int WIDTH;
    int HEIGHT;
    int LABEL;
    int id;
    
    JFrame frame = new JFrame();
    JLabel LeaderboardLabel = new JLabel("Leaderboard");
    JButton BackButton = new JButton("Go Back");

    LeaderboardPage(int user_id)
    {
        id = user_id;
        WIDTH = 1536 / 2;
        HEIGHT = 864 / 2;
        LABEL = 105;
        
        Panel panel = new Panel("background.png");
        panel.setLayout(null);
        
        LeaderboardLabel.setBounds((WIDTH - 150) / 2, 50, 250, 35);
        LeaderboardLabel.setFont(new Font(null, Font.ITALIC, 25));
        BackButton.setBounds(((WIDTH - 700) / 2), 50, 100, 35);
        BackButton.setFocusable(false);
        BackButton.addActionListener(this);
        BackButton.setBackground(Color.PINK);
        
        panel.add(LeaderboardLabel);
        panel.add(BackButton);
        
        String url = "jdbc:mysql://localhost:3306/game";
        String sql = "SELECT users.username, leaderboard.score, leaderboard.lives FROM leaderboard JOIN users ON users.id = leaderboard.account_id ORDER BY leaderboard.score DESC LIMIT 5 ";
        List<String[]> rows = new ArrayList<>();
        
        try (Connection conn = DriverManager.getConnection(url, "root", "");
            Statement stmt = conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(sql);

            while(resultSet.next())  
            {
                String[] row = new String[resultSet.getMetaData().getColumnCount()];
                for (int i = 0; i < row.length; i++) 
                {
                    row[i] = resultSet.getString(i + 1);
                }
               rows.add(row);
            }
        }
        catch (SQLException e) 
        {
           e.printStackTrace();
        }
        
        String[] leaderboard = {"Rank", "Username", "Score"};
        if( rows.size() > 0)
        {
            int i = 0;
            for(String column : leaderboard)
            {
                JLabel Column = new JLabel(column);
                Column.setBounds(((WIDTH - 100) / 2) - 100 + (100 * i), 100, LABEL, 35);
                Column.setHorizontalAlignment(SwingConstants.CENTER);
                Column.setBorder(new LineBorder(Color.pink, 5));
                Column.setBackground(Color.WHITE);
                Column.setOpaque(true);
                panel.add(Column);
                i++;
            }
        }
        
        int i = 0;
        for (String[] row : rows) 
        {
            int j = 0;
            JLabel Rank = new JLabel(Integer.toString(i + 1));
            Rank.setBounds(((WIDTH - 100) / 2) - 100 + (j * 100), 100 + (32 * (i + 1)), LABEL, 35);
            Rank.setHorizontalAlignment(SwingConstants.CENTER);
            Rank.setBackground(Color.WHITE);
            Rank.setOpaque(true);
            Rank.setBorder(new LineBorder(Color.PINK, 5));
            panel.add(Rank);
            j++;
            for(String column : row)
            {
                if(column.equals("7"))
                {
                    continue;
                }
                JLabel Coulmn = new JLabel(column);
                Coulmn.setBounds(((WIDTH - 100) / 2) - 100 + (j * 100), 100 + (32 * (i + 1)), LABEL, 35);
                Coulmn.setHorizontalAlignment(SwingConstants.CENTER);
                Coulmn.setBackground(Color.WHITE);
                Coulmn.setOpaque(true);
                Coulmn.setBorder(new LineBorder(Color.PINK, 5));
                panel.add(Coulmn);
                j++;
            }
            i++; 
        }

        frame.add(panel);  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        frame.dispose();
        if(e.getSource() == BackButton)
        {
            MenuPage menuPage = new MenuPage(id);
        }
    }
}   