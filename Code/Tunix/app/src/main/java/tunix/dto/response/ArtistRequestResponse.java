package tunix.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArtistRequestResponse {

    private Long requestId;
    private Long accountId;
    private String stageName;
    private String bio;
    private String status; // PENDING / APPROVED / REJECTED
}