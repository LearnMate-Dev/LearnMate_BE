package LearnMate.dev.model.dto.response.plan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

import java.util.List;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlanListResponse {

    private Long todoId;
    private String content;
    private String createdAt;
    private List<String> guide;

}
