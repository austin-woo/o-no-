import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu2 extends JFrame {
    public MainMenu2() {
        setTitle("Menu Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        // Create a panel to hold the menu options
        JPanel menuPanel = new JPanel(new GridLayout(4, 1));
        
        // Create the menu buttons
        JButton option1Button = new JButton("Option 1");
        JButton option2Button = new JButton("Option 2");
        JButton option3Button = new JButton("Option 3");
        JButton exitButton = new JButton("Exit");
        
        // Add the buttons to the panel
        menuPanel.add(option1Button);
        menuPanel.add(option2Button);
        menuPanel.add(option3Button);
        menuPanel.add(exitButton);
        
        // Add the panel to the frame
        add(menuPanel);

        // Register action listeners for the buttons
        option1Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //JOptionPane.showMessageDialog(null, "You selected Option 1");
                // Perform action for Option 1
                new Tetris();
            }
        });

        option2Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //JOptionPane.showMessageDialog(null, "You selected Option 2");
                // Perform action for Option 2
                SudokuGrid gr = new SudokuGrid();
                gr.show();
            }
        });

        option3Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //JOptionPane.showMessageDialog(null, "You selected Option 3");
                // Perform action for Option 3
                new MathBird();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainMenu2().setVisible(true);
            }
        });
    }
}