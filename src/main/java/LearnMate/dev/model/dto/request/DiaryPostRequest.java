package LearnMate.dev.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DiaryPostRequest {

    @NotBlank(message = "일기 내용을 입력해주세요.")
    private String content;

    @NotNull(message = "감정 점수를 입력해주세요.")
    private Double score;

    @NotBlank(message = "감정 지표를 입력해주세요.")
    private String emotion;

    @NotBlank(message = "행동 요령 제안을 입력해주세요.")
    private String actionTip;

}
