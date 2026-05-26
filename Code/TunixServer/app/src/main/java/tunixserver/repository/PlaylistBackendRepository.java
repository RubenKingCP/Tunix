package tunixserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tunixserver.entities.PlaylistEntity;

@Repository
public interface PlaylistBackendRepository extends JpaRepository<PlaylistEntity, Long> {
}