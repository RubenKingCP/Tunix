package tunixserver.dto.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArtistRequestResponse {

    private Long requestId;
    private Long applicantId;
    private String username;

    private String stageName;
    private String reason;

    private String profilePictureUrl;

    private String status;

    private LocalDateTime requestedAt;
    private LocalDateTime reviewedAt;

    public ArtistRequestResponse(Long requestId,
                                 Long applicantId,
                                 String username,
                                 String stageName,
                                 String reason,
                                 String profilePictureUrl,
                                 String status,
                                 LocalDateTime requestedAt,
                                 LocalDateTime reviewedAt) {

        this.requestId = requestId;
        this.applicantId = applicantId;
        this.username = username;
        this.stageName = stageName;
        this.reason = reason;
        this.profilePictureUrl = profilePictureUrl;
        this.status = status;
        this.requestedAt = requestedAt;
        this.reviewedAt = reviewedAt;
    }

    // getters
}