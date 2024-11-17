package LearnMate.dev.model.converter;


import LearnMate.dev.model.dto.response.PlanDetailResponse;
import LearnMate.dev.model.entity.Plan;
import LearnMate.dev.model.entity.User;

public class PlanConverter {

    public static Plan toPlan(String content, User user, String guid) {
        return Plan.builder()
                .content(content)
                .user(user)
                .guide(guid)
                .build();
    }

    public static PlanDetailResponse toPlanDetailResponse(String content, String guide) {
        return PlanDetailResponse.builder()
                .content(content)
                .guide(guide)
                .build();
    }

}
