

//Dimensions: 720 tall, 1280 wide
import java.util.*;

public class Bird {
    private int x;
    private int y;
    private double yv;

    public Bird(int x, int y) {
        this.x = x;
        this.y = y;
        yv = 0;
    }

    // call Question class to see if math question is answered correctly
    public void flap() {
        yv = -3;
    }

    public void gravity() {
        y += yv;
        yv += 0.05;
        if (y > 720)
            y = 720;
        if (y < 0) {
            y = 0;
            yv = 0;
        }
    }

    public boolean rising() {
        return yv < -1;
    }

    public boolean falling() {
        return yv > 1;
    }

    public boolean hitGround() {
        return y >= 720;
    }

    public int[] getPosition() {
        int[] coordinates = new int[2];
        coordinates[0] = x;
        coordinates[1] = y;
        return (coordinates);
    }

}
