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

public class LoginPage implements ActionListener
{
    int WIDTH;
    int HEIGHT;
    int LABEL;
    int INPUT;
    int BUTTON;
    
    JFrame frame = new JFrame();
    
    JLabel LoginLabel = new JLabel("Login From");
    JLabel welcomeLabel = new JLabel("Welcome");
    JLabel usernameLabel = new JLabel("Username");
    JTextField usernameField = new JTextField();
    
    JLabel passwordLabel = new JLabel("Password");
    JPasswordField passwordField = new JPasswordField();
    
    JButton loginButton = new JButton("Login");
    JButton signupButton = new JButton("Signup");
    JLabel messageLabel = new JLabel();

    LoginPage()
    {
        WIDTH = 1536 / 2;
        HEIGHT = 864 / 2;
        LABEL = 75;
        INPUT = 200;
        BUTTON = 200;
        
        Panel panel = new Panel("background.png");
        panel.setLayout(null);
        
        LoginLabel.setBounds((WIDTH - 150) / 2, 50, 250, 35);
        LoginLabel.setFont(new Font(null, Font.ITALIC, 25));
        usernameLabel.setBounds(((WIDTH - INPUT) / 2) - LABEL, 100, LABEL, 25);
        passwordLabel.setBounds(((WIDTH - INPUT) / 2) - LABEL, 150, LABEL, 25);
        messageLabel.setBounds(((WIDTH - INPUT) / 2) - LABEL, 300, 250, 35);
        messageLabel.setFont(new Font(null, Font.ITALIC, 25));
        usernameField.setBounds((WIDTH - INPUT) / 2, 100, INPUT, 25);
        passwordField.setBounds((WIDTH - INPUT) / 2, 150, INPUT, 25);
        loginButton.setBounds((WIDTH - BUTTON) / 2, 200, BUTTON, 25);
        loginButton.setFocusable(false);
        loginButton.addActionListener(this);
        loginButton.setBackground(Color.PINK);
        signupButton.setBounds((WIDTH - (BUTTON / 2)) / 2, 250, BUTTON/2, 25);
        signupButton.setFocusable(false);
        signupButton.addActionListener(this);
        signupButton.setBackground(Color.PINK);
        
        panel.add(LoginLabel);
        panel.add(usernameLabel);
        panel.add(passwordLabel);
        panel.add(messageLabel);
        panel.add(usernameField);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(signupButton);
        
        frame.add(panel);  
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == loginButton)
        {
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());
            
            String url = "jdbc:mysql://localhost:3306/game";
            try (Connection conn = DriverManager.getConnection(url, "root", "")) 
            {
                String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) 
                {
                    pstmt.setString(1, username);
                    pstmt.setString(2, password);
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) 
                    {
                        int id = rs.getInt("id"); 
                        
                        messageLabel.setForeground(Color.green);
                        messageLabel.setText("Login successful!");
                        // menu page
                        frame.dispose();
                        MenuPage menuPage = new MenuPage(id);
                    } 
                    else 
                    {
                        //  message (invalid)
                        messageLabel.setForeground(Color.red);
                        messageLabel.setText("Invalid username and/or password!");
                    }
                }
            } 
            catch (SQLException m) 
            {
                System.out.println("Database error: " + m.getMessage());
            } 
        }
        else
        {
            //signup
            frame.dispose();
            SignupPage signupPage = new SignupPage();
        }
    }
}