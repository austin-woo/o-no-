/**
 * @author A notes tile holds the numbers it displays as notes in a StringBuffer
 */

public class NotesTile extends NumberTile {
    private StringBuffer tile;

    /**
     * Contructor: Creates a new NotesTile, empty at first
     */
    public NotesTile() {
        super();
        tile = new StringBuffer();
    }

    /**
     * Adds the player input (int between 1 and 9) on to the StringBuffer
     * 
     * @param num is the player input
     */

    public void addValue(int num) {
        // Num must be from 1 to 9
        // Notes are small now

        String numStr = String.valueOf(num);
        if (tile.toString().length() == 0) {
            tile.append(numStr + "      "); // 6 spaces
        }

        if (!tile.toString().contains(numStr)) {
            if (tile.toString().stripTrailing().length() >= 1) {
                tile.setCharAt(tile.toString().stripTrailing().length(), numStr.charAt(0));

            }

        }
    }

    /**
     * toString method to print onto board
     * 
     * @return the tile as a string
     */
    public String toString() {
        return tile.toString();

    }

}
