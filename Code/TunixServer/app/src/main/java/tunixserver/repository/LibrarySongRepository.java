package tunixserver.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tunixserver.entities.LibraryEntity;
import tunixserver.entities.LibrarySongEntity;
import tunixserver.entities.SongEntity;

public interface LibrarySongRepository extends JpaRepository<LibrarySongEntity, Long> {

    boolean existsByLibraryAndSong(LibraryEntity library, SongEntity song);

    Optional<LibrarySongEntity> findByLibraryAndSong(LibraryEntity library, SongEntity song);
}