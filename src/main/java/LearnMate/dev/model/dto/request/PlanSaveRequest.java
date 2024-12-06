package LearnMate.dev.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PlanSaveRequest {

    @NotNull(message = "계획 내용은 필수입니다.")
    private String content;

    @NotNull(message = "Todo Guide는 필수입니다.")
    private List<String> guides;

    @Builder
    public PlanSaveRequest(String content,
                           List<String> guides) {
        this.content = content;
        this.guides = guides;
    }
}
