package LearnMate.dev.controller;

import LearnMate.dev.common.response.ApiResponse;
import LearnMate.dev.model.dto.request.PlanPostRequest;
import LearnMate.dev.model.dto.request.PlanPatchRequest;
import LearnMate.dev.model.dto.request.PlanSaveRequest;
import LearnMate.dev.model.dto.response.PlanDetailResponse;
import LearnMate.dev.model.dto.response.PlanListResponse;
import LearnMate.dev.model.dto.response.PlanRecentResponse;
import LearnMate.dev.service.PlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/todo")
public class PlanController {

    private final PlanService planService;

    @GetMapping()
    public ApiResponse<List<String>> getRecentTodoGuide() {
        return ApiResponse.onSuccessData("최신 가이드 조회", planService.getRecentTodoGuide());
    }

    @GetMapping("/list")
    public ApiResponse<List<PlanListResponse>> getTodos() {
        return ApiResponse.onSuccessData("Todo List 조회", planService.getTodos());
    }

    @PostMapping("/guide")
    public ApiResponse<List<String>> createTodo(
            @RequestBody @Valid PlanPostRequest request
    ) {
        return ApiResponse.onSuccessData("Todo 가이드 추천", planService.postTodo(request));
    }

    @PostMapping("/guide/save")
    public ApiResponse<String> saveTodoGuide(
            @RequestBody @Valid PlanSaveRequest request
    ) {
        return ApiResponse.onSuccess(planService.saveTodo(request));
    }

    @GetMapping("/recent")
    public ApiResponse<PlanRecentResponse> getTodoRecent() {
        return ApiResponse.onSuccessData("최신 Todo 상세 조회", planService.getRecentTodoDetail());
    }

    @GetMapping("/{todoId}")
    public ApiResponse<PlanDetailResponse> getTodoDetail(
            @PathVariable("todoId") Long todoId
    ) {
        return ApiResponse.onSuccessData("Todo 상세 조회", planService.getTodoDetail(todoId));
    }

    @PatchMapping("/{todoId}")
    public ApiResponse<String> patchTodo(
            @PathVariable("todoId") Long todoId,
            @RequestBody @Valid PlanPatchRequest request
    ) {
        return ApiResponse.onSuccessData("Todo Guide 재생성", planService.patchTodo(todoId, request));
    }

    @DeleteMapping("/{todoId}")
    public ApiResponse<String> deleteTodo(
            @PathVariable("todoId") Long todoId
    ) {
        return ApiResponse.onSuccess(planService.deleteTodo(todoId));
    }
}
