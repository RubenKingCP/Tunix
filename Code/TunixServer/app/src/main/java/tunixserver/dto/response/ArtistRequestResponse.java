package tunixserver.dto.response;

import java.time.LocalDateTime;

public class ArtistRequestResponse {

    private Long requestId;
    private Long userId;
    private String username;

    private String stageName;
    private String bio;
    private String profilePictureUrl;

    private String status;

    private LocalDateTime requestedAt;
    private LocalDateTime reviewedAt;

    public ArtistRequestResponse() {
    }

    public ArtistRequestResponse(Long requestId,
                                 Long userId,
                                 String username,
                                 String stageName,
                                 String bio,
                                 String profilePictureUrl,
                                 String status,
                                 LocalDateTime requestedAt,
                                 LocalDateTime reviewedAt) {
        this.requestId = requestId;
        this.userId = userId;
        this.username = username;
        this.stageName = stageName;
        this.bio = bio;
        this.profilePictureUrl = profilePictureUrl;
        this.status = status;
        this.requestedAt = requestedAt;
        this.reviewedAt = reviewedAt;
    }

    // getters & setters

    public Long getRequestId() { return requestId; }
    public void setRequestId(Long requestId) { this.requestId = requestId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getStageName() { return stageName; }
    public void setStageName(String stageName) { this.stageName = stageName; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getProfilePictureUrl() { return profilePictureUrl; }
    public void setProfilePictureUrl(String profilePictureUrl) { this.profilePictureUrl = profilePictureUrl; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getRequestedAt() { return requestedAt; }
    public void setRequestedAt(LocalDateTime requestedAt) { this.requestedAt = requestedAt; }

    public LocalDateTime getReviewedAt() { return reviewedAt; }
    public void setReviewedAt(LocalDateTime reviewedAt) { this.reviewedAt = reviewedAt; }
}