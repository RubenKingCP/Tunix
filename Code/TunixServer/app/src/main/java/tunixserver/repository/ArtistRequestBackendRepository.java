package tunixserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tunixserver.entities.ArtistRequestEntity;

public interface ArtistRequestBackendRepository extends JpaRepository<ArtistRequestEntity, Long> {
}