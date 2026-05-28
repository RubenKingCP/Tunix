package tunixserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tunixserver.entities.ArtistEntity;
import tunixserver.repository.ArtistBackendRepository;
import tunixserver.repository.SongBackendRepository;

@Service
@RequiredArgsConstructor
public class AdminBackendService {

    private final ArtistBackendRepository artistBackendRepository;
    private final SongBackendRepository songBackendRepository;
    // =========================
    // ISSUE WARNING
    // =========================
    public Boolean issueWarning(int artistId) {

        ArtistEntity artist = artistBackendRepository.findById((long) artistId)
                .orElseThrow(() -> new RuntimeException("Artist not found"));

        System.out.println("Warning issued to artist: " + artist.getId());

        return true;
    }

    // =========================
    // ISSUE BAN
    // =========================
    @Transactional
    public Boolean issueBan(int artistId) {

        Long id = (long) artistId;

        System.out.println("=== BAN START ===");
        System.out.println("Artist ID to ban: " + id);

        ArtistEntity artist = artistBackendRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artist not found"));

        System.out.println("Found artist: " + artist.getId());

        // 1. delete dependent songs first
        int deletedSongs = songBackendRepository.deleteByArtistId(id);
        System.out.println("Deleted songs count: " + deletedSongs);

        // 2. delete artist
        artistBackendRepository.deleteById(id);
        System.out.println("Artist deleted");

        System.out.println("=== BAN COMPLETE ===");

        return true;
    }
}