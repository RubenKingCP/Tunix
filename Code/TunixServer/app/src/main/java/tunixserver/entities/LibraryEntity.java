package tunixserver.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "library")
public class LibraryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // =========================
    // OWNER ACCOUNT
    // =========================
    @OneToOne
    @JoinColumn(name = "account_id", nullable = false, unique = true)
    private AccountEntity account;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // =========================
    // LIBRARY SONGS
    // =========================
    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LibrarySongEntity> songs = new ArrayList<>();

    // =========================
    // LIBRARY ALBUMS
    // =========================
    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LibraryAlbumEntity> albums = new ArrayList<>();

    // =========================
    // LIBRARY PLAYLISTS
    // =========================
    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LibraryPlaylistEntity> playlists = new ArrayList<>();

    // =========================
    // LIBRARY ARTISTS
    // =========================
    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LibraryArtistEntity> artists = new ArrayList<>();
}