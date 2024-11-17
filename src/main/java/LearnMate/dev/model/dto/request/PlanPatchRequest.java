package LearnMate.dev.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlanPatchRequest {

    @NotBlank(message = "수정할 계획은 필수입니다.")
    private String content;

}
