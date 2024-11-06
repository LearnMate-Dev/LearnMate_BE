package LearnMate.dev.controller;

import LearnMate.dev.common.ApiResponse;
import LearnMate.dev.model.dto.request.DiaryPostRequest;
import LearnMate.dev.service.DiaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/diary")
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping("")
    public ApiResponse<Long> postDiary(
            // TODO: get user info from session
//            @SessionAttribute(name = "user_id") Long userId,
            @RequestBody @Valid DiaryPostRequest request) {

        return ApiResponse.onSuccessData("일기 작성 성공", diaryService.postDiary(1L, request));

    }
}
