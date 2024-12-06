package LearnMate.dev.service;

import LearnMate.dev.common.ErrorStatus;
import LearnMate.dev.common.exception.ApiException;
import LearnMate.dev.model.converter.PlanConverter;
import LearnMate.dev.model.dto.request.PlanPostRequest;
import LearnMate.dev.model.dto.request.PlanPatchRequest;
import LearnMate.dev.model.dto.request.PlanSaveRequest;
import LearnMate.dev.model.dto.response.PlanDetailResponse;
import LearnMate.dev.model.dto.response.PlanListResponse;
import LearnMate.dev.model.dto.response.PlanRecentResponse;
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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlanService {

    private final OpenAIService openAIService;

    private final UserRepository userRepository;
    private final PlanRepository planRepository;

    // todo 리스트 조회
    public List<PlanListResponse> getTodos() {
        Long userId = getUserIdFromAuthentication();

        List<Plan> plans = planRepository.findAllByUserIdOrderByCreatedAtDesc(userId);

        return PlanConverter.toPlanListResponse(plans);
    }

    // 최신 가이드 조회
    public List<String> getRecentTodoGuide() {
        Long userId = getUserIdFromAuthentication();

        Plan plan = findRecentPlanByUserId(userId);

        return convertGuideToGuides(plan.getGuide());
    }

    // Todo 생성
    public List<String> postTodo(PlanPostRequest request) {
        validContentLength(request.getContent());

        return convertGuideToGuides(getTodoGuide(request.getContent()));
    }

    // Todo Guide 저장
    @Transactional
    public String saveTodo(PlanSaveRequest request) {
        validContentLength(request.getContent());

        Long userId = getUserIdFromAuthentication();

        User user = findUserById(userId);

        planRepository.save(PlanConverter.toPlan(request.getContent(), user, convertGuidesToGuide(request.getGuides())));

        return "가이드 저장 완료";
    }

    // Todo 최신 조회
    public PlanRecentResponse getRecentTodoDetail() {
        Long userId = getUserIdFromAuthentication();

        Plan plan = findRecentPlanByUserId(userId);

        return PlanConverter.toPlanRecentResponse(plan.getContent(), convertGuideToGuides(plan.getGuide()));
    }

    // Todo 상세 조회
    public PlanDetailResponse getTodoDetail(Long todoId) {
        Plan plan = findPlanByTodoId(todoId);

        return PlanConverter.toPlanDetailResponse(plan.getContent(), convertGuideToGuides(plan.getGuide()));
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

    private List<String> convertGuideToGuides(String guide) {
        return Arrays.stream(guide.split("\n"))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    private String convertGuidesToGuide(List<String> guides) {
        return String.join("\n", guides);
    }
}
