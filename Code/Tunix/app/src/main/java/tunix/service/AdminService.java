package tunix.service;

import java.util.LinkedList;
import java.util.List;

import tunix.api.AdminApi;
import tunix.api.SongApiClient;
import tunix.dto.request.BanRequest;
import tunix.model.ArtistRequest;

public class AdminService {
    private AdminApi api;
    private final SongApiClient songApiClient;

    public AdminService(AdminApi api, SongApiClient songApiClient){
        this.songApiClient = songApiClient;
        this.api = api;
    }

    public Boolean postIssueWarning(int artistId){
        try{
            Boolean result = api.issueWarning(artistId);
            return result;
        }catch(Exception e){
            //Handle exception
            return false; 
        }
    }

    public Boolean postBan(int artistId, String reason){
        try{
            BanRequest request = new BanRequest();
            request.setArtistId(artistId);
            request.setReason(reason);
            return api.issueBan(request);
            
        }catch(Exception e){
            //Handle exception
            return false;
        }
    }

    public String postRejectResponse(int applicationId){
        return " ";
    }

    public String postApproveResponse(int applicationId){
        return "";
    }   

    public List<String> getSongWarnings(int songId){
        return new LinkedList<String>();
    }

    // TODO: WIP
    public String postRemoveSongById(int songId) {
        return songApiClient.removeSongById(songId).getMessage();
    }

    public String getProfilePic (ArtistRequest ar) {
        return ""; // idk, something like "SELECT profile_picture_url WHERE user_id = ar.applicantId;" ??
    }

    public List<String> getArtistModerationHistory(int artistId) {
        List<String> violations = api.getArtistModerationHistory(artistId);
        if (violations == null){
            return List.of("No violations found for this artist.");
        }
        return violations;
    }
}
