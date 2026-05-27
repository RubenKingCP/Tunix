package tunixserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tunixserver.entities.SongEntity;

@Repository
public interface SongBackendRepository extends JpaRepository<SongEntity, Long> {

    boolean existsByTitleAndArtist_Id(String title, Long artistId);

    SongEntity getSongById(Long songId); 

    java.util.List<SongEntity> findByTitleContainingIgnoreCase(String title);
}