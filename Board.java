//package tetris;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.util.*;

import javax.swing.JPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.awt.Color;
import java.awt.Font;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;





/**
 * Board class
 */
public class Board extends JPanel implements KeyListener {
    /** if key is pressed */private boolean isKeyPressed = false;/** if key is pressed */
    private boolean isUpdated = false;/** t or f depending whether is updated */
    private int width;/**width of board */
    private int height;/**height of board */
    private Block[][] board;/** 2d array for board */
    private Shape currentShape;/** current shape falling */
    private boolean gameOver;/**t or f depending on game over */
    private boolean remove;/** remove or not */
    private ArrayList<Shape> shapes = new ArrayList<>();/** arraylist of shapes */
    private Score s;/**score num */
    private boolean holdfilled = false;/**is shape being holded */
    private Shape shapeholded;/**which shape is holded */
    private int timesswitched;/**num times switched */
    private List<Shape> newShapeList = new ArrayList<>();
    /** if shape is harddropping */
    private boolean isHardDropping = false;/** if shape is harddropping */
    private int trailY = -1;/**amt to trail y */
    private Timer trailTimer;/**timer of trail */
    private final int TRAIL_DURATION = 300; // Adjust the duration as needed (in milliseconds)
    /** clip of line clearing */
    private Clip lineClearClip;/** clip of line clearing */
    private Clip hardDropClip;/** clip of hard drop */
    
/**
 * initializes board components: background, size, score
 */
    public Board() {
        this.width = 10;
        this.height = 20;
        setBackground(Color.WHITE);
        setLayout(new GridLayout(width, height));
        setSize(width,height);
        board = new Block[width][height];
        
        gameOver = false;
        newShapeList.add(Shape.getRandomShape());
        newShapeList.add(Shape.getRandomShape());
        newShapeList.add(Shape.getRandomShape());
        addNewShape();
        setFocusable(true);
        addKeyListener(this);
        s = new Score();
        // try {
        //     lineClearClip = AudioSystem.getClip();
        // //    lineClearClip.open(AudioSystem.getAudioInputStream(new File("line_clear_sound.mp3")));
    
        //     hardDropClip = AudioSystem.getClip();
        //     hardDropClip.open(AudioSystem.getAudioInputStream(new File("tetris\\harddrop.wav")));
        // } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
        //     e.printStackTrace();
        // }
    }

   
/**
 * @param g the graphics
 * paints tetris board: grids, score, color
 */
    @Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Block[][] blocks = getBoard();
        
        try {
            paintTitle(g2d);
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
          //  e.printStackTrace();
        }
        paintGrid(g2d);
        paintScore(g2d);
        paintNext(g2d);
        paintHold( g2d );
        
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                if (blocks[x][y] != null) {
                    g2d.setColor(blocks[x][y].getColor());
                    g2d.fillRect(x * 30+1, y * 30+1, 28, 28);

                }
            }
        }
    }

/**
 * creates grid pattern
 * @param g the graphics
 */
    public void paintGrid(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.LIGHT_GRAY);
        for(int r = 0; r <= width; r++)
        {
            g2d.drawLine(r*30,0,r*30,height*30);
            
        }
        for(int c = 0; c <= height; c++)
        {
            g2d.drawLine(0,c*30,width*30,c*30);
            
        }
    }
/**
 * creates score label on tetris board
 * @param g the graphics
 */
    public void paintScore(Graphics g) {
        if(g instanceof Graphics2D)
        {
            Graphics2D g2d = (Graphics2D)g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            g2d.setPaint(Color.LIGHT_GRAY);
            //int fontsize = 30;
            g2d.setFont(new Font("TimesRoman", Font.PLAIN, 30));
            g2d.drawRect(width*30 + 30, height*30, 200, 60); 
            g2d.drawString("Score: " + s.getScore(),width*30 + 50, height*30 + 40); 
            
            
        }
    }

