package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SignupPage implements ActionListener
{
    int WIDTH;
    int HEIGHT;
    int LABEL;
    int INPUT;
    int BUTTON;
    
    JFrame frame = new JFrame("Signup Form");
    JLabel signupLabel = new JLabel("Signup From");
    JLabel usernameLabel = new JLabel("Username");
    JTextField usernameField = new JTextField();
    JLabel passwordLabel = new JLabel("Password");
    JPasswordField passwordField = new JPasswordField();
    JLabel tobesureLabel = new JLabel("To be sure");
    JPasswordField tobesureField = new JPasswordField();
    JButton signupButton = new JButton("Signup");
    JButton loginButton = new JButton("Lgoin");
    JLabel messageLabel = new JLabel();
    
    SignupPage()
    {
        WIDTH = 1536 / 2;
        HEIGHT = 864 / 2;
        LABEL = 75;
        INPUT = 200;
        BUTTON = 200;
    
        Panel panel = new Panel("background.png");
        panel.setLayout(null);
        
        signupLabel.setBounds((WIDTH - 150) / 2, 50, 250, 35);
        signupLabel.setFont(new Font(null, Font.ITALIC, 25));
        usernameLabel.setBounds(((WIDTH - INPUT) / 2) - LABEL, 100, 75, 25);
        passwordLabel.setBounds(((WIDTH - INPUT) / 2) - LABEL, 150, 75, 25);
        tobesureLabel.setBounds(((WIDTH - INPUT) / 2) - LABEL, 200, 75, 25);
        messageLabel.setBounds(((WIDTH - INPUT) / 2) - LABEL, 350, 250, 35);
        messageLabel.setFont(new Font(null, Font.ITALIC, 25));
        usernameField.setBounds((WIDTH - INPUT) / 2, 100, 200, 25);
        passwordField.setBounds((WIDTH - INPUT) / 2, 150, 200, 25);
        tobesureField.setBounds((WIDTH - INPUT) / 2, 200, 200, 25);
        signupButton.setBounds((WIDTH - BUTTON) / 2 , 250, 200, 25);
        signupButton.setFocusable(false);
        signupButton.addActionListener(this); 
        signupButton.setBackground(Color.PINK);
        loginButton.setBounds((WIDTH - (BUTTON / 2)) / 2, 300, 100, 25);
        loginButton.setFocusable(false);
        loginButton.addActionListener(this); 
        loginButton.setBackground(Color.PINK);
        
        panel.add(signupLabel);
        panel.add(usernameLabel);
        panel.add(passwordLabel);
        panel.add(tobesureLabel);
        panel.add(messageLabel);
        panel.add(usernameField);
        panel.add(passwordField);
        panel.add(tobesureField);
        panel.add(signupButton);
        panel.add(loginButton);
        
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==signupButton)
        {
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());
            String tobesure = String.valueOf(tobesureField.getPassword());
            
            if(!password.equals(tobesure))
            {
                messageLabel.setForeground(Color.red);
                messageLabel.setText("Passwords don't match");
            }
            else
            {
                String url = "jdbc:mysql://localhost:3306/game";
                try (Connection conn = DriverManager.getConnection(url, "root", "")) {
                    // Check if username already exists
                    String checkUserSQL = "SELECT * FROM users WHERE username = ?";
                    try (PreparedStatement pstmt = conn.prepareStatement(checkUserSQL)) 
                    {
                        pstmt.setString(1, username);
                        ResultSet rs = pstmt.executeQuery();

                        if (rs.next()) 
                        {
                            System.out.println("Username already exists. Try a different one.");
                        } 
                        else 
                        {
                            // Add new user
                            String insertSQL = "INSERT INTO users (username, password) VALUES (?, ?)";
                            try (PreparedStatement insertPstmt = conn.prepareStatement(insertSQL)) 
                            {
                                insertPstmt.setString(1, username);
                                insertPstmt.setString(2, password);
                                insertPstmt.executeUpdate();
                                System.out.println("Signup successful! You can now login.");
                                frame.dispose();
                                LoginPage loginPage = new LoginPage();
                            }
                        }
                    }
                } 
                catch (SQLException m) 
                {
                    System.out.println("Database error: " + m.getMessage());
                }
            }
        }
        else
        {
            frame.dispose();
            LoginPage loginPage = new LoginPage();
        }
    }
    
}
