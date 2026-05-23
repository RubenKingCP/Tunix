package tunixserver.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {
    private String displayName;
    private String profilePictureUrl;
    private boolean premiumTrialUsed;
}