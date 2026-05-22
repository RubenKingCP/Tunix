package tunix.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponse {
    private String displayName;
    private String profilePictureUrl;
    private boolean premiumTrialUsed;

    public UserResponse(String displayName, String profilePicUrl, boolean premiumTrialUsed){
        this.displayName = displayName;
        this.profilePictureUrl = profilePicUrl;
        this.premiumTrialUsed = premiumTrialUsed;
    }
}
