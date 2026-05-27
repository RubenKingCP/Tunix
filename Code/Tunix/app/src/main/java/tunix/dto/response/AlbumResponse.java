package tunix.dto.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AlbumResponse {

    private Long id;
    private String title;
    private Long artistId;
    private LocalDate releaseDate;
}