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

public class ServiceCity {
	private final List<String> computerCity;

	public ServiceCity(String jsonFilePath) {
		computerCity = loadCity(jsonFilePath);
	}

	private List<String> loadCity(String jsonFilePath) {
		try {
			URL url = new URL(jsonFilePath);

			InputStream inputStream = url.openStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
			Gson gson = new Gson();
			DtoCity[] cityArray = gson.fromJson(reader, DtoCity[].class);

			reader.close();

			return List.of(cityArray).stream()
					.map(DtoCity::getName)
					.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<>(); // Return an empty list in case of error
		}
	}

	public List<String> getCity(String city) {
		char lastChar = city.toLowerCase().charAt(city.length() - 1);
		return computerCity.stream()
				.filter(i -> i.toLowerCase().charAt(0) == lastChar)
				.collect(Collectors.toList());
	}
}