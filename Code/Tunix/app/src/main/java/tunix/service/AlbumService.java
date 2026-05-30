package tunix.service;

import java.util.List;

import tunix.api.AlbumApi;
import tunix.model.ILibraryAsset;
import tunix.model.musicContent.Album;
import tunix.model.musicContent.Song;

public class AlbumService {
    private final AlbumApi albumApi;

    public AlbumService (AlbumApi albumApi) {
        this.albumApi = albumApi;
    }

    public Album getAlbumById(int albumId) {
        ILibraryAsset asset = albumApi.getById(albumId);
        System.out.println("AlbumService.getAlbumById -> asset: " + asset + " | type: " + (asset == null ? "null" : asset.getClass().getName()));
        return asset instanceof Album ? (Album) asset : new Album("Placeholder", 12323, null, List.of(new Song("adsads", 123L, null, albumId, null, null)), null);
    }
}
