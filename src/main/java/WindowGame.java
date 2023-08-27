import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowGame extends JFrame {
    public WindowGame() {
        setTitle("Game Window"); // налаштовуємо заголовок вікна
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // встановлюємо, що вікно буде закриватись при натисканні на "х"
        setSize(500, 400);   // встановлюємо розмір вікна
        setLocationRelativeTo(null);    //   по центру екрана
        setResizable(false);           // не змінюємо розмір вікна на екрані при запуску

        JPanel firstPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10)); // Вирівнювання по центру, відступи  40
        // створюємо панель для групування компонентів (перша панель)
        JTextField textField = new JTextField(15);  // встановлюємо висоту текстового поля
        Dimension textFieldSize = textField.getPreferredSize();
        textFieldSize.height = 30; // Змінюємо висоту textField
        textField.setPreferredSize(textFieldSize);
        JLabel cityLabel = new JLabel("enter the city"); // створюємо напис "enter the city"
        textField.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20)); // відступ справа для textField
        cityLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 100)); // відступ справа для cityLabel
        firstPanel.add(textField);  // додаємо текстове поле і напис на першу панель
        firstPanel.add(cityLabel);

        JPanel secondPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10)); // Вирівнювання по центру, відступи
        // створюємо другу панель для групування компонентів (кнопка та надпис)
        JLabel compLabel = new JLabel("computer say:");  // створюємо напис "computer say:"

        JButton submitButton = new JButton("make step");  // створюємо кнопку "make step" та додаємо обробник події
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the welcome window
                dispose();

                // Open the main game window
                // MainGameWindow mainGameWindow = new MainGameWindow();
                // mainGameWindow.setVisible(true);
            }
        });
        //submitButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10)); // Відступ справа для submitButton
        //compLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10)); // Відступ справа для compLabel


        secondPanel.add(submitButton);  // додаємо кнопку та надпис на другу панель
        secondPanel.add(compLabel);

        add(Box.createVerticalStrut(100)); // відступ від верху
        add(firstPanel);                      // додаємо першу панель до форми
        add(secondPanel);                     // додаємо другу панель чи до вікна
        add(Box.createVerticalStrut(150)); // Відступ від низу

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        // використовуємо BoxLayout для всього контейнера
        // встановлюємо менеджер розташування BoxLayout для всього вікна
    }

    public static void main(String[] args) {
        WindowGame windowGame = new WindowGame();
        windowGame.setVisible(true);
    }
}
