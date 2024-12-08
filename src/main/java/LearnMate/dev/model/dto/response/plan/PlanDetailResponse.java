package LearnMate.dev.model.dto.response.plan;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PlanDetailResponse {

    private String content;
    private List<String> guides;

    @Builder
    public PlanDetailResponse(String content, List<String> guides) {
        this.content = content;
        this.guides = guides;
    }
}
