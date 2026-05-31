package tunixserver.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import tunixserver.dto.response.LibraryResponse;
import tunixserver.entities.AccountEntity;
import tunixserver.entities.ArtistEntity;
import tunixserver.entities.LibraryArtistEntity;
import tunixserver.entities.LibraryEntity;
import tunixserver.repository.AccountBackendRepository;
import tunixserver.repository.ArtistBackendRepository;
import tunixserver.repository.LibraryBackendRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class LibraryBackendService {

    private final LibraryBackendRepository libraryBackendRepository;
    private final AccountBackendRepository accountBackendRepository;
    private final ArtistBackendRepository artistBackendRepository;

    public LibraryResponse getLibrary(Long accountId) {

        LibraryEntity library = libraryBackendRepository
                .findByAccount_AccountId(accountId)
                .orElseGet(() -> createEmptyLibrary(accountId));

        return LibraryResponse.fromEntity(library);
    }

    private LibraryEntity createEmptyLibrary(Long accountId) {

        AccountEntity account = accountBackendRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        LibraryEntity library = new LibraryEntity();

        library.setAccount(account);
        library.setCreatedAt(LocalDateTime.now());

        library.setSongs(new ArrayList<>());
        library.setAlbums(new ArrayList<>());
        library.setPlaylists(new ArrayList<>());
        library.setArtists(new ArrayList<>());

        return libraryBackendRepository.save(library);
    }

    public LibraryResponse followArtist(Long artistId, Long userId) {

        LibraryEntity library = libraryBackendRepository
                .findByAccount_AccountId(userId)
                .orElseGet(() -> createEmptyLibrary(userId));

        ArtistEntity artist = artistBackendRepository.findById(artistId)
                .orElseThrow(() -> new RuntimeException("Artist not found"));

        boolean alreadyFollowing = library.getArtists()
                .stream()
                .anyMatch(libraryArtist ->
                        libraryArtist.getArtist()
                                .getId()
                                .equals(artistId));

        if (!alreadyFollowing) {

            LibraryArtistEntity libraryArtist = new LibraryArtistEntity();

            libraryArtist.setLibrary(library);
            libraryArtist.setArtist(artist);

            library.getArtists().add(libraryArtist);

            libraryBackendRepository.save(library);
        }

        return LibraryResponse.fromEntity(library);
    }

   public LibraryResponse unfollowArtist(Long artistId, Long userId) {
    LibraryEntity library = libraryBackendRepository
            .findByAccount_AccountId(userId)
            .orElseThrow(() -> new RuntimeException("Library not found"));

    boolean removed = library.getArtists().removeIf(
            libraryArtist -> libraryArtist.getArtist().getId().equals(artistId)
    );

    if (!removed) {
        throw new RuntimeException("Artist is not followed");
    }

    libraryBackendRepository.save(library);
    return LibraryResponse.fromEntity(library);
}
}