package tunixserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tunixserver.entities.AlbumEntity;

@Repository
public interface AlbumBackendRepository extends JpaRepository<AlbumEntity, Long>{
    List<AlbumEntity> findByTitleContainingIgnoreCase(String Title);
}
