package tunixserver.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "album")
public class AlbumEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    private ArtistEntity artist;

    private LocalDate releaseDate;

    public AlbumEntity(String title, ArtistEntity artistEntity, LocalDate rDate) {
        this.artist = artistEntity;
        this.title = title;
        this.releaseDate = rDate;
    }
}