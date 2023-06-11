
/** 
*
* @author randomly generates a sudoku grid by manipulating from a set of already developed puzzles, removes
* a certain amount of numbers based on the level (removes most numbers for hard level, less numbers for medium level,
* and the least amount of numbers for easy level), stores answer key in a twod array (sudokugrid calls the method if 
* the player incorrectly places five numbers in the tiles)
*/

import info.gridworld.grid.Location;
import java.util.*;

public class GridGenerator

{
    private static SudokuGrid theGrid; // can access it by setting this variable equal to Sudoku.gr
    private static int[][] answer;

    /**
     * constructor: accesses the sudoku grid shown in the gui,
     */
    public GridGenerator() {
        theGrid = Sudoku.gr;
    }

    /**
     * generates a full 9 by 9 grid by randomly selecting one of the six grids and
     * randomly manipulating by
     * flipping or rotating, uploads it to the gui grid
     */
    public static void generate() {
        // try one - algorithm we chose not to sue
        /*
         * for (int i = 0; i < 3; i++)
         * {
         * int[][] mini = new int[3][3];
         * ArrayList<Integer> available = new ArrayList<Integer>();
         * for (int j = 0; j < 9; j++)
         * {
         * available.add(j + 1);
         * }
         * 
         * 
         * for (int j = 0; j < 3; j++)
         * {
         * for (int k = 0; k < 3; k++)
         * {
         * int n = (int)(Math.random() * available.size()); // generates
         * // index
         * // for
         * // available
         * // size
         * mini[j][k] = available.get(n);
         * available.remove(n);
         * }
         * }
         * updateDiagonals(i, mini);
         * }
         */

        // 2: easier method using already generated puzzles
        ArrayList<int[][]> puzzles = new ArrayList<int[][]>(); // arraylist
        // containing all
        // possible
        // puzzles to
        // choose from
        int[][] x1 = {
                { 4, 3, 5, 2, 6, 9, 7, 8, 1 },
                { 6, 8, 2, 5, 7, 1, 4, 9, 3 },
                { 1, 9, 7, 8, 3, 4, 5, 6, 2 },
                { 8, 2, 6, 1, 9, 5, 3, 4, 7 },
                { 3, 7, 4, 6, 8, 2, 9, 1, 5 },
                { 9, 5, 1, 7, 4, 3, 6, 2, 8 },
                { 5, 1, 9, 3, 2, 6, 8, 7, 4 },
                { 2, 4, 8, 9, 5, 7, 1, 3, 6 },
                { 7, 6, 3, 4, 1, 8, 2, 5, 9 } };

        int[][] x2 = {
                { 1, 5, 2, 4, 8, 9, 3, 7, 6 },
                { 7, 3, 9, 2, 5, 6, 8, 4, 1 },
                { 4, 6, 8, 3, 7, 1, 2, 9, 5 },
                { 3, 8, 7, 1, 2, 4, 6, 5, 9 },
                { 5, 9, 1, 7, 6, 3, 4, 2, 8 },
                { 2, 4, 6, 8, 9, 5, 7, 1, 3 },
                { 9, 1, 4, 6, 3, 7, 5, 8, 2 },
                { 6, 2, 5, 9, 4, 8, 1, 3, 7 },
                { 8, 7, 3, 5, 1, 2, 9, 6, 4 } };

        int[][] x3 = {
                { 1, 2, 3, 6, 7, 8, 9, 4, 5 },
                { 5, 8, 4, 2, 3, 9, 7, 6, 1 },
                { 9, 6, 7, 1, 4, 5, 3, 2, 8 },
                { 3, 7, 2, 4, 6, 1, 5, 8, 9 },
                { 6, 9, 1, 5, 8, 3, 2, 7, 4 },
                { 4, 5, 8, 7, 9, 2, 6, 1, 3 },
                { 8, 3, 6, 9, 2, 4, 1, 5, 7 },
                { 2, 1, 9, 8, 5, 7, 4, 3, 6 },
                { 7, 4, 5, 3, 1, 6, 8, 9, 2 } };

        int[][] x4 = {
                { 5, 8, 1, 6, 7, 2, 4, 3, 9 },
                { 7, 9, 2, 8, 4, 3, 6, 5, 1 },
                { 3, 6, 4, 5, 9, 1, 7, 8, 2 },
                { 4, 3, 8, 9, 5, 7, 2, 1, 6 },
                { 2, 5, 6, 1, 8, 4, 9, 7, 3 },
                { 1, 7, 9, 3, 2, 6, 8, 4, 5 },
                { 8, 4, 5, 2, 1, 9, 3, 6, 7 },
                { 9, 1, 3, 7, 6, 8, 5, 2, 4 },
                { 6, 2, 7, 4, 3, 5, 1, 9, 8 }
        };

        int[][] x5 = {
                { 2, 7, 6, 3, 1, 4, 9, 5, 8 },
                { 8, 5, 4, 9, 6, 2, 7, 1, 3 },
                { 9, 1, 3, 8, 7, 5, 2, 6, 4 },
                { 4, 6, 8, 1, 2, 7, 3, 9, 5 },
                { 5, 9, 7, 4, 3, 8, 6, 2, 1 },
                { 1, 3, 2, 5, 9, 6, 4, 8, 7 },
                { 3, 2, 5, 7, 8, 9, 1, 4, 6 },
                { 6, 4, 1, 2, 5, 3, 8, 7, 9 },
                { 7, 8, 9, 6, 4, 1, 5, 3, 2 }
        };

        int[][] x6 = {
                { 1, 2, 6, 4, 3, 7, 9, 5, 8 },
                { 8, 9, 5, 6, 2, 1, 4, 7, 3 },
                { 3, 7, 4, 9, 8, 5, 1, 2, 6 },
                { 4, 5, 7, 1, 9, 3, 8, 6, 2 },
                { 9, 8, 3, 2, 4, 6, 5, 1, 7 },
                { 6, 1, 2, 5, 7, 8, 3, 9, 4 },
                { 2, 6, 9, 3, 1, 4, 7, 8, 5 },
                { 5, 4, 8, 7, 6, 9, 2, 3, 1 },
                { 7, 3, 1, 8, 5, 2, 6, 4, 9 }
        };

        puzzles.add(x1);
        puzzles.add(x2);
        puzzles.add(x3);
        puzzles.add(x4);
        puzzles.add(x5);
        puzzles.add(x6);

        int i = (int) (Math.random() * puzzles.size());// random number to pick which puzzle
        int[][] twoD = puzzles.get(i);
        int manipulate = (int) (Math.random() * 6);
        if (manipulate == 0) {
            twoD = flipboxeshorizontal(puzzles.get(i));
        }
        if (manipulate == 1) {
            twoD = flipboxesvertical(puzzles.get(i));
        }
        if (manipulate == 2) {
            twoD = fliprow(puzzles.get(i));
        }
        if (manipulate == 3) {
            twoD = flipcol(puzzles.get(i));
        }
        if (manipulate == 4) {
            twoD = rotateninety(puzzles.get(i));
        }
        answer = twoD;
        uploadPuzzle(twoD);
    }

