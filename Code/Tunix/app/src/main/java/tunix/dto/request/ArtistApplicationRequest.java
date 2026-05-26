package tunix.dto.request;

import lombok.Getter;

@Getter
public class ArtistApplicationRequest {

    private int accountId;
    private String stageName;
    private String message;

    public ArtistApplicationRequest() {}

    public ArtistApplicationRequest(int accountId, String stageName, String message) {
        this.accountId = accountId;
        this.stageName = stageName;
        this.message = message;
    }
} 