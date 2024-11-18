package LearnMate.dev.controller;

import LearnMate.dev.common.ApiResponse;
import LearnMate.dev.model.dto.response.ComplimentCardListResponse;
import LearnMate.dev.model.dto.response.ComplimentCardResponse;
import LearnMate.dev.service.ComplimentCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("detail")
    public ApiResponse<ComplimentCardResponse> getComplimentCardDetail(@RequestParam("complimentId") Long complimentId) {

        return ApiResponse.onSuccessData("칭찬카드 상세 조회", complimentCardService.getComplimentCardDetail(complimentId));

    }

}
