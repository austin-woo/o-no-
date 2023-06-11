//package tetris;
import java.awt.Color;
import java.util.*;


/**
 * Shape class
 */
public class Shape {
    private ArrayList<Block> blocks;
    private ArrayList<Block> blocksOriginal;
    private int pivotX;
    private int pivotY;
    private int orientation;
    private static boolean spicymode;
    private static final HashMap<String, Shape> shapeMap = new HashMap<>();
    
    static {
        shapeMap.put("S", getShapeS());
        shapeMap.put("Z", getShapeZ());
        shapeMap.put("L", getShapeL());
        shapeMap.put("J", getShapeJ());
        shapeMap.put("Square", getShapeSquare());
        shapeMap.put("I", getShapeI());
        shapeMap.put("T", getShapeT());
    }
    /**
     * constructor of shape class
     * @param blocks all the blocks
     * @param pivotX the pivot x of block
     * @param pivotY the pivot y of block
     */
    public Shape(ArrayList<Block> blocks, int pivotX, int pivotY) {
        this.pivotX = pivotX;
        this.pivotY = pivotY;
        this.orientation = 0;
    
        this.blocks = new ArrayList<>();
        this.blocks.add(new Block(blocks.get(0)));
        this.blocks.add(new Block(blocks.get(1)));
        this.blocks.add(new Block(blocks.get(2)));
        this.blocks.add(new Block(blocks.get(3)));
    
        blocksOriginal = new ArrayList<>();
        blocksOriginal.add(new Block(blocks.get(0)));
        blocksOriginal.add(new Block(blocks.get(1)));
        blocksOriginal.add(new Block(blocks.get(2)));
        blocksOriginal.add(new Block(blocks.get(3)));
    }

    /**
     * constructor of shape class
     * @param orig the original shape
     */
    public Shape(Shape orig) {
        this.pivotX = orig.pivotX;
        this.pivotY = orig.pivotY;
        this.orientation = orig.orientation;

        this.blocks = new ArrayList<>();
        this.blocks.add(new Block(orig.blocks.get(0)));
        this.blocks.add(new Block(orig.blocks.get(1)));
        this.blocks.add(new Block(orig.blocks.get(2)));
        this.blocks.add(new Block(orig.blocks.get(3)));
    
        blocksOriginal = new ArrayList<>();
        blocksOriginal.add(new Block(orig.blocksOriginal.get(0)));
        blocksOriginal.add(new Block(orig.blocksOriginal.get(1)));
        blocksOriginal.add(new Block(orig.blocksOriginal.get(2)));
        blocksOriginal.add(new Block(orig.blocksOriginal.get(3)));
    }

    /**
     * getter of y
     * @return y
     */
    public int getY() {
        int minY = Integer.MAX_VALUE;
        for (Block block : blocks) {
            minY = Math.min(minY, block.getY());
        }
        return minY;
    }

    /**
     * getter of x
     * @return x
     */
    public int getX() {
        int minX = Integer.MAX_VALUE;
        for (Block block : blocks) {
            minX = Math.min(minX, block.getX());
        }
        return minX;
    }
    
    /**
     * getter of arraylist of blocks
     * @return blocks arraylist
     */
    public ArrayList<Block> getBlocks() {
        return blocks;
    }
    

    /**
     * getter of arraylist of blocks aka blocks original
     * @return original blocks arraylist(used in next and hold)
     */
    public ArrayList<Block> getBlocksOriginal() {
        return blocksOriginal;
    }
    
    /**
     * getter of pivot x
     * @return pivot x
     */
    public int getPivotX() {
        return pivotX;
    }
    
    /**
     * getter of pivot y
     * @return pivot y
     */
    public int getPivotY() {
        return pivotY;
    }

    /**
     * getter of orientation
     * @return the orientation
     */
    public int getOrientation() {
        return orientation;
    }
    
    /**
     * makes the block rotate cw
     */
    public void rotateClockwise() {
        orientation = (orientation + 1) % 4;
        for (Block block : blocks) {
            int oldX = block.getX();
            int oldY = block.getY();
            int deltaX = oldX - pivotX;
            int deltaY = oldY - pivotY;
            block.setX(pivotX + deltaY);
            block.setY(pivotY - deltaX);
        }
    }
    
    /**
     * block moves by x and y
     * @param deltaX change by x
     * @param deltaY change by y
     */
    public void move(int deltaX, int deltaY) {
        pivotX += deltaX;
        pivotY += deltaY;
        for (Block block : blocks) {
            block.setX(block.getX() + deltaX);
            block.setY(block.getY() + deltaY);
        }
    }
    /**
     * makes the block rotate ccw
     */
    public void rotateCounterClockwise() {
        orientation = (orientation + 3) % 4;
        for (Block block : blocks) {
            int oldX = block.getX();
            int oldY = block.getY();
            int deltaX = oldX - pivotX;
            int deltaY = oldY - pivotY;
            block.setX(pivotX - deltaY);
            block.setY(pivotY + deltaX);
        }
    }
    /**
     * getter of a random shape
     * @return the random shape
     */
    public static Shape getRandomShape() {
        Random random = new Random();
        int index = random.nextInt(shapeMap.size());
        ArrayList<Shape> shapes = new ArrayList<>(shapeMap.values());
        return new Shape(shapes.get(index));
    }
    
