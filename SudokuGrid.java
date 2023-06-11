
/** 
* @author the sudoku grid is made up of several number tiles
*/

import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Location;
import java.util.*;

import javax.security.auth.x500.X500Principal;

public class SudokuGrid extends SudokuWorld<NumberTile> {

    private int[][] grid; // grid of user input
    //private int[][] ansGrid;
    private Stack<Location> incorrectTiles = new Stack<>();
    public static Stack<Location> moves;
    public static int attempts = 5; // max of 5 errors then grid revealed
    public static boolean notes;
    public static boolean gameRunning;
    public static int[][]arr; //2d array storing values in the sudoku grid

    /**
     * constructor: makes a grid of 9 by 9 number tiles, originally sets notes mode as off, 
     * sets sudGrid to be this grid, intiializes stack which will later store the player's moves
     * gives a welcome message at the top
     */
    public SudokuGrid() {
        super(new BoundedGrid<NumberTile>(9, 9));
        notes = false;
        sudGrid = this;
        moves = new Stack<>();

        // lines 41-61 is an easy test sudoku grid, comment out if you need
        /*
         * answer grid is:
         * 8 4 5 9 3 2 1 6 7
         * 7 3 1 4 8 6 5 9 2
         * 2 6 9 7 1 5 4 8 3
         * 5 7 2 6 9 3 8 1 4
         * 6 8 3 1 4 7 9 2 5
         * 1 9 4 5 2 8 7 3 6
         * 4 2 8 3 7 9 6 5 1
         * 3 5 7 8 6 1 2 4 9
         * 9 1 6 2 5 4 3 7 8
         * 
         */
        String[] arr = new String[] { " ", "4", " ", " ", "3", "2", " ", " ", " ",
                "7", "3", "1", " ", "8", "6", "5", "9", " ",
                "2", " ", "9", " ", "1", "5", "4", "8", "3",
                " ", "7", " ", " ", "9", " ", " ", " ", " ",
                " ", " ", " ", "1", "4", " ", "9", "2", " ",
                "1", " ", " ", "5", " ", " ", " ", "3", "6",
                " ", " ", " ", "3", " ", " ", "6", " ", " ",
                " ", "5", "7", " ", " ", " ", " ", "4", "9",
                "9", " ", "6", " ", "5", " ", " ", " ", " " };

        // int counter = 0;
        // for (int i = 0; i < 9; i++) {
        // for (int j = 0; j < 9; j++) {
        // if (arr[counter].equals(" ")) {
        // counter++;
        // } else {
        // add(new Location(i, j), new NumberTile(Integer.valueOf(arr[counter])));
        // counter++;
        // }
        // }
        // }

        setMessage(
                "Welcome to Sudoku! Select \"New Game\", Easy/Med/Hard, then press enter.\nNote: After pressing clear/undo button, press enter to show the change(s).");
    }

    /**
     * 
     * @param i is an integer value representing row
     * @param j is an integer value representing column
     * @param num is an integer representing the value at the row and column
     */
    public void addNumTwoD(int i, int j, int num) {
        grid[i][j] = num;
    }

