package tunixserver.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tunixserver.entities.UserEntity;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String displayName;
    private String profilePictureUrl;
    private boolean premium;
    private boolean premiumTrialUsed;

    public static UserResponse fromEntity(UserEntity user) {
        return new UserResponse(
                user.getId(),
                user.getDisplayName(),
                user.getProfilePictureUrl(),
                user.isPremium(),
                user.isPremiumTrialUsed()
        );
    }
}