    /**
     * getter of shape s
     * @return shape s
     */
    public static Shape getShapeS() {
        spicymode = Tetris.spicymode;
        Color bcolor;
        if (spicymode)
        {
            bcolor = new Color(136,8,8);//blood red
        }
        else
        {
            bcolor = Color.GREEN;
        }
        ArrayList<Block> blocks = new ArrayList<>();
        blocks.add(new Block(0, 0, bcolor));
        blocks.add(new Block(1, 0, bcolor));
        blocks.add(new Block(1, 1, bcolor));
        blocks.add(new Block(2, 1, bcolor));
        return new Shape(blocks, 1, 1);
    }
    
    /**
     * getter of shape z
     * @return shape z
     */
    public static Shape getShapeZ() {
        ArrayList<Block> blocks = new ArrayList<>();
        blocks.add(new Block(0, 1, Color.RED));
        blocks.add(new Block(1, 1, Color.RED));
        blocks.add(new Block(1, 0, Color.RED));
        blocks.add(new Block(2, 0, Color.RED));
        return new Shape(blocks, 1, 1);
    }
    
    /**
     * getter of shape l
     * @return shape l
     */
    public static Shape getShapeL() {
        spicymode = Tetris.spicymode;
        Color bcolor;
        if (spicymode)
        {
            bcolor = new Color(210,4,45);//cherry
        }
        else
        {
            bcolor = Color.ORANGE;
        }
        ArrayList<Block> blocks = new ArrayList<>();
        blocks.add(new Block(0, 1, bcolor));
        blocks.add(new Block(1, 1, bcolor));
        blocks.add(new Block(2, 1, bcolor));
        blocks.add(new Block(2, 0, bcolor));
        return new Shape(blocks, 1, 1);
    }
    
    /**
     * getter of shape j
     * @return shape j
     */
    public static Shape getShapeJ() {
        spicymode = Tetris.spicymode;
        Color bcolor;
        if (spicymode)
        {
            bcolor = new Color(227,66,52);//vermellion
        }
        else
        {
            bcolor = Color.BLUE;
        }
        ArrayList<Block> blocks = new ArrayList<>();
        blocks.add(new Block(0, 0, bcolor));
        blocks.add(new Block(0, 1, bcolor));
        blocks.add(new Block(1, 1, bcolor));
        blocks.add(new Block(2, 1, bcolor));
        return new Shape(blocks, 1, 1);
    }

    /**
     * getter of shape square
     * @return shape square
     */
    public static Shape getShapeSquare() {
        spicymode = Tetris.spicymode;
        Color bcolor;
        if (spicymode)
        {
            bcolor = new Color(250,95,85);//neonred
        }
        else
        {
            bcolor = Color.YELLOW;
        }
        ArrayList<Block> blocks = new ArrayList<>();
        blocks.add(new Block(0, 0, bcolor));
        blocks.add(new Block(0, 1, bcolor));
        blocks.add(new Block(1, 0, bcolor));
        blocks.add(new Block(1, 1, bcolor));
        return new Shape(blocks, 1, 1);
    }
    
    /**
     * getter of shape i
     * @return shape i
     */
    public static Shape getShapeI() {
        spicymode = Tetris.spicymode;
        Color bcolor;
        if (spicymode)
        {
            bcolor = new Color(250,128,114);//
        }
        else
        {
            bcolor = Color.CYAN;
        }
        ArrayList<Block> blocks = new ArrayList<Block>();
        blocks.add(new Block(1, 0, bcolor)); // center block
        blocks.add(new Block(0, 0, bcolor));
        blocks.add(new Block(2, 0, bcolor));
        blocks.add(new Block(3, 0, bcolor));
        return new Shape(blocks,1,1);
    }

    /**
     * getter of shape t
     * @return shape t
     */
    public static Shape getShapeT() {
        spicymode = Tetris.spicymode;
        Color bcolor;
        if (spicymode)
        {
            bcolor = new Color(170,74,68);//brick red
        }
        else
        {
            bcolor = Color.MAGENTA;
        }
        ArrayList<Block> blocks = new ArrayList<>();
        blocks.add(new Block(0, 1, bcolor));
        blocks.add(new Block(1, 0, bcolor));
        blocks.add(new Block(1, 1, bcolor));
        blocks.add(new Block(2, 1, bcolor));
        return new Shape(blocks, 1, 1);
    }

}
