import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;


public class AggregationServer {
    public static void main(String[] args) {
        int serverPort = 8888; // Port for the server to listen on

        try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
            System.out.println("AggregationServer is running on port " + serverPort + " and waiting for connections...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                    // Handle client request
                    String request = in.readLine();
                    if (request != null) {
                        //if the request is Get request
                        if (request.equals("GET")) {

                            try (FileReader reader = new FileReader("C:\\Users\\11926\\OneDrive\\Desktop\\2023Semester2(win)\\Distributed Systems\\Assignment2\\weatherData.json");
                                 BufferedReader fileReader = new BufferedReader(reader)) {
                                StringBuilder jsonResponse = new StringBuilder();
                                String line;
                                while ((line = fileReader.readLine()) != null) {
                                    jsonResponse.append(line);
                                }

                                // 将从文件中读取的内容返回给客户端
                                out.println(jsonResponse.toString());
                                System.out.println("Sent JSON response to GET request.");
                            } catch (IOException e) {
                                out.println("Error reading JSON file.");
                                e.printStackTrace();
                            }

                          //if the requests is Put request
                        } else if (request.equals("PUT")) {
                            System.out.println("Received a put request");
                            // Handle PUT request to store JSON data
                            String weatherData = in.readLine();
                            System.out.println(weatherData);

                            // Parse the JSON data
                            try {
                                // Use a regular expression to extract the JSON part
                                Pattern pattern = Pattern.compile("\\{(.+?)\\}");
                                Matcher matcher = pattern.matcher(weatherData);

                                if (matcher.find()) {
                                    String jsonPart = matcher.group(0); // Extract the JSON part
                                    System.out.println("Extracted JSON: " + jsonPart);

                                    // Create a JSON file and write the extracted JSON part to it
                                    try (FileWriter fileWriter = new FileWriter("weatherData.json")) {
                                        fileWriter.write(jsonPart);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    System.out.println("Saved extracted JSON data to 'weatherData.json'");
                                } else {
                                    System.out.println("No JSON data found in the input string.");
                                }

                            } catch (Exception e) {
                                out.println("Invalid JSON data format.");
                            }

                            jsonFormat("C:\\Users\\11926\\OneDrive\\Desktop\\2023Semester2(win)\\Distributed Systems\\Assignment2\\weatherData.json");
                        } else {
                            out.println("Invalid request.");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void jsonFormat(String inputFile){

        // 指定输入和输出文件的路径
        String outputFile = "formatted_weather.json";
        try (FileReader reader = new FileReader(inputFile);
             FileWriter writer = new FileWriter(outputFile)) {
            // 创建 Gson 对象
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            // 解析 JSON 文件中的内容
            Object jsonObject = gson.fromJson(reader, Object.class);

            // 将格式化后的 JSON 写回文件
            gson.toJson(jsonObject, writer);

            System.out.println("test.json 文件的内容已格式化并保存到 formatted_test.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
