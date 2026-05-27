package tunix.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SongResponse {

    private Long id;

    private String title;

    private Long artistId;

    private String artistName;

    private int duration;

    private String filePathUrl;

    private String coverImageUrl;
}