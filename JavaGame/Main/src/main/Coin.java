package main;

public class Coin extends GameObject {
    //where to put the sprite
    final private int value = 5;
    int x, y;
    protected boolean collected;

    //constructor
    public Coin(int x, int y) 
    { 
        super();
        this.x = x;
        this.y = y;
        this.collected= false;
    }
    public int getValue()
    {
        return value;
    }
}