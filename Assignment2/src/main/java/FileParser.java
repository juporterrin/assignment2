import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.Gson;

public class FileParser {
    public static void main(String[] args) {
        String filename = "C:\\Users\\11926\\OneDrive\\Desktop\\2023Semester2(win)\\Distributed Systems\\Assignment2\\src\\main\\java\\putMessage.txt";
        String jsonString = parseFileToJsonString(filename);
        System.out.println(jsonString);
    }

    private static String parseFileToJsonString(String filename) {
        Map<String, Object> weatherData = new LinkedHashMap<>(); // Use LinkedHashMap

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();

                    // Convert numeric values to the appropriate type
                    Object parsedValue = tryParse(value);
                    if (parsedValue != null) {
                        weatherData.put(key, parsedValue);
                    } else {
                        weatherData.put(key, value);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Convert the map to JSON using Gson
        Gson gson = new Gson();
        return gson.toJson(weatherData);
    }

    // Try to parse the value as double or long
    private static Object tryParse(String value) {
        try {
            if (value.contains(".")) {
                return Double.parseDouble(value);
            } else {
                return Long.parseLong(value);
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
