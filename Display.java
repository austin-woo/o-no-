
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;

public class Display extends JPanel {
    private final int FPS = 60;
    private int level = 1;
    private ArrayList<Integer> PipeX = new ArrayList<Integer>();
    private ArrayList<Integer> PipeY = new ArrayList<Integer>();
    private int score;
    private ArrayList<Question> q = new ArrayList<Question>();
    private int qi;
    private Image pipeL;
    private Image birdImg;
    private Image backGround;
    private Bird mathy;
    private int xv;
    private boolean alive = true;
    //private String name;
    private String x;


    // private Image pipeH;
    private int stoi(String a) {
        int ret = 0;
        int pow = 1;
        boolean neg = false;
        if (a.length() >= 1 && a.charAt(0) == '-') {
            a = a.substring(1);
            neg = true;
        }
        for (int i = a.length() - 1; i >= 0; i--) {
            ret += (int) (a.charAt(i) - '0') * pow;
            pow *= 10;
            if (a.charAt(i) > '9' || a.charAt(i) < '0')
                return -1000;
        }
        if (neg)
            ret = ret * -1;
        return ret;
    }

    public Display() {
        alive = true;
        mathy = new Bird(640, 360);
        //name = new String();
        q = Question.Bank(1);
        q.set(0, new Question(1, 1, '+'));
        qi = 0;
        newPipe(800, 280);
        for (int i = 1470; i < 800 + 1340; i += 670) {
            newPipe(i);
        }
        xv = 0;
        try {
            // pipeH = ImageIO.read(new File("78px-Pipe.png"));
            pipeL = ImageIO.read(new File("pipe_part.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            // pipeH = ImageIO.read(new File("78px-Pipe.png"));
            birdImg = ImageIO.read(new File("flap.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            backGround = ImageIO.read(new File("background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setSize(400, 400);
        setVisible(true);
    }

    public int GetFPS() {
        return FPS;
    }
    public boolean isDead(){
        return !alive;
    }
    public int getScore() {
        return score;
    }

    public void newPipe(int x) {
        PipeX.add(x);
        PipeY.add((int) (Math.random() * 400) + 160);
    }

    public void newPipe(int x, int y) {
        PipeX.add(x);
        PipeY.add(y);
    }

    public void setScore(int n) {
        score = n;
    }

    public Question curQuestion() {
        return q.get(qi);
    }
    public int level() {
        return level;
    }
    public void answer(String ans) {
        if (alive) {
                if (stoi(ans) >= 1 && stoi(ans) <= 3) {
                    level = stoi(ans);
                    q = Question.Bank(level);
                    xv = 2;
                    mathy.flap();
                    return;
                }
        }
        if (q.get(qi).isCorrect(stoi(ans))) {
            if (!alive) {
                alive = true;
                mathy = new Bird(640, 360);
                q.clear();
                q = Question.Bank(level);
                qi = 0;
                q.set(0, new Question(1, 1, '+'));
                PipeX.clear();
                PipeY.clear();
                newPipe(800, 280);
                for (int i = 1470; i < 800 + 1340; i += 670) {
                    newPipe(i);
                }
                xv = 0;
                score = 0;
                return;
            }
            mathy.flap();
        } else {
            if (score > 0)
                score--;
        }
        qi++;
        if (qi >= q.size()) {
            q = Question.Bank(level);
            qi = 0;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        if (xv != 0 || !alive)
            mathy.gravity();
        g.drawImage(backGround, 0, 0, 1280, 720, null);
        g.setColor(new Color(0, 64, 0));
        for (int i = 0; i < PipeX.size(); i++) {
            g.drawImage(pipeL, PipeX.get(i), 0, 60, PipeY.get(i) - 100, null);
            g.drawImage(pipeL, PipeX.get(i), PipeY.get(i) + 100, 60, 720, null);
            PipeX.set(i, PipeX.get(i) - xv);
            if (PipeX.get(i) <= -60) {
                PipeX.remove(i);
                PipeY.remove(i);
                i--;
                newPipe(1280);
            }
            if (PipeX.get(i) == 560) {
                score++;
            }
            if (PipeX.get(i) >= 550 && PipeX.get(i) <= 670) {
                if (mathy.getPosition()[1] > PipeY.get(i) + 80 || mathy.getPosition()[1] < PipeY.get(i) - 80) {
                    alive = false;
                    xv = 0;
                }
            }
        }
        if (mathy.hitGround()) {
            xv = 0;
            alive = false;
        }
        g.drawImage(birdImg, mathy.getPosition()[0] - 45, mathy.getPosition()[1] - 25, 90, 50, null);
        g.setColor(Color.PINK);
        g.fillRect(0, 0, 200, 70);
        g.setFont(new Font("Serif", Font.PLAIN, 30));
        g.setColor(Color.BLACK);
        g.drawString("score: " + score, 50, 50);
        g.setFont(new Font("Serif", Font.PLAIN, 48));
        g.setColor(Color.WHITE);
        g.fillRect(520, 600, 300, 60);
        g.setColor(Color.BLACK);
        
        if (alive && xv == 0) {
            g.drawString("Level 1, 2, or 3", 520, 650);
        } else {
            g.drawString(q.get(qi).toString(), 580, 650);
        }
        if (!alive) {
            g.setFont(new Font("Consolas", Font.PLAIN, 48));
            g.drawString("GAME OVER", 560, 300);
            g.drawString("Your score was: " + score, 400, 360);
            g.drawString("To restart, answer the current question", 120, 420);
            //name = new String();
            
        }
        
    }
    public void resetGameState() {
        alive = true;
        mathy = new Bird(640, 360);
        q.clear();
        q = Question.Bank(level);
        qi = 0;
        q.set(0, new Question(1, 1, '+'));
        PipeX.clear();
        PipeY.clear();
        newPipe(800, 280);
        for (int i = 1470; i < 800 + 1340; i += 670) {
            newPipe(i);
        }
        xv = 0;
        score = 0;
    }

}