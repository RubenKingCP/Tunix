package tunix.dto.request;

public class PlaylistCreateRequest {
    private final String name;
    private final String description;
    private final String coverImagePath;

    public PlaylistCreateRequest(String name, String description, String coverImagePath) {
        this.name = name;
        this.description = description;
        this.coverImagePath = coverImagePath;
    }

    
    
}
