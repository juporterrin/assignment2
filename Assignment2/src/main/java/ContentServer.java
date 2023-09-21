import com.google.gson.Gson;
import java.io.*;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

public class ContentServer {
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
    public static void main(String[] args) {

        String serverHost = "localhost"; // Change to your AggregationServer address
        int serverPort = 8888; // Change to the port AggregationServer is listening on
        String path = "/";

        try (Socket socket = new Socket(serverHost, serverPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String filename = "C:\\Users\\11926\\OneDrive\\Desktop\\2023Semester2(win)\\Distributed Systems\\Assignment2\\src\\main\\java\\putMessage.txt";
            String jsonString = parseFileToJsonString(filename);

            String content = "PUT /weather.json HTTP/1.1 User-Agent: ATOMClient/1/0 Content-Type: (You should work this one out) Content-Length: (And this one too)";
            // Send a PUT request with the content from the file as JSON data to the AggregationServer
            out.println("PUT");
            out.println(content + jsonString);
            System.out.println(content + jsonString);

            // Receive a response (if needed)
            String response = in.readLine();
            System.out.println("Received response: " + response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