/**
 * paints a given shape
 * @param g the graphics
 * @param shp shape that will be painted
 * @param x0 the x
 * @param y0 the y
 */
    public void paintShapeHere(Graphics g, Shape shp, int x0, int y0) {
        Graphics2D g2d = (Graphics2D) g;
        

        for (Block block : shp.getBlocksOriginal()) 
        {
            int x = block.getX();
            int y = block.getY();
            g2d.setColor(block.getColor());
            g2d.fillRect(x*30+x0+30, y*30+y0, 28, 28);
                    
        }            
    }
    /**
     * displays the tetris piece that is put on hold by the player
     * @param g the graphics
     */
    public void paintHold(Graphics g) {
        int yoffset = 300;
        int xoffset = width*30+30;
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.LIGHT_GRAY);

        g2d.drawRect(xoffset, 5 + yoffset, 200, 120); 
            
        if ( shapeholded != null )
        {
            paintShapeHere(g, shapeholded, xoffset, yoffset+25);
        }
            
            
        
    }

    /**
     * displays the next three tetris pieces
     * @param g the graphics
     */
    public void paintNext(Graphics g) {
    
        int yoffset = 30;
        int xoffset = width*30+30;
        Graphics2D g2d = (Graphics2D)g;
            
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawRect(xoffset, 0 + yoffset-30, 200, 300); 

        for (int i=0; i<3; i++) {
            paintShapeHere(g, newShapeList.get(i), xoffset, yoffset + 90*i);
        }            
        
    }
/**
 * creates title label on tetris board
 * @param g the graphics
 * @throws Exception for the inputstream
 */
    public void paintTitle(Graphics g) throws Exception{
        //Path fName = Paths.get("tetris\\modern-tetris.ttf");
        //Graphics2D g2d = (Graphics2D)g;
        //g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        InputStream is = new BufferedInputStream(new FileInputStream("tetris\\modern-tetris.ttf"));//new FileInputStream("tetris\\modern-tetris.ttf");
        Font font = Font.createFont(Font.TRUETYPE_FONT, is);
        Font newFont = font.deriveFont(20f);
        // System.out.println("ENTERED");
        g.setFont(font);
        g.drawString("TETRIS", 0 , 0 ); 
    }

/**f
 * drops the current piece
 */
    public void dropCurrentShape() {
        currentShape.move(0, 1);
        if (!isValidMove(currentShape)) {
            currentShape.move(0, -1);
            freezeShape(currentShape);
            addNewShape();
        }
    }
    /**
     * adds a new shape into the game
     */
    public void addNewShape() {
        currentShape = newShapeList.get(0);
        newShapeList.remove(0);
        newShapeList.add(Shape.getRandomShape());
        timesswitched = 0;
        currentShape.move(1 - currentShape.getPivotX(), 1 - currentShape.getPivotY()); // Update pivot point
        currentShape.move(0, 1);
        if (!isValidMove(currentShape)) 
        {
            currentShape.move(0, -1);
            freezeShape(currentShape);
            //repaint();
            gameOver = true;
        } 
        else
        {
            currentShape.move(0, -1);            
        }
        shapes.add(currentShape);
    }

/**
 * keeps a piece on hold when triggered by user
 */
    public void holdShape(){
        //holdshape = current
        if(timesswitched==1)
        {
            return;
        }
        if (shapeholded != null)
        {
            Shape temp = currentShape;
            if (isValidMove(shapeholded))
            {
                currentShape = new Shape(shapeholded);
                shapeholded = new Shape(temp);
    
            }
            else
            {
                boolean junkit = isValidMove(shapeholded);
             //   System.out.println(junkit);
                return;
            }
            //currentShape.move(-currentShape.getX(),-currentShape.getY());
          //  System.out.println("swithced1");
        }
        else
        {
            shapeholded  = new Shape(currentShape);
            addNewShape();
        //    System.out.println("swithced0");
        }
 
        
        holdfilled = !holdfilled;
        timesswitched++;
    //    System.out.println(timesswitched);
    }

    /**
     * moves shape left by one unit
     */
    public void moveShapeLeft() {
        currentShape.move(-1, 0);
        if (!isValidMove(currentShape)) {
            currentShape.move(1, 0);
        }
    }
    /**
     * moves shape right by one unit
     */
    public void moveShapeRight() {
        currentShape.move(1, 0);
        if (!isValidMove(currentShape)) {
            currentShape.move(-1, 0);
        }
    }
    /**
     * rotates the piece clockwise
     */
    public void rotateShapeClockwise() {
        currentShape.rotateClockwise();
        if (!isValidMove(currentShape)) {
            currentShape.rotateCounterClockwise();
        }
    }
    /**
     * rotates the piece counterclockwise
     */
    public void rotateShapeCounterClockwise() {
        currentShape.rotateCounterClockwise();
        if (!isValidMove(currentShape)) {
            currentShape.rotateCounterClockwise();
        }
    }
    /**
     * hard drops the current piece, meaning it falls to the bottom immediately
     */
    public void hardDrop() {
        while (isValidMove(currentShape)) {
            currentShape.move(0, 1);
        }
        currentShape.move(0, -1); // Move the shape up by one to its last valid position
        
        dropCurrentShape();
        //s.incrementScore(); // Increment the score
        // hardDropClip.setFramePosition(3000); // Rewind the clip to the beginning
        // hardDropClip.start();
    }
    
    @Override
    /**
     * @param e the key press 
     */
    public void keyPressed(KeyEvent e) {
        if (isKeyPressed)    // wait till the key is released before proceeding
            return;
        isKeyPressed = true;
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                moveShapeLeft();
                break;
            case KeyEvent.VK_RIGHT:
                moveShapeRight();
                break;
            case KeyEvent.VK_UP:
                rotateShapeClockwise();
                break;
            case KeyEvent.VK_DOWN:
                dropCurrentShape();
                break;
            case KeyEvent.VK_C:
                holdShape();
                break;
            case KeyEvent.VK_SPACE:
                // TBD: FIXME - require an update between hard drops
                if (isUpdated)
                    hardDrop();
                isUpdated = false;
                break;
        }
        repaint();
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
    
    @Override
    public void keyReleased(KeyEvent e) 
    {
        isKeyPressed = false;
    }
    
    /**
     * freezes the current piece
     * @param shape that is frozen
     */
    private void freezeShape(Shape shape) {
        for (Block block : shape.getBlocks()) {
            int x = block.getX();
            int y = block.getY();
            if (x >= 0 && x < width && y >= 0 && y < height) {
                board[x][y] = block;
            }
        }
        clearLines();
    }
    
