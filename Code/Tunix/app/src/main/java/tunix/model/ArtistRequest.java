package tunix.model;

import tunix.dto.enums.ArtistRequestStatus;

public class ArtistRequest {
    private int applicantId;
    private ArtistRequestStatus status;
    private String reason;

    public ArtistRequest(int applicantId, ArtistRequestStatus status, String reason) {
        this.applicantId = applicantId;
        this.status = status;
        this.reason = reason;
    }

    public int getApplicantId() {
        return applicantId;
    }

    public ArtistRequestStatus getStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }
}
