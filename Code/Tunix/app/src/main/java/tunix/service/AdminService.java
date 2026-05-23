package tunix.service;

import java.util.LinkedList;
import java.util.List;

import tunix.api.AdminApi;

public class AdminService {
    private AdminApi api;

    public AdminService(AdminApi api){
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

    public Boolean postBan(int artistId){
        try{
            Boolean result = api.issueBan(artistId);
            return result;
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
}
