package tunixserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tunixserver.entities.AccountEntity;
import tunixserver.entities.UserEntity;

@Repository
public interface UserBackendRepository extends JpaRepository<UserEntity, Integer>{
    
}
