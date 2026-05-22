package tunixserver.entities;

import jakarta.persistence.*;
import lombok.Builder;

@Builder
@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    private Long userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private AccountEntity account;

    private String displayName;

    private String profilePictureUrl;

    private boolean premiumTrialUsed;
}
