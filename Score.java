

/**
 * class Score which stores the current score of the game
 */
public class Score {
    private int s;
/**
 * initializes score
 */
    public Score() {
        this.s = 0;
    }
/**
 * 
 * @return score
 */
    public int getScore() {
        return s;
    }
/**
 * increases score
 * @param num s is incremented by
 */
    public void incrementScore(int num) {
        s += num;
    }
 
}