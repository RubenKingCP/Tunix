package tunixserver.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "library_playlist",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"library_id", "playlist_id"})
    }
)
public class LibraryPlaylistEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "library_id", nullable = false)
    private LibraryEntity library;

    @ManyToOne
    @JoinColumn(name = "playlist_id", nullable = false)
    private PlaylistEntity playlist;
}