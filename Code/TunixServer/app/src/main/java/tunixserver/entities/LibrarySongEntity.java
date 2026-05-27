package tunixserver.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "library_song")
@Getter
@Setter
public class LibrarySongEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "library_id", nullable = false)
    private LibraryEntity library;

    @ManyToOne
    @JoinColumn(name = "song_id", nullable = false)
    private SongEntity song;
}