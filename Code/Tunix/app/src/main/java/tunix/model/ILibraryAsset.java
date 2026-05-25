package tunix.model;

import java.awt.Color;
import java.util.List;

import tunix.dto.enums.LibraryAssetType;
import tunix.model.musicContent.Song;

public interface ILibraryAsset {

    String getTitle();

    int getId();

    LibraryAssetType getType();

    String getSubtitle();

    /**
     * Songs displayed inside MusicView table.
     */
    List<Song> getDisplaySongs();

    default boolean isCircularAvatar() {
        return false;
    }

    default Color getDisplayColor() {

        int hash = Math.abs(getTitle().hashCode());

        int r = 40 + (hash % 80);
        int g = 50 + ((hash / 7) % 80);
        int b = 70 + ((hash / 11) % 80);

        return new Color(r, g, b);
    }
}