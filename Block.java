//package tetris;
import java.awt.Color;

/**
 * block class
 */
public class Block {
    private int x;
    private int y;
    private Color color;

    /**
     * block constructor, sets color, x, and y to that of given block
     * @param b the block
     */
    public Block(Block b) {
        this.x = b.x;
        this.y = b.y;
        this.color = b.color;
    }
    
    /**
     * block constructor, sets color, x, and y from params
     * @param x coord
     * @param y coord
     * @param color of the block
     */
    public Block(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }
    
    /**
     * 
     * @return x value
     */
    public int getX() {
        return x;
    }
    
    /**
     * sets x to given value
     * @param x set to x
     */
    public void setX(int x) {
        this.x = x;
    }
    
    /**
     * 
     * @return y value
     */
    public int getY() {
        return y;
    }
    /**
     * sets y to given value
     * @param y sets to y
     */
    public void setY(int y) {
        this.y = y;
    }
    /**
     * 
     * @return color of block
     */
    public Color getColor() {
        return color;
    }
    /**
     * sets color to given color
     * @param color sets to color
     */
    public void setColor(Color color) {
        this.color = color;
    }
    /**
     * moves block by x and y
     * @param dx move by this
     * @param dy move by this
     */
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }
    
    
}
