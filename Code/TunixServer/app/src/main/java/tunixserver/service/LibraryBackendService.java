package tunixserver.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tunixserver.dto.response.AlbumResponse;
import tunixserver.dto.response.ApiResponse;
import tunixserver.dto.response.ArtistResponse;
import tunixserver.dto.response.LibraryResponse;
import tunixserver.dto.response.PlaylistResponse;
import tunixserver.dto.response.SongResponse;
import tunixserver.entities.AccountEntity;
import tunixserver.entities.LibraryEntity;
import tunixserver.entities.LibrarySongEntity;
import tunixserver.entities.SongEntity;
import tunixserver.mapper.SongMapper;
import tunixserver.repository.AccountBackendRepository;
import tunixserver.repository.LibraryRepository;
import tunixserver.repository.LibrarySongRepository;
import tunixserver.repository.SongBackendRepository;

@Service
public class LibraryBackendService {
    private final AccountBackendRepository accountRepo;
    private final LibraryRepository libraryRepo;
    private final SongBackendRepository songRepo;
    private final LibrarySongRepository librarySongRepo;

    public LibraryBackendService(AccountBackendRepository accountRepo, 
                            LibraryRepository libraryRepo, 
                            SongBackendRepository songRepo,
                            LibrarySongRepository librarySongRepo) {
        this.accountRepo = accountRepo;
        this.libraryRepo = libraryRepo;
        this.songRepo = songRepo;
        this.librarySongRepo = librarySongRepo;
    }

    @Transactional
    public ApiResponse<Void> addSongToLibrary(Long accountId, Long songId) {

        AccountEntity account = accountRepo.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        LibraryEntity library = libraryRepo.findByAccount(account)
                .orElseThrow(() -> new RuntimeException("Library not found"));

        SongEntity song = songRepo.findById(songId)
                .orElseThrow(() -> new RuntimeException("Song not found"));

        if (librarySongRepo.existsByLibraryAndSong(library, song)) {
            return new ApiResponse<>(false, "Song already in library", null);
        }

        LibrarySongEntity entry = new LibrarySongEntity();
        entry.setLibrary(library);
        entry.setSong(song);

        librarySongRepo.save(entry);

        return new ApiResponse<>(true, "Song added to library", null);
    }

    @Transactional
    public ApiResponse<Void> removeSongFromLibrary(Long accountId, Long songId) {

        AccountEntity account = accountRepo.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        LibraryEntity library = libraryRepo.findByAccount(account)
                .orElseThrow(() -> new RuntimeException("Library not found"));

        SongEntity song = songRepo.findById(songId)
                .orElseThrow(() -> new RuntimeException("Song not found"));

        LibrarySongEntity entry = librarySongRepo
                .findByLibraryAndSong(library, song)
                .orElseThrow(() -> new RuntimeException("Song not in library"));

        librarySongRepo.delete(entry);

        return new ApiResponse<>(true, "Song removed", null);
        }

       public LibraryResponse getLibrary(Long accountId) {

                LibraryEntity library = libraryRepo.findByAccount_Id(accountId)
                        .orElseThrow(() -> new RuntimeException("Library not found"));

                List<SongResponse> songs = libraryRepo.findSongsByLibraryId(library.getId())
                        .stream()
                        .map(SongResponse::fromEntity)
                        .toList();

                List<AlbumResponse> albums = libraryRepo.findAlbumsByLibraryId(library.getId())
                        .stream()
                        .map(AlbumResponse::fromEntity)
                        .toList();

                List<PlaylistResponse> playlists = libraryRepo.findPlaylistsByLibraryId(library.getId())
                        .stream()
                        .map(PlaylistResponse::fromEntity)
                        .toList();

                List<ArtistResponse> artists = libraryRepo.findArtistsByLibraryId(library.getId())
                        .stream()
                        .map(ArtistResponse::fromEntity)
                        .toList();

                return new LibraryResponse(
                        library.getId(),
                        accountId,
                        library.getCreatedAt(),
                        songs,
                        albums,
                        playlists,
                        artists
                );
        }
}
