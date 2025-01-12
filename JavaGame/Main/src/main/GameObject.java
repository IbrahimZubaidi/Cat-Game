package main;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GameObject {
    //attributes
    protected double positionX, positionY;
    protected double height;
    protected double width;
    protected double speedX, speedY;
    protected Rectangle hitbox;
    protected String spritePath;
    protected boolean isCollidable, isVisible; 

    //consructors
    public GameObject() {   }
    public GameObject(double positionX, double positionY, double width, double height, double speedX, double speedY, String spritePath, boolean isCollidable, boolean isVisible) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.width = width;
        this.height = height;
        this.speedX = speedX;
        this.speedY = speedY;
        this.hitbox = new Rectangle((int) positionX, (int) positionY, (int) width, (int) height);
        this.spritePath = spritePath;
        this.isCollidable = isCollidable;
        this.isVisible=isVisible;
      }
    
    //getters and setters 
    public double getPositionX() {
        return positionX;
      }
    public void setPositionX(double positionX) {
        this.positionX = positionX;
      }
    public double getPositionY() {
        return positionY;
      }
    public void setPositionY(double positionY) {
        this.positionY = positionY;
      }
    public double getWidth() {
        return width;
      }
    public void setWidth(double width) {
        this.width = width;
      }
    public double getHeight() {
        return height;
      } 
    public void setHeight(double height) {
        this.height = height;
      }
    public double getSpeedX() {
        return speedX;
      }
    public void setSpeedX(double speedX) {
        this.speedX = speedX;
      }
    public double getSpeedY() {
        return speedY;
      }
    public void setSpeedY(double speedY) {
        this.speedY = speedY;
      }
    public Rectangle getHitbox() {
        return hitbox; 
      }
    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox; 
      }
    public String getSpritePath() {
        return spritePath;
      }
    public void setSpritePath(String spritePath) {
        this.spritePath = spritePath;
      }
    public boolean isCollidable() {
        return isCollidable;
    }
    
    public boolean intersects(GameObject other) {
      return this.hitbox.intersects(other.hitbox);
  }
    public Image loadSprite() throws IOException {
        return ImageIO.read(new File(spritePath));
    }

    
}
