package tunixserver.service;

import tunixserver.repository.ArtistRequestBackendRepository;
import tunixserver.dto.response.ArtistRequestResponse;
import tunixserver.dto.response.ApiResponse;
import tunixserver.entities.ArtistRequestEntity;
import tunixserver.mapper.ArtistRequestMapper;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistRequestBackendService {

    private final ArtistRequestBackendRepository artistRequestRepository;

    public ArtistRequestBackendService(ArtistRequestBackendRepository artistRequestRepository) {
        this.artistRequestRepository = artistRequestRepository;
    }

    public List<ArtistRequestResponse> getAllArtistRequests() {

        List<ArtistRequestEntity> artistRequests = artistRequestRepository.findAll();

        return artistRequests.stream()
                .map(ArtistRequestMapper::toResponse)
                .toList();
    }

    public ApiResponse<Void> approveArtistRequest(Long requestId) {

        ArtistRequestEntity request = artistRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        System.err.println("ArtistRequestBackendService: " + request);

        if (!"PENDING".equals(request.getStatus())) {
            return new ApiResponse<>(false, "Request is already processed", null);
        }

        request.approve();
        artistRequestRepository.save(request);

        return new ApiResponse<>(true, "Request approved successfully", null);
    }

    public ApiResponse<Void> rejectArtistRequest(Long requestId) {

        ArtistRequestEntity request = artistRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (!"PENDING".equals(request.getStatus())) {
            return new ApiResponse<>(false, "Request is already processed", null);
        }

        request.reject();
        artistRequestRepository.save(request);

        return new ApiResponse<>(true, "Request rejected successfully", null);
    }
}