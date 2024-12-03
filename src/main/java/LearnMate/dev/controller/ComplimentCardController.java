package LearnMate.dev.controller;

import LearnMate.dev.common.ApiResponse;
import LearnMate.dev.model.dto.response.ComplimentCardListResponse;
import LearnMate.dev.model.dto.response.ComplimentCardResponse;
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
        return ApiResponse.onSuccessData("칭찬카드 리스트 조회", complimentCardService.getComplimentCards());
    }

    @GetMapping("/{complimentId}/detail")
    public ApiResponse<ComplimentCardResponse> getComplimentCardDetail(@PathVariable("complimentId") Long complimentId) {

        return ApiResponse.onSuccessData("칭찬카드 상세 조회", complimentCardService.getComplimentCardDetail(complimentId));

    }

}
