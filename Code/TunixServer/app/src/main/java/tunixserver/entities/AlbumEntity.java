package tunixserver.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false)
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

    @ManyToMany
    @JoinTable(
        name = "album_song",   // ← was "album_son"
        joinColumns = @JoinColumn(name = "album_id"),
        inverseJoinColumns = @JoinColumn(name = "song_id")
    )
    private List<SongEntity> songs = new ArrayList<>();
}