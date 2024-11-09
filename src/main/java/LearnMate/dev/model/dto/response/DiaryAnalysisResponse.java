package LearnMate.dev.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiaryAnalysisResponse {

    private Float emotionScore;
    private String actionTip;

}
