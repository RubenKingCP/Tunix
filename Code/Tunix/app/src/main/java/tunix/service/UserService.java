package tunix.service;

import tunix.api.UserApi;

public class UserService {
    private final UserApi api;
    public UserService(UserApi api){
        this.api = api;
    }
    public boolean startTrial(int userId){
        return api.trialStarted(userId);
    }
    public boolean buyPremium(int userId){
        return api.buyPremium(userId);
    } 
    public boolean cancelPremium(int userId){
        return api.cancelPremium(userId);
    }
}
