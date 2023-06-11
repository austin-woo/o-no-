import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MathBird extends JFrame {
    private Display dis;
    private JTextField textField;
    private Timer timer;

    public MathBird() {
        setTitle("Mathy Bird");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720);
        setResizable(false);

        dis = new Display();
        add(dis, BorderLayout.CENTER);

        textField = new JTextField(8);
        textField.setBounds(720, 600, 180, 60);
        textField.setFont(new Font("Serif", Font.PLAIN, 48));
        dis.add(textField);

        textField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (textField.getText().length() > 0)
                        dis.answer(textField.getText());
                    textField.setText("");
                }
            }
        });

        timer = new Timer(1000 / dis.GetFPS(), new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dis.repaint();
                if (dis.isDead()) {
                    int x = dis.getScore();
                }
            }
        });
    }

    public void startGame() {
        setVisible(true);
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MathBird mathBird = new MathBird();
                mathBird.startGame();
            }
        });
    }
}
