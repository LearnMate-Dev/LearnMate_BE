package LearnMate.dev.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlanSaveRequest {

    @NotNull(message = "계획 내용은 필수입니다.")
    private String content;

    @NotNull(message = "Todo Guide는 필수입니다.")
    private String guide;

    @Builder
    public PlanSaveRequest(String content,
                           String guide) {
        this.content = content;
        this.guide = guide;
    }
}
