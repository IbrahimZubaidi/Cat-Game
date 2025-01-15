package main;

public class GameObject {
    
   public int x, y; 
   // Constructor
   public GameObject()
   {
       this.x = 0;
       this.y = 0;
   }
   public GameObject(int px, int py)
   {
       this.x = px;
       this.y = py;
   }
   // Setter
   public void setX(int px)
   {
       this.x = px;
   }
   public void setY(int py)
   {
       this.y = py;
   }
   
   // Getter
   public int getX(int px)
   {
       return this.x;
   }
   public int getY(int py)
   {
       return this.y;
   }
}
