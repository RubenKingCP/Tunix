package tunixserver.service;

import tunixserver.repository.AccountBackendRepository;
import tunixserver.repository.ArtistBackendRepository;
import tunixserver.repository.ArtistRequestBackendRepository;
import tunixserver.dto.response.ArtistRequestResponse;
import tunixserver.dto.enums.RequestStatus;
import tunixserver.dto.enums.Role;
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

    public ArtistRequestBackendService(ArtistRequestBackendRepository artistRequestRepository, AccountBackendRepository accountBackendRepository, ArtistBackendRepository artistBackendRepository) {
        this.artistRequestRepository = artistRequestRepository;
        this.accountBackendRepository = accountBackendRepository;
        this.artistBackendRepository = artistBackendRepository;
    }

    public List<ArtistRequestResponse> getAllArtistRequests() {

        System.out.println("ArtistReqBackendService: Fetching artist requests");

        List<ArtistRequestEntity> requests = artistRequestRepository.findAll();

        if (requests.isEmpty()) {
            System.out.println("ArtistReqBackendService: No artist requests found");
            return List.of();
        }

        // Print raw entities
        System.out.println("ArtistReqBackendService: Raw requests from DB:");
        requests.forEach(r -> System.out.println(
                "RequestId=" + r.getRequestId() +
                ", UserId=" + (r.getUser() != null ? r.getUser().getId() : null) +
                ", Status=" + r.getStatus() +
                ", StageName=" + r.getStageName()
        ));

        List<ArtistRequestResponse> response = requests.stream()
                .map(ArtistRequestMapper::toResponse)
                .toList();

        // Print mapped DTOs
        System.out.println("ArtistReqBackendService: Mapped responses:");
        response.forEach(r -> System.out.println(r.toString()));

        return response;
    }

    @Transactional
    public ApiResponse<Void> approveArtistRequest(Long requestId) {

        ArtistRequestEntity request = artistRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (request.getStatus() != RequestStatus.PENDING) {
            return new ApiResponse<>(false, "Request already processed", null);
        }

        request.approve();
        artistRequestRepository.save(request);

        AccountEntity account = request.getUser().getAccount();

        if (artistBackendRepository.existsByAccount(account)) {
            return new ApiResponse<>(false, "User is already an artist", null);
        }

        ArtistEntity artist = new ArtistEntity();
        artist.setAccount(account);
        artist.setBiography(null);
        artist.setFollowersCount(0);
        artist.setVerified(false);

        artistBackendRepository.save(artist);

        account.setRole(Role.ARTIST);
        accountBackendRepository.save(account);

        return new ApiResponse<>(true, "User successfully promoted to artist", null);
    }


    public ApiResponse<Void> rejectArtistRequest(Long requestId) {

        ArtistRequestEntity request = artistRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (request.getStatus() != RequestStatus.PENDING) {
            return new ApiResponse<>(false, "Request is already processed", null);
        }

        request.reject();
        artistRequestRepository.save(request);

        return new ApiResponse<>(true, "Request rejected successfully", null);
    }
}