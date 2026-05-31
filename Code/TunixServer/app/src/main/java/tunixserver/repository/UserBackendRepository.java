package tunixserver.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tunixserver.entities.UserEntity;

@Repository
public interface UserBackendRepository extends JpaRepository<UserEntity, Long>{
    Optional<UserEntity> findByAccount_AccountId(Long accountId);
}
