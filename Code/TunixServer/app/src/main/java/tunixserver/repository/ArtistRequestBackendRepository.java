package tunixserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tunixserver.entities.ArtistRequestEntity;
import tunixserver.entities.UserEntity;
import tunixserver.dto.enums.RequestStatus;

import java.util.List;
import java.util.Optional;

public interface ArtistRequestBackendRepository extends JpaRepository<ArtistRequestEntity, Long> {

    List<ArtistRequestEntity> findByStatus(RequestStatus status);
    boolean existsByUser(UserEntity user);
    Optional<ArtistRequestEntity> findByUser_Id(Long accountId);
}