import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeWindow extends JFrame {
    public WelcomeWindow() {
        setTitle("Welcome");
        setSize(400,100);//400 width and 100 height
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout());

        // Use a FlowLayout with horizontal alignment
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Adjust gap as needed
        // Add a JLabel with a welcome message
        JLabel welcomeLabel = new JLabel("Welcome in game");
        // welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        add(welcomeLabel, BorderLayout.CENTER);

        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the welcome window
                dispose();

                // Open the main game window
                // MainGameWindow mainGameWindow = new MainGameWindow();
                // mainGameWindow.setVisible(true);
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    public static void main(String[] args) {
        WelcomeWindow welcomeWindow = new WelcomeWindow();
        welcomeWindow.setVisible(true);

    }
}