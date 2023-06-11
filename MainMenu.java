
import javax.swing.*;
import java.net.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.time.Duration;

public class MainMenu extends JFrame {

    private JPanel coloredBoxPanel;

    private static final Color COLOR_RED = new Color(255, 0, 0);
    private static final Color COLOR_BLUE = new Color(0, 0, 255);
    private static final Color COLOR_GREEN = new Color(0, 153, 0);
    private static final Color COLOR_ORANGE = new Color(255, 165, 0);


    public MainMenu() {
        setTitle("Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton calculusTetrisButton = createMenuButton("Calculus Tetris", COLOR_RED);
        calculusTetrisButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Tetris();
            }
        });
        panel.add(calculusTetrisButton, gbc);

        JButton sudokuButton = createMenuButton("Sudoku", COLOR_BLUE);
        sudokuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SudokuGrid gr = new SudokuGrid();
                gr.show();
            }
        });
        gbc.gridy = 1;
        panel.add(sudokuButton, gbc);

        JButton mathButton = createMenuButton("Arithmetic Bird", COLOR_GREEN);
        mathButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MathBird mathBird = new MathBird();
                mathBird.startGame(); 
            }
        });
        gbc.gridy = 2;
        panel.add(mathButton, gbc);

        JButton scratchButton = createMenuButton("Typing Game", COLOR_ORANGE);
        scratchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().browse(new URL("https://scratch.mit.edu/projects/863808264").toURI());
                } catch (Exception f) {}
            }
        });
        gbc.gridy = 3;
        panel.add(scratchButton, gbc);

        add(panel, BorderLayout.NORTH);

        coloredBoxPanel = new JPanel();
        coloredBoxPanel.setPreferredSize(new Dimension(200, 100));
        coloredBoxPanel.setVisible(false);
        add(coloredBoxPanel, BorderLayout.CENTER);
    }

    private JButton createMenuButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(button.getFont().deriveFont(14f));
        button.setFocusPainted(false);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(200, 30));
        return button;
    }

    private void showColoredBox(Color color) {
        coloredBoxPanel.setBackground(color);
        coloredBoxPanel.setVisible(true);
        repaint();
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainMenu mainMenu = new MainMenu();
                mainMenu.setVisible(true);
            }
        });
    }
}   