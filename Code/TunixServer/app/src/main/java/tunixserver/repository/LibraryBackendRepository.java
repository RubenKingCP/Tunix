package tunixserver.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tunixserver.entities.LibraryEntity;

@Repository
public interface LibraryBackendRepository extends JpaRepository<LibraryEntity, Long> {

    Optional<LibraryEntity> findByAccount_AccountId(Long accountId);

}