package tunix.navigation.events;

import tunix.model.ILibraryAsset;

public class LibraryPlaylistClicked {
    private final ILibraryAsset playlist;
    public LibraryPlaylistClicked(ILibraryAsset playlist){
        this.playlist = playlist;
    }
    public ILibraryAsset getPlaylist(){
        return this.playlist;
    }
}
