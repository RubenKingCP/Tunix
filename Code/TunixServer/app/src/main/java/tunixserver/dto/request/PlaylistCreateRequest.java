package tunixserver.dto.request;

import java.util.List;

public class PlaylistCreateRequest {

    private String title;
    private Long creatorId;
    private List<Long> coauthorIds;

    public PlaylistCreateRequest() {}

    public PlaylistCreateRequest(String title, Long creatorId, List<Long> coauthorIds) {
        this.title = title;
        this.creatorId = creatorId;
        this.coauthorIds = coauthorIds;
    }

    public String getTitle() {
        return title;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public List<Long> getCoauthorIds() {
        return coauthorIds;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public void setCoauthorIds(List<Long> coauthorIds) {
        this.coauthorIds = coauthorIds;
    }
}