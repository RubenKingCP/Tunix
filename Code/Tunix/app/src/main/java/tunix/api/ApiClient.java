package tunix.api;

import tunix.dto.response.ApiResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ApiClient {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String baseUrl;

    public ApiClient(String baseUrl) {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
        this.baseUrl = baseUrl;

        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    // =========================
    // POST (FIXED)
    // =========================
    public <T> ApiResponse<T> post(String path, Object body, Class<T> dataType) {
        try {

            // 🔥 DEBUG: print real JSON BEFORE sending
            String jsonRequest = objectMapper.writeValueAsString(body);

            System.out.println("========== API CLIENT DEBUG ==========");
            System.out.println("POST URL: " + baseUrl + path);
            System.out.println("REQUEST JSON:");
            System.out.println(jsonRequest);
            System.out.println("======================================");

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + path))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                    .build();

            return sendAndParse(request, dataType);

        } catch (Exception e) {
            throw new RuntimeException("POST failed: " + path, e);
        }
    }

    // =========================
    // PUT
    // =========================
    public <T> ApiResponse<T> put(String path, Object body, Class<T> dataType) {
        try {

            String jsonRequest = objectMapper.writeValueAsString(body);

            System.out.println("PUT JSON:");
            System.out.println(jsonRequest);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + path))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonRequest))
                    .build();

            return sendAndParse(request, dataType);

        } catch (Exception e) {
            throw new RuntimeException("PUT failed: " + path, e);
        }
    }

    // =========================
    // GET
    // =========================
    public <T> ApiResponse<T> get(String path, TypeReference<ApiResponse<T>> typeRef) {
        try {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + path))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("RAW RESPONSE: " + response.body());

            return objectMapper.readValue(response.body(), typeRef);

        } catch (Exception e) {
            throw new RuntimeException("GET failed: " + path, e);
        }
    }

    // =========================
    // DELETE
    // =========================
    public <T> ApiResponse<T> delete(String path, Class<T> dataType) {
        try {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + path))
                    .header("Content-Type", "application/json")
                    .DELETE()
                    .build();

            return sendAndParse(request, dataType);

        } catch (Exception e) {
            throw new RuntimeException("DELETE failed: " + path, e);
        }
    }

    // =========================
    // CORE PARSER
    // =========================
    private <T> ApiResponse<T> sendAndParse(HttpRequest request, Class<T> dataType) throws Exception {

        HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("RAW RESPONSE: " + response.body());

        JsonNode root = objectMapper.readTree(response.body());

        boolean success = root.get("success").asBoolean();
        String message = root.get("message").asText();
        JsonNode dataNode = root.get("data");

        T data = null;
        if (dataNode != null && !dataNode.isNull()) {
            data = objectMapper.treeToValue(dataNode, dataType);
        }

        return new ApiResponse<>(success, message, data);
    }
}