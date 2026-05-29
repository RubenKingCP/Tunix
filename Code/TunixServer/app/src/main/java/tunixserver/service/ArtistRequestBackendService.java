package tunixserver.service;

import tunixserver.repository.AccountBackendRepository;
import tunixserver.repository.ArtistBackendRepository;
import tunixserver.repository.ArtistRequestBackendRepository;
import tunixserver.dto.response.ArtistRequestResponse;
import tunixserver.dto.enums.RequestStatus;
import tunixserver.dto.enums.Role;
import tunixserver.dto.request.ArtistApplicationRequest;
import tunixserver.dto.response.ApiResponse;
import tunixserver.entities.AccountEntity;
import tunixserver.entities.ArtistEntity;
import tunixserver.entities.ArtistRequestEntity;
import tunixserver.mapper.ArtistRequestMapper;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class ArtistRequestBackendService {

    private final ArtistRequestBackendRepository artistRequestRepository;
    private final ArtistBackendRepository artistBackendRepository;
    private final AccountBackendRepository accountBackendRepository;

    public ArtistRequestBackendService(
            ArtistRequestBackendRepository artistRequestRepository,
            AccountBackendRepository accountBackendRepository,
            ArtistBackendRepository artistBackendRepository
    ) {
        this.artistRequestRepository = artistRequestRepository;
        this.accountBackendRepository = accountBackendRepository;
        this.artistBackendRepository = artistBackendRepository;
    }

    // =========================
    // GET ALL REQUESTS
    // =========================
    public List<ArtistRequestResponse> getAllArtistRequests() {

        List<ArtistRequestEntity> requests = artistRequestRepository.findAll();

        if (requests.isEmpty()) {
            return List.of();
        }

        return requests.stream()
                .map(ArtistRequestMapper::toResponse)
                .toList();
    }

    // =========================
    // APPROVE REQUEST
    // =========================
    @Transactional
public ApiResponse<Void> approveArtistRequest(Long requestId) {

    ArtistRequestEntity request = artistRequestRepository.findById(requestId)
            .orElseThrow(() -> new RuntimeException("Request not found"));

    // Check request status
    if (request.getStatus() != RequestStatus.PENDING) {
        return new ApiResponse<>(false, "Request already processed", null);
    }

    // Get account
    AccountEntity account = request.getUser().getAccount();

    // Prevent duplicate artist creation
    if (artistBackendRepository.existsByAccount(account)) {
        return new ApiResponse<>(false, "User is already an artist", null);
    }

    // Approve request
    request.approve();
    artistRequestRepository.save(request);

    // Create artist profile
    ArtistEntity artist = new ArtistEntity();

    artist.setAccount(account);

    // REQUIRED FIELD
    artist.setDisplayName(account.getUsername());

    // Optional fields
    artist.setBiography("");
    artist.setFollowersCount(0);
    artist.setVerified(false);

    // Save artist
    artistBackendRepository.save(artist);

    // Promote account role
    account.setRole(Role.ARTIST);
    accountBackendRepository.save(account);

    return new ApiResponse<>(
            true,
            "User successfully promoted to artist",
            null
    );
}

    // =========================
    // REJECT REQUEST
    // =========================
    @Transactional
    public ApiResponse<Void> rejectArtistRequest(Long requestId) {

        ArtistRequestEntity request = artistRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (request.getStatus() != RequestStatus.PENDING) {
            return new ApiResponse<>(false, "Request is already processed", null);
        }

        request.reject();

        return new ApiResponse<>(true, "Request rejected successfully", null);
    }

    @Transactional
    public ApiResponse<Void> createArtistRequest(ArtistApplicationRequest dto) {

        AccountEntity account = accountBackendRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔥 CHECK IF REQUEST ALREADY EXISTS
        boolean exists = artistRequestRepository.existsByUser(account.getUser());

        if (exists) {
            return new ApiResponse<>(
                    false,
                    "You already have an active artist request",
                    null
            );
        }

        ArtistRequestEntity request = new ArtistRequestEntity(
                account.getUser(),
                dto.getStageName(),
                dto.getReason(),
                null
        );

        artistRequestRepository.save(request);

        return new ApiResponse<>(
                true,
                "Artist request created successfully",
                null
        );
    }
}