package tunix.model;

import java.util.List;

public class Library {
    private List<ILibraryAsset> assets;

    public Library(List<ILibraryAsset> assets) {
        this.assets = assets;
    }

    public List<ILibraryAsset> getAssets() {
        return assets;
    }

    public void addAsset(ILibraryAsset asset) {
        assets.add(asset);
    }

    public void removeAsset(ILibraryAsset asset) {
        assets.remove(asset);
    }
}
