package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Cat extends GameObject implements ActionListener, KeyListener {
    //where to put the sprite
    int lives;
    int score;
    double movementSpeed=5.0;
     Cat (double positionX, double positionY, double width, double height, double speedX, double speedY, String spritePath, boolean isCollidable, boolean isVisible, int lives, int score){
        super();
        this.lives = 7;
        this.score = 0;
        this.lives = lives;
        this.score = score;
        this.isVisible=true;
    }
    //getter and setter
    public int getLives() 
    {
        return lives;
    }
    
    public void setLives(int lives) 
    {
        this.lives = lives;
    }
    
    public int getScores() 
    {
        return score;
    }
    
    public void setScores(int score) 
    {
        this.score = score;
    }

    public void collectCoin(Coin coin) {
        if (this.intersects(coin) && coin.isCollidable()) { 
            coin.isCollidable = false; // Make the coin non-collidable
            coin.isVisible = false;
        }
    }

    //move method
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode(); 

        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                speedX = -movementSpeed;
                break;
            case KeyEvent.VK_RIGHT:
                speedX = movementSpeed;
                break;
            case KeyEvent.VK_UP:
                speedY = -movementSpeed;
                break;
            case KeyEvent.VK_DOWN:
                speedY = movementSpeed;
                break;
            default:
                speedX = 0;
                speedY = 0;
        }
    }

    //for the following functions, these are written so that we don't get errors when using the implementation
    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }
    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    } 
}
