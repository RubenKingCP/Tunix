package tunixserver.entities;

import tunixserver.dto.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    private String username;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private UserEntity user;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private ArtistEntity artist;
}