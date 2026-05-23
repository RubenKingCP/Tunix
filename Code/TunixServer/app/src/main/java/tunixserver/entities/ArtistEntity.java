package tunixserver.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "artist")
public class ArtistEntity {

    @Id
    private Long artistId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "artist_id")
    private AccountEntity account;

    private String stageName;

    @Column(length = 1000)
    private String bio;

    private String profilePictureUrl;

    private int monthlyListeners;

    private boolean verified;
}