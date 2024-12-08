package LearnMate.dev.controller;

import LearnMate.dev.common.response.ApiResponse;
import LearnMate.dev.model.dto.response.compliment.ComplimentCardListResponse;
import LearnMate.dev.model.dto.response.compliment.ComplimentCardResponse;
import LearnMate.dev.model.dto.response.diary.DiarySimpleResponse;
import LearnMate.dev.service.ComplimentCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/complimentCards")
public class ComplimentCardController {

    private final ComplimentCardService complimentCardService;

    @GetMapping
    public ApiResponse<List<ComplimentCardListResponse>> getComplimentCardsList() {
        return ApiResponse.onSuccessData("칭찬카드 리스트 조회 성공", complimentCardService.getComplimentCards());
    }

    @GetMapping("/{complimentId}/detail")
    public ApiResponse<ComplimentCardResponse> getComplimentCardDetail(
            @PathVariable("complimentId") Long complimentId
    ) {
        return ApiResponse.onSuccessData("칭찬카드 상세 조회 성공", complimentCardService.getComplimentCardDetail(complimentId));
    }

    @GetMapping("/{complimentId}/diaries")
    public ApiResponse<List<DiarySimpleResponse>> getComplimentCardDiaries(
            @PathVariable("complimentId") Long complimentId
    ) {
        return ApiResponse.onSuccessData("칭찬카드 관련 일기 조회 성공", complimentCardService.getComplimentCardDiaries(complimentId));
    }
}
