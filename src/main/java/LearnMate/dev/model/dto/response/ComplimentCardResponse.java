package LearnMate.dev.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplimentCardResponse {

    private String complimentTitle;
    private String complimentContent;
    private Long diaryId;

}
