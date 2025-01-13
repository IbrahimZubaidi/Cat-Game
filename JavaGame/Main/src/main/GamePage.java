package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class GamePage{
    
    JFrame frame = new JFrame("Purrfect");
    JPanel panel;
    JButton back;
    Rectangle catHitbox;
    Cat cat;
    Coin[] coins;
    Background background;
    int x = 0, y = 0;
    boolean finished = false;
    
    int id, score = 0, jumpHeight = 10, jumpProgress = 0; 
    boolean isJumping = false, moveLeft = false, moveRight = false;
    
    Image gameOver = new ImageIcon(getClass().getResource("/resources/gameOver.png")).getImage();

    // Constructor
    GamePage(int user_id) {
        
        id = user_id;
        
        cat = new Cat();
        coins = new Coin[20];
        background = new Background();
        

        setCoinLocations();

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                for(int i = -1; i < background.repeats; i++)
                { 
                    g.drawImage(background.background, background.backgroundX + i * background.width + 40, 0, background.width, background.height, null);  
                    g.drawImage(background.midground , background.midgroundX  + i * background.width + 40, 5, background.width, background.height, null);  
                    g.drawImage(background.foreground, background.foregroundX + i * background.width + 40, 0, background.width, background.height, null);  
                }
                    g.drawImage(background.finish, background.foregroundX + background.repeats * background.width + 40, 0, background.width, background.height, null);  
                for (Coin coin : coins) {
                    if (!coin.collected) 
                    { 
                        g.drawImage(coin.image, coin.x + background.foregroundX, coin.y, 50, 50, null);
                    }
                }

                g.drawImage(cat.image, cat.x, cat.y, cat.width, cat.height, null);
                g.drawImage(gameOver, 0, 0, x, y, null);
                
                
                // Hitbox
                //g.setColor(Color.RED);
                //g.drawRect(catHitbox.x, catHitbox.y, catHitbox.width, catHitbox.height);
            }
        };
        
        panel.setPreferredSize(new java.awt.Dimension(background.width, background.height));
        panel.setFocusable(true);
        updateCatHitbox();
        
        panel.addKeyListener(new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            // Move Left
            if (keyCode == KeyEvent.VK_A) {
                moveLeft = true;
                cat.image = new ImageIcon(getClass().getResource("/resources/runLeft.png")).getImage();
            }

            // Move Right
            if (keyCode == KeyEvent.VK_D) {
                moveRight = true;
                cat.image = new ImageIcon(getClass().getResource("/resources/runRight.png")).getImage();
            }

            // Start Jumping (only if not already jumping)
            if (keyCode == KeyEvent.VK_SPACE && !isJumping) {
                isJumping = true;
                jumpProgress = 0;
            }
            
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();

            // Stop Left Movement
            if (keyCode == KeyEvent.VK_A) {
                moveLeft = false;
                cat.image = new ImageIcon(getClass().getResource("/resources/catLeft.png")).getImage();
            }

            // Stop Right Movement
            if (keyCode == KeyEvent.VK_D) {
                moveRight = false;
                cat.image = new ImageIcon(getClass().getResource("/resources/catRight.png")).getImage();
            }
        }
    });
        
    // Game loop or Timer to update every frame
    new Timer(16, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Move Left
            if (moveLeft && background.foregroundX < 0 && !finished) {
                background.foregroundX += cat.vx / 1;
                background.midgroundX += cat.vx / 2;
                background.backgroundX += cat.vx / 4;
            }
            //Move Right
            if (moveRight && background.foregroundX > -(background.width * background.repeats) && !finished) {
                background.foregroundX -= cat.vx / 1;
                background.midgroundX -= cat.vx / 2;
                background.backgroundX -= cat.vx / 4;
            }
            // Handle Jumping
            if (isJumping && !finished) {
                if (jumpProgress < jumpHeight) 
                {
                    cat.y -= 10; // Jumping up
                } 
                else if (jumpProgress < jumpHeight * 2) 
                {
                    cat.y += 10; // Falling down
                } 
                else 
                {
                    isJumping = false;  // End the jump
                }
                jumpProgress++;
            }

            // Update hitbox, coin collection, etc.
            updateCatHitbox();
            checkCoinCollection();
            checkWin(); 
            panel.repaint();
        }
    }).start();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(background.width + 40, background.height + 40);
        frame.setLocationRelativeTo(null);
        frame.add(panel);
        frame.setVisible(true);
        panel.requestFocusInWindow();
    }
    
    // Update the cat's hitbox
    private void updateCatHitbox() {
        catHitbox = new Rectangle(cat.x, cat.y, 80, 75);
    }
    
    // Check if the cat collects a coin
    private void checkCoinCollection() {
        for (Coin coin : coins) {
            Rectangle coinHitbox = new Rectangle(coin.x + background.foregroundX, coin.y, 50, 50);
            if (!coin.collected && catHitbox.intersects(coinHitbox)) 
            {
                coin.collected = true;
                score += 5;
                System.out.println("Coin collected! Score: " + score);
            }
        }
    }
    
    public void checkWin() {
        if (background.foregroundX <= -(background.width * background.repeats))
        {
            finished = true;
            x = background.width + 40;
            y = background.height + 40;
            back = new JButton("Go Back");
            back.setBounds(20, 20, 100, 50);
            back.setFocusable(false);
            back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    GameLeaderboardManager manager = new GameLeaderboardManager();
                    manager.checkAndUpdateScore(id, score);
                    frame.dispose();
                    MenuPage m = new MenuPage(id);   
                }
            });
            
            panel.add(back); 
            
            back.setBackground(Color.PINK);
        }
    }
    
    public void setCoinLocations(){
        // First part: 5 coins
        for (int i = 0; i < 5; i++) {
            coins[i] = new Coin(600 + i * 40, 410); 
        }

        // Second part: 5 coins with a parabolic pattern
        for (int i = 5; i < 10; i++) {
            coins[i] = new Coin(1100 + i * 40, 310 + (i - 7) * (i - 7) * 5); // 6-10 
        }

        // Zigzag pattern for coins 10-14
        for (int i = 10; i < 15; i++) {
            if(i % 2 == 0)
                coins[i] = new Coin(1600 + i * 40, 430); 
            else
                coins[i] = new Coin(1600 + i * 40, 410); 
        }

        int baseX = 1600 + 14 * 40 + 200; 

        // Coins 15-19 heart
        coins[15] = new Coin(baseX, 390);
        coins[16] = new Coin(baseX + 10, 400); 
        coins[17] = new Coin(baseX + 20, 420); 
        coins[18] = new Coin(baseX + 30, 400); 
        coins[19] = new Coin(baseX + 40, 390);
    }
}
