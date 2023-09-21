import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GETClient {
    public static void main(String[] args) {
        String serverHost = "localhost"; // Change to your AggregationServer address
        int serverPort = 8888; // Change to the port AggregationServer is listening on
        String path = "/";  // 请求的资源路径

        try (Socket socket = new Socket(serverHost, serverPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {


            // 构建 HTTP GET 请求
            out.println("GET " + path + " HTTP/1.1");
            out.println("Host: " + serverHost);
            out.println("Connection: close");  // 请求完成后关闭连接
            out.println();  // 空行表示请求头结束

            // Receive and parse the JSON response
            String jsonResponse = in.readLine();
            if (jsonResponse != null) {
                // 使用 Gson 创建格式化器
                Gson gson = new GsonBuilder().setPrettyPrinting().create();

                // 使用 Gson 解析 JSON 行
                try {
                    Object jsonObject = gson.fromJson(jsonResponse, Object.class);

                    // 使用 Gson 格式化 JSON 并打印
                    String formattedJson = gson.toJson(jsonObject);
                    System.out.println("Received JSON data:");
                    System.out.println(formattedJson);
                } catch (Exception e) {
                    System.err.println("Error parsing or formatting JSON data: " + e.getMessage());
                }
            } else {
                System.out.println("No JSON response received from the server.");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
