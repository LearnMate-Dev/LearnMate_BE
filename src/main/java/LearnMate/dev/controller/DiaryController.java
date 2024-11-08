package LearnMate.dev.controller;

import LearnMate.dev.common.ApiResponse;
import LearnMate.dev.model.dto.request.DiaryAnalysisRequest;
import LearnMate.dev.model.dto.request.DiaryPatchRequest;
import LearnMate.dev.model.dto.request.DiaryPostRequest;
import LearnMate.dev.model.dto.response.DiaryAnalysisResponse;
import LearnMate.dev.model.dto.response.DiaryDetailResponse;
import LearnMate.dev.service.DiaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/diary")
public class DiaryController {

    private final DiaryService diaryService;

    @GetMapping("/analysis")
    public ApiResponse<DiaryAnalysisResponse> getDiaryAnalysis(
            // TODO: get user info from session
//            @SessionAttribute(name = "user_id") Long userId,
            @RequestBody @Valid DiaryAnalysisRequest request) {

        return ApiResponse.onSuccessData("일기 분석 성공", diaryService.analyzeDiary(1L, request));

    }

    @PostMapping("")
    public ApiResponse<DiaryDetailResponse> postDiary(
            // TODO: get user info from session
//            @SessionAttribute(name = "user_id") Long userId,
            @RequestBody @Valid DiaryPostRequest request) {

        return ApiResponse.onSuccessData("일기 작성 성공", diaryService.postDiary(1L, request));

    }

    @PatchMapping("")
    public ApiResponse<DiaryDetailResponse> patchDiary(
            // TODO: get user info from session
//            @SessionAttribute(name = "user_id") Long userId,
            @RequestBody @Valid DiaryPatchRequest request ) {

        return ApiResponse.onSuccessData("일기 수정 성공", diaryService.patchDiary(1L, request));

    }

    @DeleteMapping("/{diaryId}")
    public ApiResponse deleteDiary(
            // TODO: get user info from session
//            @SessionAttribute(name = "user_id") Long userId,
            @PathVariable(value = "diaryId") Long diaryId) {

        diaryService.deleteDiary(1L, diaryId);
        return ApiResponse.onSuccess("일기 삭제 성공");

    }

    @GetMapping("/{diaryId}")
    public ApiResponse<DiaryDetailResponse> getDiaryDetail(
            // TODO: get user info from session
//            @SessionAttribute(name = "user_id") Long userId,
            @PathVariable(value = "diaryId") Long diaryId) {

        return ApiResponse.onSuccessData("일기 상세 조회 성공", diaryService.getDiaryDetail(1L, diaryId));

    }
}
