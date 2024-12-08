package LearnMate.dev.model.dto.response.plan;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlanListResponse {

    private Long todoId;
    private String content;
    private String createdAt;

    @Builder
    public PlanListResponse (Long todoId, String content, String createdAt) {
        this.todoId = todoId;
        this.content = content;
        this.createdAt = createdAt;
    }
}