    /**
     * 
     * @return a 2d array version of the sudoku grid
     */
    public int[][] convertToTwoD() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Location l = new Location(i, j);
                if (getGrid().get(l) == (null)) {
                    grid[i][j] = -1;
                } 
                else {
                    grid[i][j] = getGrid().get(l).getValue();
                }
            }
        }
        return grid;
    }

    /**
     * @param description stores value entered into tile
     * @param loc stores the location its clicked at
     * @return boolean value
     * checks if its a valid number that can be entered: says correct if it is, 
     * incorrect if it isn't, the number of tiles left, congrats if the grid is fully completed,
     * or the answer key if all attempts have been exhausted
     */
    public boolean keyPressed(String description, Location loc) {
        if (!gameRunning) {
            return true;
        }
        String numbers = "123456789";
        if (numbers.contains(description)) {
            if (!notes) {
                if (getGrid().get(loc) != null) {
                    if (getGrid().get(loc) instanceof NotesTile) {
                        remove(loc);
                    } 
                    else {
                        // setMessage("Tile is already occupied!");
                        return true;
                    }
                }
                if (isValidNumber(description, loc)) {
                    add(loc, new NumberTile(Integer.valueOf(description)));
                    if (gridIsFull()) {
                        setMessage("Congratulations for completing the game!\nSelect \"New Game\", level, and press enter to play again.");
                        getFrame().myControl.box.setEnabled(true);
                        getFrame().myControl.clearButton.setEnabled(false);
                        moves.clear();
                    } 
                    else {
                        setMessage("This number is valid according to the rules, but may not be the correct solution. Feel free to click undo. Click on another tile to continue.\nYou have " + attempts
                                + " attempt(s) left.");
                        moves.push(loc);
                        getFrame().myControl.clearButton.setEnabled(true);
                    }
                    getFrame().myControl.undoButton.setEnabled(true);
                    return true;
                }
                attempts--;
                incorrectTiles.add(loc);
                if (attempts <= 0) {
                    setMessage("You have exhausted all your attempts. Answer has been revealed.\nSelect \"New Game\", level, and press enter to play again.");
                    if (attempts == 0) {
                        add(loc, new NumberTile(Integer.valueOf(description)));
                        revealAnswer();
                        getFrame().myControl.undoButton.setEnabled(false);
                        getFrame().myControl.clearButton.setEnabled(false);
                        getFrame().myControl.notesButton.setEnabled(false);
                        getFrame().myControl.box.setEnabled(true);
                        notes = false;
                        gameRunning = false;
                        moves.clear();
                    }
                    attempts = 5;
                    return true;
                } 
                else {
                    setMessage(
                            "Incorrect value, you may try again by pushing undo and pressing enter.\nYou have "
                                    + attempts + " attempt(s) left.");
                    add(loc, new NumberTile(Integer.valueOf(description)));
                    moves.push(loc);
                }
                getFrame().myControl.undoButton.setEnabled(true);
                getFrame().myControl.clearButton.setEnabled(true);
            }
            // is notes mode
            else {
                if (attempts <= 0) {
                    setMessage("You have exhausted all your attempts. Answer will be revealed.");
                    getFrame().myControl.notesButton.setEnabled(false);
                } 
                else {
                    setMessage("You are now in notes mode. Attempts left: " + attempts);
                    if (getGrid().get(loc) == null) {
                        add(loc, new NotesTile());
                        moves.push(loc);
                        getFrame().myControl.undoButton.setEnabled(true);
                        getFrame().myControl.clearButton.setEnabled(true);
                    }
                    ((NotesTile) getGrid().get(loc)).addValue(Integer.valueOf(description));
                }
            }
        }
        return true;
    }


 
    
    /**
     * 
     * @param num is an integer representing value entered in the grid
     * @param loc is the location in the grid where the value is entered
     * @return a boolean: true if the valid can be placed there according to sudoku rules, 
     * false if it can't
     */
    public boolean isValidNumber(String num, Location loc) {
        int n = Integer.valueOf(num);
        // check that number occurs only once in the row
        for (int i = 0; i < 9; i++) {
            if (i != loc.getCol() && getGrid().get(new Location(loc.getRow(), i)) != null
                    && !(getGrid().get(new Location(loc.getRow(), i)) instanceof NotesTile) &&
                    getGrid().get(new Location(loc.getRow(), i)).getValue() == n) {
                return false;
            }
        }
        // check that number occurs only once in column
        for (int i = 0; i < 9; i++) {
            if (i != loc.getRow() && getGrid().get(new Location(i, loc.getCol())) != null
                    && !(getGrid().get(new Location(i, loc.getCol())) instanceof NotesTile)
                    && getGrid().get(new Location(i, loc.getCol())).getValue() == n) {
                return false;
            }

        }
        // // find the three by three grid and check if theres repeats in that grid
        // int[][] smallGrid = new int[3][3];
        // int c1 = 0; // colStart, colStart+1, colStart+2
        // int r1 = 0;
        // // r1, c1 r1, c1+1 r1,c1+2 
        // // r1+1,c1+1 r1+1,c1+1 r1+1, c1+2
        // // r1+2,c1+2 r1+2, c1+2. r1+2, c1+2

        // // finding r1
        // if(loc.getRow()%3==0) {
        // r1=loc.getRow();
        // }
        // else if(loc.getRow()%3==1) {
        // r1=loc.getRow()-1;
        // }
        // else {
        // r1=loc.getRow()-2;
        // }

        // //c1
        // if(loc.getCol()%3==0) {
        // r1=loc.getCol();
        // }
        // else if(loc.getCol()%3==1) {
        // r1=loc.getCol()-1;
        // }
        // else {
        // r1=loc.getCol()-2;
        // }

        // for (int i = r1; i < r1 + 3; i++) {
        // for (int j = c1; j < c1 + 3; j++) {
        // if (getGrid().get(new Location(i, j)) != null) {
        // if (getGrid().get(new Location(i, j)).getValue() != null) {
        // smallGrid[i - r1][j - c1] = getGrid().get(new Location(i, j)).getValue();
        // }
        // }
        // }
        // }

        // for (int i = 0; i < 3; i++) {
        // for (int j = 0; j < 3; j++) {
        // if ((i != loc.getRow() || j != loc.getCol()) && smallGrid[i][j] == n) {
        // return false;
        // }
        // }
        // }

        // return true;

        // other way

        int[] col = new int[3];
        if (loc.getCol() == 0 || loc.getCol() == 1 || loc.getCol() == 2) {
            col = new int[] { 0, 1, 2 };
        }
        if (loc.getCol() == 3 || loc.getCol() == 4 || loc.getCol() == 5) {
            col = new int[] { 3, 4, 5 };
        }
        if (loc.getCol() == 6 || loc.getCol() == 7 || loc.getCol() == 8) {
            col = new int[] { 6, 7, 8 };
        }

        int[] row = new int[3];
        if (loc.getRow() == 0 || loc.getRow() == 1 || loc.getRow() == 2) {
            row = new int[] { 0, 1, 2 };
        }
        if (loc.getRow() == 3 || loc.getRow() == 4 || loc.getRow() == 5) {
            row = new int[] { 3, 4, 5 };
        }
        if (loc.getRow() == 6 || loc.getRow() == 7 || loc.getRow() == 8) {
            row = new int[] { 6, 7, 8 };
        }

        for (int i = row[0]; i <= row[2]; i++) {
            for (int j = col[0]; j <= col[2]; j++) {
                if (getGrid().get(new Location(i, j)) != null
                        && !(getGrid().get(new Location(i, j)) instanceof NotesTile)
                        && getGrid().get(new Location(i, j)).getValue() == n) {
                    return false;
                }
            }
        }
        return true;
        

    }

    /**
     * reveals the original answer when all tries have been exhausted
     */
    public void revealAnswer() {
        // done
        GridGenerator.storeAnswer();
    }

    /**
     * 
     * @return a boolean: true if every tile is filled and the puzzle is complete,
     * false if it is still incomplete
     */
    public boolean gridIsFull() {
        for (int r = 0; r < getGrid().getNumRows(); r++) {
            for (int c = 0; c < getGrid().getNumCols(); c++) {
                Location loc = new Location(r, c);
                if (getGrid().get(loc) == null || getGrid().get(loc) instanceof NotesTile) {
                    return false;
                }
            }
        }
        if (!incorrectTiles.isEmpty()) {
            String msg = "The values at these locations are still incorrect: ";
            for (Location loc : incorrectTiles) {
                msg += loc + " ";
            }
            setMessage(msg);
            return false;
        }
        return true;
    }

}