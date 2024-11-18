package LearnMate.dev.controller;

import LearnMate.dev.common.ApiResponse;
import LearnMate.dev.model.dto.request.PlanPostRequest;
import LearnMate.dev.model.dto.request.PlanPatchRequest;
import LearnMate.dev.model.dto.response.PlanDetailResponse;
import LearnMate.dev.service.PlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/todo")
public class PlanController {

    private final PlanService planService;

    @GetMapping()
    public ApiResponse<String> getTodos() {
        return ApiResponse.onSuccess(planService.getTodos());
    }

    @PostMapping()
    public ApiResponse<String> createTodo(@RequestBody @Valid PlanPostRequest request) {
        return ApiResponse.onSuccessData("Todo Guide 생성", planService.postTodo(request));
    }

    @GetMapping("/{todoId}")
    public ApiResponse<PlanDetailResponse> getTodoDetail(@PathVariable("todoId") Long todoId) {
        return ApiResponse.onSuccessData("Todo 상세 조회", planService.getTodoDetail(todoId));
    }

    @PatchMapping("/{todoId}")
    public ApiResponse<String> patchTodo(@PathVariable("todoId") Long todoId,
                                          @RequestBody @Valid PlanPatchRequest request) {
        return ApiResponse.onSuccessData("Todo Guide 재생성", planService.patchTodo(todoId, request));
    }

    @DeleteMapping("/{todoId}")
    public ApiResponse<String> deleteTodo(@PathVariable("todoId") Long todoId) {
        return ApiResponse.onSuccess(planService.deleteTodo(todoId));
    }

}
