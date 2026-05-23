package tunix.api;

import tunix.dto.response.ApiResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class ApiClient {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String baseUrl;

    public ApiClient(String baseUrl) {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
        this.baseUrl = baseUrl;
    }

    // POST
    public <T> ApiResponse<T> post(String path, Object body, Class<T> dataType) {
        System.err.println("Request post reached\n");
        try {
            String jsonRequest = objectMapper.writeValueAsString(body);
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

    // GET
    public <T> ApiResponse<T> get(String path, Class<T> dataType) {
        System.err.println("Request get reached\n");
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + path))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();
            return sendAndParse(request, dataType);
        } catch (Exception e) {
            throw new RuntimeException("GET failed: " + path, e);
        }
    }

    // GET for lists
    public <T> ApiResponse<T> get(String path, TypeReference<ApiResponse<T>> typeRef) {
    try {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + path))
                .GET()
                .build();

        HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return objectMapper.readValue(response.body(), typeRef);

    } catch (Exception e) {
        throw new RuntimeException("GET failed", e);
    }
}

    // DELETE
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

    // shared logic for parsing the response
    private <T> ApiResponse<T> sendAndParse(HttpRequest request, Class<T> dataType) throws Exception {
        HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        JsonNode root = objectMapper.readTree(response.body());
        boolean success = root.get("success").asBoolean();
        String message = root.get("message").asText();
        JsonNode dataNode = root.get("data");

        T data = null;
        if (dataNode != null && !dataNode.isNull()) {
            data = objectMapper.treeToValue(dataNode, dataType);
        }

        ApiResponse<T> apiResponse = new ApiResponse<>(
                success,
                message,
                data
        );

        return apiResponse;
    }
}