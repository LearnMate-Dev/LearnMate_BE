package LearnMate.dev.model.converter;

import LearnMate.dev.model.dto.response.ReportResponse;

import java.util.List;

public class ReportConverter {

    public static ReportResponse.ReportDto toReportDto(List<ReportResponse.EmotionDto> emotionDtoList) {
        return ReportResponse.ReportDto.builder()
                .emotionReport(emotionDtoList)
                .build();
    }

}
