package LearnMate.dev.model.converter;


import LearnMate.dev.model.dto.response.PlanDetailResponse;
import LearnMate.dev.model.entity.Plan;
import LearnMate.dev.model.entity.User;

public class PlanConverter {

    public static Plan toPlan(String content, User user, String guide) {
        return Plan.builder()
                .content(content)
                .user(user)
                .guide(guide)
                .build();
    }

    public static PlanDetailResponse toPlanDetailResponse(String content, String guide) {
        return PlanDetailResponse.builder()
                .content(content)
                .guide(guide)
                .build();
    }

}
