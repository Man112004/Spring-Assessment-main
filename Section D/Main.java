import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {

    public static void main(String[] args) throws Exception {

        String apiKey = System.getenv("OPENAI_API_KEY");

        if (apiKey == null || apiKey.isBlank()) {
            System.out.println("Please set OPENAI_API_KEY.");
            return;
        }

        Scanner sc = new Scanner(System.in);
        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        while (true) {
            System.out.print("Enter prompt (exit to quit): ");
            String prompt = sc.nextLine();

            if (prompt.equalsIgnoreCase("exit")) {
                break;
            }

            String json = mapper.createObjectNode()
                    .put("model", "gpt-4.1-mini")
                    .set("messages", mapper.createArrayNode()
                            .add(mapper.createObjectNode()
                                    .put("role", "user")
                                    .put("content", prompt)))
                    .toString();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(
                            "https://api.openai.com/v1/chat/completions"))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString());

            JsonNode root = mapper.readTree(response.body());

            if (response.statusCode() == 200) {
                System.out.println(
                        root.path("choices").get(0)
                                .path("message").path("content").asText());
            } else {
                System.out.println("API Error: " + response.statusCode());
            }
        }

        sc.close();
    }
}