package LearnMate.dev.model.dto.response;

import LearnMate.dev.model.enums.EmotionSpectrum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiaryDetailResponse {

    private String date;
    private String content;
    private String emotion; // 감정 지표
    private String actionTip;

}
