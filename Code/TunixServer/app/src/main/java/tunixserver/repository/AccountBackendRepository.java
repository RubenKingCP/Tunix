package tunixserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tunixserver.entities.AccountEntity;

public interface AccountBackendRepository extends JpaRepository<AccountEntity, Integer>{
    
}
