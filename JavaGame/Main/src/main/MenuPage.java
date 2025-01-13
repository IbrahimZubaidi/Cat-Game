package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;


public class MenuPage implements ActionListener
{
    int WIDTH;
    int HEIGHT;
    int LABEL;
    int INPUT;
    int BUTTON;
    int id;
    
    JFrame frame = new JFrame("Menue Page");
    JLabel welcomeLabel = new JLabel("Welcome");
    JButton playButton = new JButton("Play");
    JButton leaderboardButton = new JButton("Leaderboard");
    JButton exitButton = new JButton("Exit");
    
    MenuPage(int user_id)
    {
        id = user_id;
        WIDTH = 1536 / 2;
        HEIGHT = 864 / 2;
        LABEL = 110;
        INPUT = 200;
        BUTTON = 200;
        
        Panel panel = new Panel("background.png");
        panel.setLayout(null);
        
        
        welcomeLabel.setBounds((WIDTH - LABEL) / 2, 100, 200, 25);
        welcomeLabel.setFont(new Font(null, Font.ITALIC, 25));
        playButton.setBounds((WIDTH - BUTTON) / 2, 150, 200, 25);
        playButton.setFocusable(false);
        playButton.addActionListener(this); 
        playButton.setBackground(Color.PINK);
        leaderboardButton.setBounds((WIDTH - BUTTON) / 2, 200, 200, 25);
        leaderboardButton.setFocusable(false);
        leaderboardButton.addActionListener(this); 
        leaderboardButton.setBackground(Color.PINK);
        exitButton.setBounds((WIDTH - BUTTON) / 2, 250, 200, 25);
        exitButton.setFocusable(false);
        exitButton.addActionListener(this);
        exitButton.setBackground(Color.PINK);
        
        panel.add(welcomeLabel);
        panel.add(playButton);
        panel.add(leaderboardButton);
        panel.add(exitButton);
        
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        frame.dispose();
        if(e.getSource()==playButton)
        {
            SwingUtilities.invokeLater(() -> new GamePage(id));
        }
        else if (e.getSource()==leaderboardButton)
        {
            LeaderboardPage leaderboardPage = new LeaderboardPage(id);
        }
        else
        {
            System.exit(0);
        }
    }
    
}
