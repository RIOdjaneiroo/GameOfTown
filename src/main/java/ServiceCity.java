import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceCity {  //клас що буде використовуватись компом для вибору відповіді
	private final List<String> secviceCity;  // створюємо список

	public ServiceCity(String jsonFilePath) {
		secviceCity = loadCity(jsonFilePath);
	}
	// в конструктор передаємо параметр строка підключення і викликаємо метод loadCity що формує список
	private List<String> loadCity(String jsonFilePath) {   //приватний метод формує список з файла
		try {
			URL url = new URL(jsonFilePath);   // строка підключення

			InputStream inputStream = url.openStream(); // створюємо змінну типу InputStream
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
			// в буфферрідер кладем наш стрім
			Gson gson = new Gson(); // створюємо обєкт типу жсон
			DtoCity[] cityArray = gson.fromJson(reader, DtoCity[].class);
			// робим масив дтошок в який складаємо прочитаний буфером файл

			reader.close(); //закриваємо рідер

			return List.of(cityArray).stream() //повертаємо список з масива стрімом
					.map(DtoCity::getName)     // витягуємо імена
					.collect(Collectors.toList()); // в колекцію
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<>(); // Return an empty list in case of error
		}
	}

	public List<String> getCity(String city) {
		char lastChar = city.toLowerCase().charAt(city.length() - 1);
		return secviceCity.stream()
				.filter(i -> i.toLowerCase().charAt(0) == lastChar)
				.collect(Collectors.toList());
	}
}