/**
 * checks if a piece can make a given move 
 * @param shape checks if it is valid move
 * @return whether move is valid 
 */
    public boolean isValidMove(Shape shape) {
        for (Block block : shape.getBlocks()) {
            int x = block.getX();
            int y = block.getY();
 
            if (x < 0 || x >= width || y < 0 || y >= height) {
                return false; // block is out of bounds
            }
            if (shape.getBlocks().isEmpty()) {
                return false; // the shape has no blocks
            }
           // if (y == 0) {
           //     System.out.println("Why is this an error?");
                //return false; // block is at the top of the board
           // }
            if (board[x][y] != null) {
                return false; // block is colliding with another block
            }
        }
        return true; // move is valid
    }
    /**
     * checks if a tetris line is filled and ready to be cleared
     */
    public void clearLines() {
        for (int y = height - 1; y >= 0; y--) {
            boolean lineCompleted = true;
            for (int x = 0; x < width; x++) {
                if (board[x][y] == null) {
                    lineCompleted = false;
                    break;
                }
            }
            if (lineCompleted) {
                removeLine(y);
                shiftBlocksDown(y);
                y++;
                s.incrementScore(10); // Increment the score
                // lineClearClip.setFramePosition(0); // Rewind the clip to the beginning
                // lineClearClip.start();
            }
        }
    }
    /**
     * clears a tetris line
     * @param lineIndex the index of the line to be removed
     */
    private void removeLine(int lineIndex) {
        remove = true;
        for (int x = 0; x < width; x++) {
            board[x][lineIndex] = null;
        }
        
        for (int y = lineIndex - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                Block block = board[x][y];
                if (block != null) {
                    board[x][y + 1] = block;
                    board[x][y] = null;
                }
            }
        }
    }

    /**
     * @return if remove is true or false
     */
    public boolean toDisplay()
    {
        return remove;
    }

    /**
     * changes remove to false
     */
    public void changeRemove()
    {
        remove = false;
    }
    
    /**
     * moves block down by one unit
     * @param lineIndex the index where lines should start moving down
     */
    private void shiftBlocksDown(int lineIndex) {
        for (int y = lineIndex - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                Block block = board[x][y];
                if (block != null) {
                    block.move(0, 1); // Move the block down by one
                    board[x][y + 1] = block;
                    board[x][y] = null;
                }
            }
        }
    }
    
    /**
     * 
     * @return whether game is over or not
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * updates game
     */
    public void update() {
        isUpdated = true;
        if (!gameOver) {
            dropCurrentShape();
                if (currentShape.getY() < 0) { 
                    gameOver = true;
                }
        }
    }
    
    /**
     * 
     * @return a copy of the board
     */
    public Block[][] getBoard() {
        Block[][] copy = new Block[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (board[x][y] != null) {
                    copy[x][y] = new Block(x, y, board[x][y].getColor());
                }
            }
        }
        for (Block block : currentShape.getBlocks()) {
            int x = block.getX();
            int y = block.getY();
            if (x >= 0 && x < width && y >= 0 && y < height) {
                copy[x][y] = new Block(x, y, block.getColor());
            }
        }
        return copy;
    }
    /**
     * calls score's increment method
     * @param num to increase score by
     */
    public void increaseScore(int num)
    {
        s.incrementScore(num);
    }
    
}

