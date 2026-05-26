package tunixserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tunixserver.entities.AccountEntity;
import tunixserver.entities.ArtistEntity;

@Repository
public interface ArtistBackendRepository extends JpaRepository<ArtistEntity, Long> {
    boolean existsByAccount(AccountEntity account);
}