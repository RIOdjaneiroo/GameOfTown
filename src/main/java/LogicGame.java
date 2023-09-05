import com.google.gson.Gson;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class LogicGame {
    public static int humanScore = 0;
    public static int computerScore = 0;
    public LinkedList<String> resultList = new LinkedList<>();

    private HashMap<String,String> cityRegionMap = new HashMap<>();

    private boolean isExistInList(String city) {
        return resultList.stream().anyMatch(i -> i.equalsIgnoreCase(city));
    }

    private boolean isFirstLetterCorrect(String city, String lastCity) {
        return city.toLowerCase().charAt(0) == lastCity.toLowerCase().charAt(lastCity.length() - 1);
    }

    private boolean isRealCity(String city) {
        if (cityRegionMap.isEmpty()) {
            parseCityData();
        }
        return cityRegionMap.containsKey(city.toLowerCase());
    }
    public String addCityToCompList(List<String> listCityCompFind) { // метод що перевіряє чи містить результуючий список місто
        for (String cityTown : listCityCompFind) { // циклом перевіряємо переданий масив                          повертає строку
            String cityInLowerCase = cityTown.toLowerCase(); // Переводимо місто в нижній регістр
            if (!resultList.stream().anyMatch(i -> i.equalsIgnoreCase(cityInLowerCase))) {    // якщо результуючий список не містить місто зі списку без врахування регісту
                computerScore++;                    // додаємо компютору бал
                resultList.addLast(cityTown);       // додаємо місто до результуючого списку
                return cityTown;                    // виводимо місто в результат
            }
        }
        return "citynotfound";                      // і виводимо notfound"
    }



    public String addToResultCity(String city) {  // метод що робить перевірку введеного міста перед тим як вивести в лейбл

         if (city.equalsIgnoreCase("здаюсь")) {
            return "Computer wins!";
        } else if (getResultList().size() > 1 && !isFirstLetterCorrect(city, getResultList().getLast())) {
            return "Місто повинно починатись на останню літеру останнього написаного комп'ютером міста";
        } else if (!isRealCity(city)) {
            return "Такого міста немає в наданому списку, повторіть спробу";
        } else {
            //addCityToResultList(city);
            return city;
        }
    }

    public void addCityToResultList(String city) { // метод що додає гравцю бал та заносить місто до результуючого списку
        humanScore++;
        resultList.addLast(city);
        //resultList.addLast(city.toLowerCase()); // конвертуємо введене місто до нижнього регістру перед додаванням
    }

    private void parseCityData() {         //метод для розбору даних з файлу JSON
        try {
            String filePath = "file:///" + System.getProperty("user.dir") + "/src/main/java/dtoCities.json";
            URL url = new URL(filePath); //створюємо об'єкт типу URL на основі рядка filePath, який містить шлях до файлу
            InputStream inputStream = url.openStream(); //відкриває потік вводу (InputStream) для читання даних з URL, який було підготовлено
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            // створюємо BufferedReader дозволить зручно читати дані з потоку вводу використовуючи InputStreamReader, що конвертує байтовий потік у текстовий вказуєм кодування UTF-8
            Gson gson = new Gson(); // Створюємо обєкт Gson, який буде використовуватися для розбору даних в об'єкти.
            DtoCity[] cities = gson.fromJson(reader, DtoCity[].class); //створюємо масив об'єктів типу DtoCity і заповнюємо інформацією про міста з буферу по шаблону
            for (DtoCity city : cities) {             // читаємо масив і на його основі формуємо мапу з ключем "назва міста" і порожнім значенням
                cityRegionMap.put(city.getName().toLowerCase(), ""); // пізніше використаємо
            }
            reader.close();  // закриваємо рідер
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LinkedList<String> getResultList() {
        return resultList;
    }
}
