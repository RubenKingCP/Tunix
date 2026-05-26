package tunix.model;

import tunix.dto.enums.ArtistRequestStatus;

public class ArtistRequest {
    private int applicantId;
    private String stageName;
    private ArtistRequestStatus status;
    private String reason;

    public ArtistRequest(int applicantId, String stageName, ArtistRequestStatus status, String reason) {
        this.applicantId = applicantId;
        this.stageName = stageName;
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

    public void updateStatus(ArtistRequestStatus e){
        this.status = e;
    }

    public String getStageName() {
        return this.stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }
}
