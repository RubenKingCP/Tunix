package tunixserver.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tunixserver.entities.AccountEntity;
import tunixserver.entities.AlbumEntity;
import tunixserver.entities.ArtistEntity;
import tunixserver.entities.LibraryEntity;
import tunixserver.entities.PlaylistEntity;
import tunixserver.entities.SongEntity;

@Repository
public interface LibraryRepository extends JpaRepository<LibraryEntity, Long> {

    // correct object-based lookup
    Optional<LibraryEntity> findByAccount(AccountEntity account);

    // correct nested property lookup
    Optional<LibraryEntity> findByAccount_Id(Long accountId);

    @Query("""
    SELECT s FROM SongEntity s
    JOIN LibrarySongEntity ls ON ls.song.id = s.id
    WHERE ls.library.id = :libraryId
    """)
    List<SongEntity> findSongsByLibraryId(Long libraryId);

    @Query("""
    SELECT a FROM AlbumEntity a
    JOIN LibraryAlbumEntity la ON la.album.id = a.id
    WHERE la.library.id = :libraryId
    """)
    List<AlbumEntity> findAlbumsByLibraryId(Long libraryId);

    @Query("""
    SELECT p FROM PlaylistEntity p
    JOIN LibraryPlaylistEntity lp ON lp.playlist.id = p.id
    WHERE lp.library.id = :libraryId
    """)
    List<PlaylistEntity> findPlaylistsByLibraryId(Long libraryId);

    @Query("""
    SELECT ar FROM ArtistEntity ar
    JOIN LibraryArtistEntity la ON la.artist.id = ar.id
    WHERE la.library.id = :libraryId
    """)
    List<ArtistEntity> findArtistsByLibraryId(Long libraryId);
}