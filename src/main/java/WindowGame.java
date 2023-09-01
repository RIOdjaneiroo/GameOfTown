import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class WindowGame extends JFrame {
    private JTextField cityTextField;
    private JLabel responseCompList;

    private JButton makeMoveButton;

    private ServiceCity serviceCity;
    private LogicGame resultList;

    public WindowGame() {
        setTitle("Гра в міста");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel firstPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
        cityTextField = new JTextField(15);
        Dimension textFieldSize = cityTextField.getPreferredSize();
        textFieldSize.height = 30;
        cityTextField.setPreferredSize(textFieldSize);
        JLabel cityLabel = new JLabel("введіть місто");
        cityLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));
        cityTextField.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        firstPanel.add(cityLabel);
        firstPanel.add(cityTextField);

        JPanel secondPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 65, 10));
        responseCompList = new JLabel();
        makeMoveButton = new JButton("зробити хід");
        makeMoveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeMove();
            }
        });

        secondPanel.add(responseCompList);
        secondPanel.add(makeMoveButton);

        add(Box.createVerticalStrut(100));
        add(firstPanel);
        add(secondPanel);
        add(Box.createVerticalStrut(150));

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        serviceCity = new ServiceCity("file:///" + System.getProperty("user.dir") + "/src/main/java/dtoCities.json");
        resultList = new LogicGame();
    }

    private void makeMove() {
        StringBuilder stringBuilder = new StringBuilder();
        String city = cityTextField.getText();

        String result = resultList.addToResultCity(city);
        if (result.equals("Місто повинно починатись на літеру, яка є останньою в останньому слові списку")
                || result.equals("Місто вже виказувалось, введіть інше") || result.equals("Введіть існуючу назву міста")) {
            JOptionPane.showMessageDialog(this, result, "Помилка", JOptionPane.ERROR_MESSAGE);
        } else if (result.equals("Computer wins!")) {
            optinPanelResult("Комп'ютер переміг!");
        } else {
            resultList.addToResultListByHuman(city);
            List<String> findCitiesInComputerList = serviceCity.getCity(city);
            String resultComputerCityFound = resultList.addCityToCompList(findCitiesInComputerList);
            if (resultComputerCityFound.equals("citynotfound")) {
                optinPanelResult("Ви перемогли!");
            } else {
                for (int i = 0; i < resultList.getResultList().size(); i++) {
                    stringBuilder.append(resultList.getResultList().get(i));
                    if (i != resultList.getResultList().size() - 1) {
                        stringBuilder.append(", ");
                    }
                }
                responseCompList.setText(stringBuilder.toString());
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