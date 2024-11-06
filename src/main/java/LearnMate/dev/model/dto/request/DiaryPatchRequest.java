package LearnMate.dev.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DiaryPatchRequest {

    @NotNull(message = "수정할 일기의 id를 입력해주세요.")
    private Long diaryId;

    @NotBlank(message = "수정할 일기 내용을 입력해주세요.")
    private String content;
}
