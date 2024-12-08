package LearnMate.dev.model.dto.request.plan;

import jakarta.validation.constraints.NotNull;
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
}
