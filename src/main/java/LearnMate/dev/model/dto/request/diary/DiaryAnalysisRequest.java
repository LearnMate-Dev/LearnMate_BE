package LearnMate.dev.model.dto.request.diary;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DiaryAnalysisRequest {

    @NotBlank(message = "일기 내용을 입력해주세요.")
    private String content;
}
