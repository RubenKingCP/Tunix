package tunix.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String displayName;
    private String profilePictureUrl;
    private boolean premium;
    private boolean premiumTrialUsed;
}