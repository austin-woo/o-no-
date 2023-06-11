
/** 
*
* @author This is the runner class, displays grid to the player. Other classes control what happen on the grid.
*/

public class Sudoku{

    /**
     * This is the SudokuGrid that our program will run. Other
     * classes need to access the grid to modify values in the 
     * grid. 
     */
    public static SudokuGrid gr = new SudokuGrid();

    /**
     * Runs the game
     * 
     * @param args - not used
     */
    public static void main(String[] args) {
        gr.show();

        // GridGenerator x = new GridGenerator();
        /*
         * //gird generator object
         * int[][] grid=objectr.generate(); //be the 2d array
         * EasyGrid x = remove(grid);
         

        /**
         * when generating: make sure to set grid to be the reference returned in remove
         */
        
    }
}
