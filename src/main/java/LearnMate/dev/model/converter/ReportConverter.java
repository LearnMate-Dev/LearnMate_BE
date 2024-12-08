package LearnMate.dev.model.converter;

import LearnMate.dev.model.dto.response.report.ReportResponse;

import java.util.List;

public class ReportConverter {

    public static ReportResponse.ReportEmotionDto toReportEmotionDto(ReportResponse.EmotionDto emotionDto) {
        return ReportResponse.ReportEmotionDto.builder()
                .emotion(emotionDto.getEmotionSpectrum().getValue())
                .count(emotionDto.getCount())
                .build();
    }

    public static List<ReportResponse.ReportEmotionDto> toReportEmotionDtoList(List<ReportResponse.EmotionDto> emotionDtoList) {
        return emotionDtoList.stream()
                .map(ReportConverter::toReportEmotionDto)
                .toList();
    }

    public static ReportResponse.ReportDto toReportDto(List<ReportResponse.ReportEmotionDto> reportEmotionDtoList) {
        return ReportResponse.ReportDto.builder()
                .emotionReport(reportEmotionDtoList)
                .build();
    }

    public static ReportResponse.ReportDetailDto toReportDetailDto(List<ReportResponse.ReportEmotionDto> reportEmotionDtoList,
                                                                   List<ReportResponse.EmotionOnDayDto> emotionOnDayDtoList,
                                                                   List<ReportResponse.EmotionRankDto> emotionRankDtoList) {
        return ReportResponse.ReportDetailDto.builder()
                .emotionReport(reportEmotionDtoList)
                .emotionOnDayList(emotionOnDayDtoList)
                .emotionRankDtoList(emotionRankDtoList)
                .build();
    }

}
