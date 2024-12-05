package LearnMate.dev.service;

import LearnMate.dev.common.ErrorStatus;
import LearnMate.dev.common.exception.ApiException;
import LearnMate.dev.model.converter.PlanConverter;
import LearnMate.dev.model.dto.request.PlanPostRequest;
import LearnMate.dev.model.dto.request.PlanPatchRequest;
import LearnMate.dev.model.dto.request.PlanSaveRequest;
import LearnMate.dev.model.dto.response.PlanDetailResponse;
import LearnMate.dev.model.entity.Plan;
import LearnMate.dev.model.entity.User;
import LearnMate.dev.repository.PlanRepository;
import LearnMate.dev.repository.UserRepository;
import LearnMate.dev.security.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlanService {

    private final OpenAIService openAIService;

    private final UserRepository userRepository;
    private final PlanRepository planRepository;

    // 최신 가이드 조회
    public String getTodos() {
        Long userId = getUserIdFromAuthentication();

        Plan plan = findRecentPlanByUserId(userId);

        return plan.getGuide();
    }

    // Todo 생성
    public String postTodo(PlanPostRequest request) {
        validContentLength(request.getContent());

        return getTodoGuide(request.getContent());
    }

    @Transactional
    public String saveTodo(PlanSaveRequest request) {
        validContentLength(request.getContent());

        Long userId = getUserIdFromAuthentication();

        User user = findUserById(userId);

        planRepository.save(PlanConverter.toPlan(request.getContent(), user, request.getGuide()));

        return "가이드 저장 완료";
    }

    // Todo 상세 조회
    public PlanDetailResponse getTodoDetail(Long todoId) {
        Long userId = getUserIdFromAuthentication();

        Plan plan = findRecentPlanByUserId(userId);

        return PlanConverter.toPlanDetailResponse(plan.getContent(), plan.getGuide());
    }

    // Todo 수정
    @Transactional
    public String patchTodo(Long todoId, PlanPatchRequest request) {
        validContentLength(request.getContent());

        Long userId = getUserIdFromAuthentication();

        User user = findUserById(userId);

        Plan plan = findPlanByTodoId(todoId);

        validIsUserAuthorizedForPlan(user, plan);

        String guide = getTodoGuide(request.getContent());

        plan.updateContentAndGuide(request.getContent(), guide);

        return guide;
    }

    // Todo 삭제
    @Transactional
    public String deleteTodo(Long todoId) {
        Long userId = getUserIdFromAuthentication();

        Plan plan = findPlanByTodoId(todoId);

        User user = findUserById(userId);

        validIsUserAuthorizedForPlan(user, plan);

        planRepository.delete(plan);

        return "Todo 삭제";
    }

    private Long getUserIdFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUserId();
    }

    private Plan findPlanByTodoId(Long planId) {
        return planRepository.findById(planId).orElseThrow(() -> new ApiException(ErrorStatus._PLAN_NOT_FOUND));
    }

    private Plan findRecentPlanByUserId(Long userId) {
        return planRepository.findFirstByUserIdOrderByCreatedAtDesc(userId);
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ApiException(ErrorStatus._USER_NOT_FOUND));
    }

    private void validContentLength(String content) {
        if (content.length() > 15)
            throw new ApiException(ErrorStatus._INVALID_DIARY_CONTENT_LENGTH);
    }

    private void validIsUserAuthorizedForPlan(User user, Plan plan) {
        if (!plan.getUser().equals(user))
            throw new ApiException(ErrorStatus._USER_FORBIDDEN_PLAN);
    }

    private String getTodoGuide(String content) {
        return openAIService.getTodoGuide(content);
    }
}
