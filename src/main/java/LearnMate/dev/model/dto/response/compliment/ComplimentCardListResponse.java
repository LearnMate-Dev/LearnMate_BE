package LearnMate.dev.model.dto.response.compliment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplimentCardListResponse {

    private Long complimentId;
    private String complimentKeyword;

}
