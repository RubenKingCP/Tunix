package tunixserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import tunixserver.entities.AccountWarningEntity;

public interface AccountWarningBackendRepository
        extends JpaRepository<AccountWarningEntity, Long> {

            List<AccountWarningEntity> findByAccount_AccountId(Long accountId);
            long countByAccount_AccountId(Long accountId);
}