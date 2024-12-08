package LearnMate.dev.model.dto.request.plan;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlanPostRequest {

    @NotBlank(message = "계획 내용은 필수입니다.")
    private String content;
}
