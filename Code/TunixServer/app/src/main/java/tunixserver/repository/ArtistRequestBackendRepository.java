package tunixserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tunixserver.entities.ArtistRequestEntity;
import tunixserver.entities.UserEntity;
import tunixserver.dto.enums.RequestStatus;

import java.util.List;

public interface ArtistRequestBackendRepository extends JpaRepository<ArtistRequestEntity, Long> {

    List<ArtistRequestEntity> findByStatus(RequestStatus status);
    boolean existsByUser(UserEntity user);
}