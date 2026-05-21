package tunix.dto.request;

public class ArtistApplicationRequest {

    private String name;
    private String message;

    public ArtistApplicationRequest() {}

    public ArtistApplicationRequest(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getStatus() {
        return null;
    }

    public String getMessage() {
        return message;
    }
} 