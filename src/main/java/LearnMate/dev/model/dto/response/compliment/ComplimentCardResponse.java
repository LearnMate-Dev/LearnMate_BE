package LearnMate.dev.model.dto.response.compliment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplimentCardResponse {

    private Long complimentId;
    private String complimentKeyword;
    private String complimentContent;

}
