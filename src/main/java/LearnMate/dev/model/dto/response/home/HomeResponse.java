package LearnMate.dev.model.dto.response.home;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HomeResponse {

    private Long diaryId;
    private String emoticon;
    private Long todoId;
    private String todoGuide;

}
