package tunix.api;

public class UserApi {
    private final ApiClient api;
    public UserApi(ApiClient api){
        this.api = api;
    }
    public boolean trialStarted(int userId) {
        return api.post("accounts/trial", userId, boolean.class).getData();
        //return true; // Return true if trial started successfully
    }
    /*public boolean premiumStatusUpdated(int userId, boolean isPremium) {
        // Logic to update the user's premium status
        return true; // Return true if update was successful
    }*/
   public boolean buyPremium(int userId){
        return true;
   }
   public boolean cancelPremium(int userId){
        return true;
   }
}
