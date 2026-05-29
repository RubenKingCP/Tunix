package tunix.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BanRequest {
    private Integer artistId;
    private String reason;
}