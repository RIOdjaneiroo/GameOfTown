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
    public String addCityToCompList(List<String> findCitiesInComputerList) {
        for (String compCity : findCitiesInComputerList) {
            if (!resultList.contains(compCity)) {
                computerScore++;
                resultList.addLast(compCity);
                return compCity;
            }
        }
        return "citynotfound";
    }



    public String addToResultCity(String city) {
        if (isExistInList(city)) {
            return "Місто вже є у списку, спробуйте інше";
        } else if (city.equalsIgnoreCase("здаюсь")) {
            return "Computer wins!";
        } else if (getResultList().size() > 1 && !isFirstLetterCorrect(city, getResultList().getLast())) {
            return "Місто повинно починатись на літеру, яка є останньою в останньому слові списку";
        } else if (!isRealCity(city)) {
            return "Введіть існуючу назву міста";
        } else {
            return city;
        }
    }

    public void addToResultListByHuman(String city) {
        humanScore++;
        resultList.addLast(city);
    }

    private void parseCityData() {
        try {
            String filePath = "file:///" + System.getProperty("user.dir") + "/src/main/java/dtoCities.json";
            URL url = new URL(filePath);

            InputStream inputStream = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            Gson gson = new Gson();
            DtoCity[] cities = gson.fromJson(reader, DtoCity[].class);

            for (DtoCity city : cities) {
                cityRegionMap.put(city.getName().toLowerCase(), ""); // Empty region for simplicity
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LinkedList<String> getResultList() {
        return resultList;
    }
}
