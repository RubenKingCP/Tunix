package tunixserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tunixserver.entities.AccountEntity;
import tunixserver.entities.ArtistEntity;
import tunixserver.repository.AccountBackendRepository;
import tunixserver.repository.ArtistBackendRepository;
import tunixserver.repository.SongBackendRepository;

@Service
@RequiredArgsConstructor
public class AdminBackendService {

    private final ArtistBackendRepository artistBackendRepository;
    private final SongBackendRepository songBackendRepository;
    private final ArtistBackendRepository artistRepo;
    private final AccountBackendRepository accountRepository;
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
    public boolean banArtist(Long artistId, String reason) {

        ArtistEntity artist = artistRepo.findById(artistId)
                .orElseThrow();

        AccountEntity account = artist.getAccount();

        account.setBanned(true);
        account.setBanReason(reason);

        return (accountRepository.save(account) != null);
    }
}