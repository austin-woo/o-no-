//package tetris;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;
import java.awt.Container;
import java.awt.Font;

/**
 * main class Tetris
 */
public class Tetris implements ActionListener {
    private Board board;
    private Timer timer;
    private JFrame frame;
    /** need to access in other classes like shape*/ public static boolean spicymode = true; /** need to access in other classes like shape*/ 

    /**
     * Initializes Tetris class
     */
    public Tetris() {
        openMainGame();
    }

    /**
     * creates the main game window
     */
    public void openMainGame() {
        board = new Board();
        timer = new Timer(1000, this);
        frame = new JFrame("Tetris");
        frame.add(board);
        
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(320 + 250, 640 + 100);
        frame.setVisible(true);
        timer.start();
    }

    /**
     * main method of Tetris class
     * @param args main argument
     */
    public static void main(String[] args) {
        new Tetris();
    }

    /**
     * Displays game over and restart signals, or calculus questions when triggered
     * @param e the action
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        board.update();
        board.repaint();
        if (board.isGameOver()) {
            timer.stop();
            JOptionPane.showMessageDialog(frame, "Game Over!");
            JButton restartb;
            restartb = new JButton("REPLAY");
            restartb.setBounds(5, 5, 100, 50);
        }
        String display;
        Calculus c = new Calculus();
        if (board.toDisplay() && spicymode) {
            int n = (int)(Math.random() * 2);
            if (n == 0) {
                display = c.intGenerator();
                JOptionPane.showMessageDialog(frame, display);
                var ans = JOptionPane.showInputDialog("Integral: " + display + "   What is your answer?");
                if (ans.trim().equals(c.getIntAns())) {
                    JOptionPane.showMessageDialog(frame, "Correct! The answer is: " + c.getIntAns());
                    board.increaseScore(10);
                    board.changeRemove();
                } else {
                    JOptionPane.showMessageDialog(frame, "Incorrect! The answer is: " + c.getIntAns());
                    board.increaseScore(1);
                    board.changeRemove();
                }
            } else {
                display = c.derivGenerator();
                JOptionPane.showMessageDialog(frame, display);
                var ans = JOptionPane.showInputDialog("Derivative: " + display + "   What is your answer?");
                if (ans.trim().equals(c.getDerivAns())) {
                    JOptionPane.showMessageDialog(frame, "Correct! The answer is: " + c.getDerivAns());
                    board.increaseScore(10);
                    board.changeRemove();
                } else {
                    JOptionPane.showMessageDialog(frame, "Incorrect! The answer is: " + c.getIntAns());
                    board.increaseScore(1);
                    board.changeRemove();
                }
            }
        }
    }
}
