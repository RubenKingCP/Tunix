package tunixserver.dto.response;

public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;

    // Empty constructor for Jackson
    public ApiResponse() {}

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    // SUCCESS WITH DATA
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(
                true,
                "Operation successful",
                data
        );
    }

    // SUCCESS WITHOUT DATA
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(
                true,
                "Operation successful",
                null
        );
    }

    // ERROR
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(
                false,
                message,
                null
        );
    }
}