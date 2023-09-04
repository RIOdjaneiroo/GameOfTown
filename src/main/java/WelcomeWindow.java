//package rio.ua.tenLection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeWindow extends JFrame {
    public WelcomeWindow() {
        setTitle("Вітаємо");
        setSize(400, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Font newFont = new Font("Arial", Font.PLAIN, 12);

        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JLabel welcomeLabel = new JLabel("Вітаємо вас у грі дитинства і всіх розумників");
        welcomeLabel.setFont(newFont); // Встановлюємо шрифт для мітки
        add(welcomeLabel, BorderLayout.CENTER);

        JButton startButton = new JButton("Далі");
        startButton.setFont(newFont); // Встановлюємо шрифт для кнопки
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                WindowGame gameWindow = new WindowGame(); // Створюємо новий об'єкт WindowGame
                gameWindow.setVisible(true); // Робимо його видимим
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

