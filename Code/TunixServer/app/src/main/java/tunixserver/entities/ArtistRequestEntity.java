package tunixserver.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "artist_request")
public class ArtistRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String stageName;

    private String bio;

    private String profilePictureUrl;

    private String status;

    private LocalDateTime requestedAt;

    private LocalDateTime reviewedAt;

    public ArtistRequestEntity() {
    }

    public ArtistRequestEntity(UserEntity user, String stageName, String bio, String profilePictureUrl) {
        this.user = user;
        this.stageName = stageName;
        this.bio = bio;
        this.profilePictureUrl = profilePictureUrl;
        this.status = "PENDING";
        this.requestedAt = LocalDateTime.now();
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(LocalDateTime requestedAt) {
        this.requestedAt = requestedAt;
    }

    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(LocalDateTime reviewedAt) {
        this.reviewedAt = reviewedAt;
    }

    public void approve() {
        this.status = "APPROVED";
        this.reviewedAt = LocalDateTime.now();
    }

    public void reject() {
        this.status = "REJECTED";
        this.reviewedAt = LocalDateTime.now();
    }
}
