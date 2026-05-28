package tunixserver.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import tunixserver.dto.response.LibraryResponse;
import tunixserver.entities.AccountEntity;
import tunixserver.entities.LibraryEntity;
import tunixserver.repository.AccountBackendRepository;
import tunixserver.repository.LibraryBackendRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class LibraryBackendService {

    private final LibraryBackendRepository libraryBackendRepository;
    private final AccountBackendRepository accountBackendRepository;

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
}