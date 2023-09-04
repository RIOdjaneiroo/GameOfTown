import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class WindowGame extends JFrame {
    private JTextField cityTextField;
    private JButton makeMoveButton;
    private ServiceCity serviceCity;
    private LogicGame logicGame;
    private JLabel responseCompList;
    ImageIcon icon;

    public WindowGame() {
        setTitle("Гра в міста");   // налаштовуємо заголовок вікна
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // встановлюємо, що вікно буде закриватись при натисканні на "х"
        setSize(500, 400); // встановлюємо розмір вікна
        setLocationRelativeTo(null);   //   по центру екрана
        setResizable(false);           // не змінюємо розмір вікна на екрані при запуску
        icon = new ImageIcon("src/main/java/favicon.png");
        this.setIconImage(icon.getImage());

        JPanel firstPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10)); // Вирівнювання по центру, відступи  40
        // створюємо панель для групування компонентів (перша панель)
        cityTextField = new JTextField(15);                      // встановлюємо ширину текстового поля
        Dimension textFieldSize = cityTextField.getPreferredSize();    // створюэмо змінну типу інтервал
        textFieldSize.height = 30;                                    // змінюємо висоту textField
        cityTextField.setPreferredSize(textFieldSize);                //встановлюємо попередньо обчислені розміри
        JLabel cityLabel = new JLabel("введіть місто");          // створюємо напис "введіть місто"
        cityLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15)); // відступ справа для cityLabel
        cityTextField.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));  // відступ справа для textField
        firstPanel.add(cityTextField);                              // додаємо текстове поле і напис на першу панель
        firstPanel.add(cityLabel);

        JPanel secondPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 65, 10)); // Вирівнювання по центру, відступи
        // створюємо другу панель для групування компонентів (кнопка та надпис)
        JLabel compLabel = new JLabel("комп'ютор каже:");  // створюємо напис "комп'ютор каже:"

        makeMoveButton = new JButton("зробити хід");   // створюємо кнопку "зробити хід" та додаємо обробник події
        makeMoveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeMove();
            }
        });

        secondPanel.add(makeMoveButton);
        secondPanel.add(compLabel);

        JPanel thirdPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Вирівнювання по центру, відступи
        // створюємо третю панель для виводу процесу гри
        responseCompList = new JLabel("<html>привіт привіт привіт привіт привіт привіт привіт привіт привіт привіт привіт привіт привіт</html>");  // створюємо лейбл з вітанням
        responseCompList.setPreferredSize(new Dimension(400, 50)); // Встановлюємо розмір мітки
        responseCompList.setHorizontalAlignment(SwingConstants.LEFT); // Встановлюємо вирівнювання тексту зліва
        thirdPanel.add(responseCompList);

        //JScrollPane thirdPanelScrollPane = new JScrollPane(thirdPanel);
        //thirdPanelScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        //JLabel label = new JLabel("<html>привіт привіт привіт привіт привіт привіт привіт привіт привіт привіт привіт привіт привіт</html>");  // створюємо лейбл з вітанням
        //label.setPreferredSize(new Dimension(400, 70)); // Встановлюємо розмір мітки
        //thirdPanelScrollPane.add(label);
        //////////////////////////////////thirdPanel.add(responseCompList);


        add(Box.createVerticalStrut(100));   // відступ від верху
        add(firstPanel);                           // додаємо першу панель до форми
        add(secondPanel);                          // додаємо другу панель чи до вікна
        add(thirdPanel);                           // додаємо третю панель чи до вікна
        //add(thirdPanelScrollPane);
        add(Box.createVerticalStrut(10));   // Відступ від низу

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        // використовуємо BoxLayout для всього контейнера
        // встановлюємо менеджер розташування BoxLayout для всього вікна

        serviceCity = new ServiceCity("file:///" + System.getProperty("user.dir") + "/src/main/java/dtoCities.json");
        //ініціалізуємо поля що ми обявили вище. створюємо клас і в конструктор передаємо шлях до файлу з містами
        logicGame = new LogicGame();    // так само ініціалізуємо поле що ми обявили вище
    }

    private void makeMove() {
        StringBuilder stringBuilder = new StringBuilder();  // створюэмо стрынг білдер для збирання в рядок слів
        String city = cityTextField.getText(); // створюємо змінну в яку передаємо введений в текстове поле текст
        if (city.isEmpty()) {                 // ця перевірка використовується для гравців що не вводять вісто, а без кіння клацають зробити хід
            JOptionPane.showMessageDialog(this, "Введіть місто перед натисканням кнопки 'Зробити хід'.", "Помилка", JOptionPane.ERROR_MESSAGE);
            return; // Перервати виконання методу, якщо місто не введено
        }
        String result = logicGame.addToResultCity(city); // створюємо строкову змінну результату що заповнюється методом з логіки на основі переданого вище параметру
        if (result.equals("Місто повинно починатись на літеру, яка є останньою в останньому слові списку")
                || result.equals("Місто вже виказувалось, введіть інше") || result.equals("Такого міста немає в наданому списку, повторіть спробу")) {
            JOptionPane.showMessageDialog(this, result, "Помилка", JOptionPane.ERROR_MESSAGE);
        } else if (result.equals("Computer wins!")) {
            optinPanelResult("Комп'ютер переміг!");
        } else {
            logicGame.addCityToResultList(city);
            List<String> findCitiesInComputerList = serviceCity.getCity(city);
            String resultComputerCityFound = logicGame.addCityToCompList(findCitiesInComputerList);
            if (resultComputerCityFound.equals("citynotfound")) {
                optinPanelResult("Ви перемогли!");
            } else {
                for (int i = 0; i < logicGame.getResultList().size(); i++) {
                    stringBuilder.append(logicGame.getResultList().get(i));
                    if (i != logicGame.getResultList().size() - 1) {
                        stringBuilder.append(", ");
                    }
                }
                responseCompList.setText("<html>" + stringBuilder.toString() + "</html>"); // вставляємо результат в HTML-теги щоб зручно дивитись за грою
            }
        }
        cityTextField.setText("");
    }

    private void optinPanelResult(String message) {        // метод що виводить інформативне вікно
        JOptionPane.showMessageDialog(this, message
                // this: Вікно, яке відображатиме повідомлення. Може бути посиланням на поточне вікно або компонент (наприклад, JFrame).
                + "\nРахунок: " + LogicGame.humanScore + "-"
                + LogicGame.computerScore, "Результат", JOptionPane.INFORMATION_MESSAGE);//JOptionPane.INFORMATION_MESSAGE - параметр вказує тип повідомлення, яке буде відображатися в цьому випадку
        // інформаційне  "Результат": Це заголовок діалогового вікна.
        dispose();  // використовується для закриття поточного вікна
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WindowGame windowGame = new WindowGame();
            windowGame.setVisible(true);
        });
    }
}