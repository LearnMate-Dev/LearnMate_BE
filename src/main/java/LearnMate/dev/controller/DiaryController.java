package LearnMate.dev.controller;

import LearnMate.dev.common.response.ApiResponse;
import LearnMate.dev.model.dto.request.diary.DiaryAnalysisRequest;
import LearnMate.dev.model.dto.request.diary.DiaryPatchRequest;
import LearnMate.dev.model.dto.request.diary.DiaryPostRequest;
import LearnMate.dev.model.dto.response.diary.DiaryAnalysisResponse;
import LearnMate.dev.model.dto.response.diary.DiaryCalendarResponse;
import LearnMate.dev.model.dto.response.diary.DiaryDetailResponse;
import LearnMate.dev.service.DiaryService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/diary")
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping("/analysis")
    public ApiResponse<DiaryAnalysisResponse> getDiaryAnalysis(
            @RequestBody @Valid DiaryAnalysisRequest request
    ) {
        return ApiResponse.onSuccessData("일기 분석 성공", diaryService.analyzeDiary(request));
    }

    @PostMapping("")
    public ApiResponse<DiaryDetailResponse> postDiary(
            @RequestBody @Valid DiaryPostRequest request
    ) {
        return ApiResponse.onSuccessData("일기 작성 성공", diaryService.postDiary(request));
    }

    @PatchMapping("")
    public ApiResponse<DiaryDetailResponse> patchDiary(
            @RequestBody @Valid DiaryPatchRequest request
    ) {
        return ApiResponse.onSuccessData("일기 수정 성공", diaryService.patchDiary(request));
    }

    @DeleteMapping("/{diaryId}")
    public ApiResponse deleteDiary(
            @PathVariable(value = "diaryId") Long diaryId
    ) {
        diaryService.deleteDiary(diaryId);
        return ApiResponse.onSuccess("일기 삭제 성공");
    }

    @GetMapping("/{diaryId}")
    public ApiResponse<DiaryDetailResponse> getDiaryDetail(
            @PathVariable(value = "diaryId") Long diaryId
    ) {
        return ApiResponse.onSuccessData("일기 상세 조회 성공", diaryService.getDiaryDetail(diaryId));
    }

    @GetMapping
    public ApiResponse<DiaryDetailResponse> getDiaryDetailByDate(
            @RequestParam(value = "date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return ApiResponse.onSuccessData("일기 상세 조회 성공", diaryService.getDiaryDetailByDate(date));
    }

    @GetMapping("/calendar")
    public ApiResponse<DiaryCalendarResponse.DiaryCalendarDto> getDiaryCalendar(
            @RequestParam(value = "date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return ApiResponse.onSuccessData("캘린더 조회 성공", diaryService.getDiaryCalendar(date));
    }
}
