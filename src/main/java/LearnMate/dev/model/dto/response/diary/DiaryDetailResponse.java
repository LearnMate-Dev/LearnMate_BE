package LearnMate.dev.model.dto.response.diary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiaryDetailResponse {

    private Long diaryId;
    private String date;
    private String content;
    private String emotion; // 감정 지표
    private String actionTip;
    private String compliment;

}
