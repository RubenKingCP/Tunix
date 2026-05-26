package tunixserver.service;

import tunixserver.dto.request.SongRequest;
import tunixserver.entities.ArtistEntity;
import tunixserver.entities.SongEntity;
import tunixserver.repository.ArtistBackendRepository;
import tunixserver.repository.SongBackendRepository;

import org.springframework.stereotype.Service;

@Service
public class SongBackendService {
    private final SongBackendRepository songRepository;
    private final ArtistBackendRepository artistRepository;
    
    public SongBackendService(SongBackendRepository songRepository, ArtistBackendRepository artistRepository) {
        // Initialize any required resources, such as repositories or services
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
    }
    public SongEntity uploadSong(SongRequest req) {

    // 1. Check duplicate
    boolean exists = songRepository.existsByTitleAndArtist_Id(
            req.getTitle(),
            req.getArtistId()
    );

    if (exists) {
        throw new RuntimeException("Song already exists");
    }

    // 2. Fetch artist
    ArtistEntity artist = artistRepository.findById(req.getArtistId())
            .orElseThrow(() -> new RuntimeException("Artist not found"));

    // 3. Create song entity
    SongEntity song = new SongEntity();
    song.setTitle(req.getTitle());
    song.setArtist(artist);
    song.setDuration(req.getDuration());
    song.setFilePathUrl(req.getFilePathUrl());
    song.setCoverImageUrl(req.getCoverImageUrl());

    // 4. Save to DB
    return songRepository.save(song);
}
}
