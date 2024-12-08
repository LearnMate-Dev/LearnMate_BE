package LearnMate.dev.model.dto.response.report;

import LearnMate.dev.model.enums.EmotionSpectrum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportResponse {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmotionDto {

        private EmotionSpectrum emotionSpectrum;
        private Long count;
        private int order;

        public EmotionDto(EmotionSpectrum emotion, Long count) {
            this.emotionSpectrum = emotion;
            this.count = count == null ? 0 : count;
            this.order = emotion.getOrder();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReportEmotionDto {

        private String emotion;
        private String emoticon;
        private Long count;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmotionOnDayDto {

        private String emotion;
        private String emoticon;
        private String day;

        public EmotionOnDayDto(EmotionSpectrum emotion, LocalDateTime date) {
            this.emotion = emotion.getValue();
            this.emoticon = emotion.getEmoticon();
            this.day = date.format(DateTimeFormatter.ofPattern("MM/dd"));
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmotionRankDto {

        private String emotion;
        private String emoticon;
        private Long rank;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReportDto {

        private List<ReportEmotionDto> emotionReport;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReportDetailDto {

        private List<ReportEmotionDto> emotionReport;
        private List<EmotionOnDayDto> emotionOnDayList;
        private List<EmotionRankDto> emotionRankDtoList;

    }

}


