package tunix.service;

import tunix.api.UserApi;

public class UserService {
    private final UserApi api;
    public UserService(UserApi api){
        this.api = api;
    }
}
