package tunix.dto.request;

import lombok.Getter;

@Getter
public class ArtistApplicationRequest {

    private String name;
    private String message;

    public ArtistApplicationRequest() {}

    public ArtistApplicationRequest(String name, String message) {
        this.name = name;
        this.message = message;
    }
} 