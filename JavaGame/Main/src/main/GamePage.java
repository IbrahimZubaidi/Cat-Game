package main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class GamePage {

    JFrame frame = new JFrame("My Game");
    JButton b;
    Rectangle catHitbox;
    Coin[] coins;
    Image coinImage, cat, buildings, trees, ground, finish;
    int id, score = 0;
    int catX = 200, catY = 410;  
    int buildingsX = 0, buildingsY = 0;  
    int treesX = 0, treesY = 0;  
    int groundX = 0, groundY = 0; 
    int jumpHeight = 20, jumpProgress = 0;
    boolean isJumping = false,  moveLeft = false, moveRight = false;

    // Constructor
    GamePage(int user_id) {
        id = user_id;
        
        cat = new ImageIcon(getClass().getResource("/resources/cat.png")).getImage();
        buildings = new ImageIcon(getClass().getResource("/resources/buildings.png")).getImage();
        trees = new ImageIcon(getClass().getResource("/resources/trees.png")).getImage();
        ground = new ImageIcon(getClass().getResource("/resources/ground.png")).getImage();
        finish = new ImageIcon(getClass().getResource("/resources/finish.png")).getImage();
        coinImage = new ImageIcon(getClass().getResource("/resources/coin.png")).getImage();
        
        // Initialize coins
        coins = new Coin[20];

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

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                for(int i = -1; i < 5; i++)
                { 
                    g.drawImage(buildings, buildingsX + i * buildings.getWidth(null), buildingsY, buildings.getWidth(null), buildings.getHeight(null), null);  
                    g.drawImage(trees, treesX + i * trees.getWidth(null), treesY + 5, trees.getWidth(null), trees.getHeight(null), null);  
                    g.drawImage(ground, groundX + i * ground.getWidth(null), buildings.getHeight(null) - ground.getHeight(null), ground.getWidth(null), ground.getHeight(null), null);  
                }
                g.drawImage(finish, groundX + 5 * ground.getWidth(null), buildings.getHeight(null) - ground.getHeight(null), ground.getWidth(null), ground.getHeight(null), null);  
                
                // Draw
                for (Coin coin : coins) {
                    if (!coin.collected) 
                    { 
                        g.drawImage(coinImage, coin.x + groundX, coin.y, 50, 50, null);
                    }
                }

                g.drawImage(cat, catX, catY, 80, 75, null); 
                //g.setColor(Color.BLUE);
                //g.drawRect(catHitbox.x, catHitbox.y, catHitbox.width, catHitbox.height);
            }
        };
        
        panel.setPreferredSize(new java.awt.Dimension(buildings.getWidth(null), buildings.getHeight(null)));
        panel.setFocusable(true);

        updateCatHitbox();
        
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                // move left
                if (keyCode == KeyEvent.VK_A) {
                    if (!(groundX >= 0))
                    {
                        groundX += 10;
                        treesX += 5;
                        buildingsX += 3;
                        moveLeft = true; 
                    }
                // move right
                } else if (keyCode == KeyEvent.VK_D) {
                    if (!(groundX <= -(ground.getWidth(null) * 5)))
                    {
                        groundX -= 10;
                        treesX -= 5;
                        buildingsX -= 3;
                        moveRight = true;
                    }
                } 
                 else if (keyCode == KeyEvent.VK_SPACE && !isJumping) {
            isJumping = true;
            jumpProgress = 0;
            new Timer(0, new AbstractAction() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    if (moveLeft && !(groundX >= 0)) {
                        groundX += 3;
                        treesX += 2;
                        buildingsX += 1;
                    }
                    if (moveRight && !(groundX <= -(ground.getWidth(null) * 5))) {
                        groundX -= 3;
                        treesX -= 2;
                        buildingsX -= 1;
                    }
                    if (jumpProgress < jumpHeight) {
                        catY -= 5;
                    } else if (jumpProgress < jumpHeight * 2) {
                        catY += 5;
                    } else {
                        isJumping = false;  // End the jump
                        ((Timer) e.getSource()).stop();
                    }
                    jumpProgress++;
                    
                    updateCatHitbox();
                    checkCoinCollection();
                    checkWin(); 
                    panel.repaint();  // Only repaint the panel
                }
            }).start();
        }
        updateCatHitbox();
        checkCoinCollection();
        checkWin(); 
        panel.repaint();  // Only repaint the panel, not the entire frame
    }

            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_A) {
                    moveLeft = false;  
                } else if (keyCode == KeyEvent.VK_D) {
                    moveRight = false;  
                }
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(buildings.getWidth(null), buildings.getHeight(null) + 40);
        frame.setLocationRelativeTo(null);
        frame.add(panel);
        frame.setVisible(true);
        panel.requestFocusInWindow();
    }
    
    private void updateCatHitbox() {
        catHitbox = new Rectangle(catX, catY, 80, 75); // Match the cat's size
    }
    
    // Check if the cat collects a coin
    private void checkCoinCollection() {
        for (Coin coin : coins) {
            Rectangle coinHitbox = new Rectangle(coin.x + groundX, coin.y, 50, 50);
            if (!coin.collected && catHitbox.intersects(coinHitbox)) {
                coin.collected = true;
                score += 5;
                System.out.println("Coin collected! Score: " + score);
            }
        }
    }
    
    public void checkWin() {
        if (groundX <= -(ground.getWidth(null) * 5) ) 
        {
            GameLeaderboardManager manager = new GameLeaderboardManager();
            manager.checkAndUpdateScore(id, score);
            frame.dispose();
            MenuPage m = new MenuPage(id);   
        }
    }

}
