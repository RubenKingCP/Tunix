package tunixserver.entities;

import jakarta.persistence.*;
import tunixserver.dto.enums.RequestStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "artist_request")
public class ArtistRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long requestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "stage_name", length = 150)
    private String stageName;

    // IMPORTANT: matches DTO field "reason"
    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason;

    @Column(name = "profile_picture_url", columnDefinition = "TEXT")
    private String profilePictureUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RequestStatus status;

    @Column(name = "requested_at", updatable = false)
    private LocalDateTime requestedAt;

    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;

    public ArtistRequestEntity() {}

    public ArtistRequestEntity(UserEntity user,
                               String stageName,
                               String reason,
                               String profilePictureUrl) {
        this.user = user;
        this.stageName = stageName;
        this.reason = reason;
        this.profilePictureUrl = profilePictureUrl;
        this.status = RequestStatus.PENDING;
        this.requestedAt = LocalDateTime.now();
    }

    @PrePersist
    public void onCreate() {
        if (status == null) status = RequestStatus.PENDING;
        if (requestedAt == null) requestedAt = LocalDateTime.now();
    }

    // business logic
    public void approve() {
        this.status = RequestStatus.APPROVED;
        this.reviewedAt = LocalDateTime.now();
    }

    public void reject() {
        this.status = RequestStatus.REJECTED;
        this.reviewedAt = LocalDateTime.now();
    }

    // getters

    public Long getRequestId() {
        return requestId;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }

    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }
}