    /**
     * stores the original answer before numbers are removed and uploads it to the
     * gui
     * this method is called after the player makes five wrong moves
     */
    public static void storeAnswer() {
        uploadPuzzle(answer);
        theGrid.setMessage("Congrats you've completed the puzzle!");
    }

    public static SudokuGrid giveAnswer() {
        SudokuGrid x=new SudokuGrid();

        for(int i=0; i<9; i++) {
            for(int j=0; j<9; j++) {
                x.add(new Location(i,j), new NumberTile(answer[i][j]));
            }
        }
        return x;
    }

    /**
     * 
     * @param arr is a 9 by 9 2d array that is
     *            uploaded onto the sudoku grid and made visible
     */
    public static void uploadPuzzle(int[][] arr) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Sudoku.gr.add(new Location(i, j), new NumberTile(arr[i][j]));
            }
        }
    }

    /**
     * 
     * @param type is a string speciiying the level (easy, medium, or hard)
     *             this method removes random numbers from the board
     *             easy level removes roughly 30% of the board
     *             medium level removes roughly 50% of the baord
     *             hard level removes roughly 70% of the board
     */
    public static void removeNumbers(String type) {
        ArrayList<String> removed = new ArrayList<String>();
        int y = 0;
        if (type.equals("easy")) {
            y = (int) (0.3 * 81);
        } else if (type.equals("medium")) {
            y = (int) (0.5 * 81);
        } else {
            y = (int) (0.7 * 81);
        }
        System.out.println("going into remove");
        for (int i = 0; i < y; i++) {

            while (true) {

                int r = (int) (Math.random() * 9);
                int c = (int) (Math.random() * 9);

                if (!removed.contains(String.valueOf(r + "" + c))) {
                    removed.add(String.valueOf(r + "" + c));
                    Sudoku.gr.remove(new Location(r, c));
                    System.out.println("removed");
                    break;
                }
            }

        }

    }

    /*
     * past algorithm that we're not using anymore
     * //which var corresponds to i : to keep track of which part of the grid is
     * being updated
     * public static void updateDiagonals(int which, int[][]twoD) {
     * int startR=0;
     * int startC=0;
     * 
     * if(which==1) {
     * startR=3;
     * startC=3;
     * }
     * else {
     * startR=6;
     * startC=6;
     * }
     * for(int i=0; i<3; i++) {
     * for(int j=0; j<3; j++) {
     * int r=startR+i;
     * int c=startC+j;
     * Sudoku.gr.add(new Location(r,c), new NumberTile(twoD[i][j]));
     * }
     * }
     * }
     */

    /**
     * 
     * @param arr is a 2d array
     * @return a version of the 2d array that moves bottom 3 rows to top
     */
    public static int[][] flipboxeshorizontal(int[][] arr) {
        int[][] newarr = new int[9][9];

        for (int i = 3; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                newarr[i - 3][j] = arr[i][j];
            }
        }

        for (int i = 6; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                newarr[i][j] = arr[i - 6][j];
            }
        }
        return newarr;
    }

    /**
     * @param arr is a 2d array
     * @return a version of the 2d that moves first 3 cols to end
     */
    public static int[][] flipboxesvertical(int[][] arr) {
        int[][] newarr = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 6; j++) {
                newarr[i][j] = arr[i][j + 3];

            }
        }

        for (int i = 0; i < 9; i++) {
            newarr[i][6] = arr[i][0];
        }
        for (int i = 0; i < 9; i++) {
            newarr[i][7] = arr[i][1];
        }
        for (int i = 0; i < 9; i++) {
            newarr[i][8] = arr[i][2];
        }

        return newarr;

    }

    /**
     * 
     * @param arr is a 2d array
     * @return is a 2d array that has the 1st, 3rd rows flipped, 4th and 6th
     *         flipped, and 7th and 9th flipped
     */
    public static int[][] fliprow(int[][] arr) {
        int[][] newarr = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                newarr[i][j] = arr[i][j];
            }
        }

        for (int i = 0; i < 9; i++) {
            newarr[0][i] = arr[2][i];
        }
        for (int i = 0; i < 9; i++) {
            newarr[2][i] = arr[0][i];
        }

        for (int i = 0; i < 9; i++) {
            newarr[3][i] = arr[5][i];
        }
        for (int i = 0; i < 9; i++) {
            newarr[5][i] = arr[3][i];
        }

        for (int i = 0; i < 9; i++) {
            newarr[6][i] = arr[8][i];
        }
        for (int i = 0; i < 9; i++) {
            newarr[8][i] = arr[6][i];
        }
        return newarr;
    }

    /**
     * 
     * @param arr is a 2d array
     * @return a version of the 2d array that flips the 1st and 3rd columns, 4th and
     *         6th columns, and the 7th and 9th columns
     */
    public static int[][] flipcol(int[][] arr) {

        int[][] newarr = new int[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                newarr[i][j] = arr[i][j];
            }
        }

        for (int i = 0; i < 9; i++) {
            newarr[i][0] = arr[i][2];
        }
        for (int i = 0; i < 9; i++) {
            newarr[i][2] = arr[i][0];
        }

        for (int i = 0; i < 9; i++) {
            newarr[i][3] = arr[i][5];
        }
        for (int i = 0; i < 9; i++) {
            newarr[i][5] = arr[i][3];
        }

        for (int i = 0; i < 9; i++) {
            newarr[i][6] = arr[i][8];
        }
        for (int i = 0; i < 9; i++) {
            newarr[i][8] = arr[i][6];
        }
        return newarr;

    }

    /**
     * @param arr is a 2d array
     * @return a version of the 2d array that is rotated 90 degrees clockwise
     */
    public static int[][] rotateninety(int[][] arr) {
        int[][] newarr = new int[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                newarr[j][9 - 1 - i] = arr[i][j];
            }
        }
        return newarr;
    }

    /**
     * Method to get the grid
     * 
     * @return theGrid variable
     */
    public SudokuGrid getGrid() {
        return theGrid;
    }

}
