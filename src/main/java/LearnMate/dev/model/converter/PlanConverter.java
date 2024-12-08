package LearnMate.dev.model.converter;


import LearnMate.dev.model.dto.response.PlanDetailResponse;
import LearnMate.dev.model.dto.response.PlanListResponse;
import LearnMate.dev.model.dto.response.PlanRecentResponse;
import LearnMate.dev.model.entity.Plan;
import LearnMate.dev.model.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class PlanConverter {
    public static Plan toPlan(String content, User user, String guide) {
        return Plan.builder()
                .content(content)
                .user(user)
                .guide(guide)
                .build();
    }

    public static List<PlanListResponse> toPlanListResponse(List<Plan> plans) {
        return plans.stream()
                .map(plan -> PlanListResponse.builder()
                        .todoId(plan.getId())
                        .content(plan.getContent())
                        .createdAt(plan.getCreatedAtFormatted())
                        .build())
                .collect(Collectors.toList());
    }

    public static PlanRecentResponse toPlanRecentResponse(String content, List<String> guides) {
        return PlanRecentResponse.builder()
                .content(content)
                .guides(guides)
                .build();
    }

    public static PlanDetailResponse toPlanDetailResponse(String content, List<String> guides) {
        return PlanDetailResponse.builder()
                .content(content)
                .guides(guides)
                .build();
    }
}
