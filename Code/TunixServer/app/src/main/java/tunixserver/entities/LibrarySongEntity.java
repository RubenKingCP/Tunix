package tunixserver.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "library_song",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"library_id", "song_id"})
    }
)
public class LibrarySongEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // =========================
    // LIBRARY
    // =========================
    @ManyToOne
    @JoinColumn(name = "library_id", nullable = false)
    private LibraryEntity library;

    // =========================
    // SONG
    // =========================
    @ManyToOne
    @JoinColumn(name = "song_id", nullable = false)
    private SongEntity song;
}