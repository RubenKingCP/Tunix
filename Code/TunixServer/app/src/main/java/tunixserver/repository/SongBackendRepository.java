package tunixserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tunixserver.entities.SongEntity;

@Repository
public interface SongBackendRepository extends JpaRepository<SongEntity, Integer> {
    // duplicate check — Spring generates the SQL automatically
    boolean existsByTitleAndArtistId(String title, int artistId);
}