package tunix.api;

import tunix.dto.response.ApiResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
        // tolerate extra fields in error responses from backend (e.g., Spring Boot error payload)
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
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

            int status = response.statusCode();

            if (status >= 200 && status < 300) {
                return objectMapper.readValue(response.body(), typeRef);
            }

            // non-2xx: try to parse a helpful message from the body, but return a failed ApiResponse
            try {
                JsonNode node = objectMapper.readTree(response.body());
                String message = null;
                if (node.has("message")) message = node.get("message").asText();
                else if (node.has("error")) message = node.get("error").asText();
                else message = "HTTP " + status + " - " + response.body();

                // build a generic ApiResponse with success=false
                String wrapper = objectMapper.writeValueAsString(new ApiResponse<>(false, message, null));
                return objectMapper.readValue(wrapper, typeRef);
            } catch (Exception ex) {
                throw new RuntimeException("GET failed: " + path + " (status " + status + ")", ex);
            }

        } catch (Exception e) {
            throw new RuntimeException("GET failed: " + path, e);
        }
    }

    // =========================
    // DELETE
    // =========================
    public <T> T delete(String path, TypeReference<T> typeReference) {

        try {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + path))
                    .header("Content-Type", "application/json")
                    .DELETE()
                    .build();

            return sendAndParse(request, typeReference);

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

    // =========================
    // CORE PARSER (TypeReference)
    // =========================
    private <T> T sendAndParse(
            HttpRequest request,
            TypeReference<T> typeReference
    ) throws Exception {

        HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("RAW RESPONSE: " + response.body());

        return objectMapper.readValue(response.body(), typeReference);
    }
}