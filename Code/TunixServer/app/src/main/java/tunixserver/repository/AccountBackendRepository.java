package tunixserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import tunixserver.entities.AccountEntity;

public interface AccountBackendRepository extends JpaRepository<AccountEntity, Integer>{
    Optional<AccountEntity> findByUsernameAndPassword(String username, String password);
}
