package tunix.service;

import java.util.LinkedList;
import java.util.List;

import tunix.model.Artist;

public class FollowService {
    public void followArtist(int userId, int artistId){

    }

    public void unfollowArtist(int userId, int artistId){

    }

    public List<Artist> getFollowedArtists(int userId){

        return new LinkedList<Artist>();
    }

    public boolean isFollowing(int userId, int artistId){
        return false;
        
    }
}
