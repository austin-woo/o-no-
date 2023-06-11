
/** 
* @author A number tile just holds the number it displays on the screen, using a toString method
*/


public class NumberTile {
    private Integer number;

    /**
     * Contructor: Creates a new NumberTile of number num
     * 
     * @param num - integer tile will hold
     */
    public NumberTile(Integer num) {
        number = num;
    }

    /**
     * Contructor: Creates a new NumberTile with no number
     */
    public NumberTile() {
        number = null;
    }

    /**
     * Returns number as a string. Used to
     * display the number in Sudoku Grid
     * 
     * @return the number as a string
     */

    public String toString() {
        if (number == null) {
            return "";
        }
        return number + "";
    }

    /**
     * Returns the integer value
     * 
     * @return the number as an integer
     */
    public Integer getValue() {
        return number;
    }

}
