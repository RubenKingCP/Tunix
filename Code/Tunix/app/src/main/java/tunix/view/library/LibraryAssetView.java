package tunix.view.library;

import tunix.model.ILibraryAsset;

public interface LibraryAssetView extends ILibraryAsset {
    String getCoverImageUrl();

    default boolean hasCoverImage() {
        String url = getCoverImageUrl();
        return url != null && !url.isBlank();
    }
}
