package LearnMate.dev.model.converter;

import LearnMate.dev.model.dto.response.ReportResponse;

import java.util.List;

public class ReportConverter {

    public static ReportResponse.ReportEmotionDto toReportEmotionDto(ReportResponse.EmotionDto emotionDto) {
        return ReportResponse.ReportEmotionDto.builder()
                .emotion(emotionDto.getEmotionSpectrum().getValue())
                .emoticon(emotionDto.getEmotionSpectrum().getEmoticon())
                .count(emotionDto.getCount())
                .build();
    }
    public static ReportResponse.ReportDto toReportDto(List<ReportResponse.EmotionDto> emotionDtoList) {

        List<ReportResponse.ReportEmotionDto> reportEmotionDtoList = emotionDtoList.stream()
                .map(ReportConverter::toReportEmotionDto)
                .toList();

        return ReportResponse.ReportDto.builder()
                .emotionReport(reportEmotionDtoList)
                .build();
    }

}
