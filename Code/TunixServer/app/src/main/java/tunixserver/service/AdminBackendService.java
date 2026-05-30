package tunixserver.service;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tunixserver.dto.request.BanRequest;
import tunixserver.entities.AccountEntity;
import tunixserver.entities.AccountWarningEntity;
import tunixserver.entities.ArtistEntity;
import tunixserver.repository.AccountBackendRepository;
import tunixserver.repository.AccountWarningBackendRepository;
import tunixserver.repository.ArtistBackendRepository;
import tunixserver.repository.SongBackendRepository;

@Service
@RequiredArgsConstructor
public class AdminBackendService {

    private final ArtistBackendRepository artistBackendRepository;
    private final SongBackendRepository songBackendRepository;
    private final ArtistBackendRepository artistRepo;
    private final AccountBackendRepository accountRepository;
    private final AccountWarningBackendRepository warningBackendRepository;
    // =========================
    // ISSUE WARNING
    // =========================
    @Transactional
    public Boolean issueWarning(BanRequest request) {

        ArtistEntity artist = artistBackendRepository.findById((long) request.getArtistId())
                .orElseThrow(() -> new RuntimeException("Artist not found"));

        AccountWarningEntity warning = AccountWarningEntity.builder()
                                            .account(artist.getAccount())
                                            .warnedBy(null)
                                            .reason(request.getReason())
                                            .build();

        warningBackendRepository.save(warning);

        artist.getAccount().getWarnings().add(warning);
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

    // GET WARNINGS
    public List<String> getWarningsById(int artistId) {
        ArtistEntity artist = artistBackendRepository.findById((long) artistId)
                .orElseThrow(() -> new RuntimeException("Artist not found"));
        List<AccountWarningEntity> warnings = warningBackendRepository.findByAccount_AccountId(artist.getAccount().getAccountId());
        return warnings.stream()
                .map(AccountWarningEntity::getReason)
                .toList();
    }
}