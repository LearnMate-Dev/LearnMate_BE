package LearnMate.dev.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DiaryPostRequest {

    @NotBlank(message = "일기 내용을 입력해주세요.")
    private String content;

}
