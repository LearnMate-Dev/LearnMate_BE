package LearnMate.dev.controller;

import LearnMate.dev.common.response.ApiResponse;
import LearnMate.dev.model.dto.response.report.ReportResponse;
import LearnMate.dev.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/report")
public class ReportController {
    private final ReportService reportService;

    @GetMapping()
    public ApiResponse<ReportResponse.ReportDto> getEmotionReport() {
        return ApiResponse.onSuccessData("감정 분석 레포트 조회 성공", reportService.getEmotionReport());
    }

    @GetMapping("/detail")
    public ApiResponse<ReportResponse.ReportDetailDto> getEmotionDetailReport() {
        return ApiResponse.onSuccessData("감정 분석 상세 레포트 조회 성공", reportService.getEmotionDetailReport());
    }
}
