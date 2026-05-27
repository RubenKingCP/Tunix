package tunixserver.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tunixserver.entities.PlaylistEntity;

@Repository
public interface PlaylistBackendRepository extends JpaRepository<PlaylistEntity, Long> {
    Optional<PlaylistEntity> findByTitleAndCreator_AccountId(String title, Long creatorId);
}